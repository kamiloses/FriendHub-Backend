package com.kamiloses.commentservice.service;

import com.kamiloses.commentservice.dto.CommentDto;
import com.kamiloses.commentservice.dto.PublishCommentDto;
import com.kamiloses.commentservice.entity.CommentEntity;
import com.kamiloses.commentservice.exception.CommentDatabaseFetchException;
import com.kamiloses.commentservice.rabbit.RabbitCommentProducer;
import com.kamiloses.commentservice.repository.CommentRepository;
import com.kamiloses.rabbitmq.exception.RabbitExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@Import(RabbitExceptionHandler.class)
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final RabbitCommentProducer rabbitCommentProducer;

    public CommentService(CommentRepository commentRepository, RabbitCommentProducer rabbitCommentProducer) {
        this.commentRepository = commentRepository;
        this.rabbitCommentProducer = rabbitCommentProducer;
    }


    public Flux<CommentDto> findCommentsRelatedWithPost(String postId) {
        return commentRepository.findCommentEntitiesByPostId(postId)
                .onErrorResume(error -> {
                    log.error("There was some problem with fetching comments related with post", error);
                    return Mono.error(CommentDatabaseFetchException::new);
                })
                .flatMap(commentEntity ->
                        rabbitCommentProducer.askForUserDetails(commentEntity.getUserId())
                                .map(userDetailsDto -> CommentDto.builder()
                                        .id(commentEntity.getId())
                                        .content(commentEntity.getContent())
                                        .createdAt(commentEntity.getCreatedAt())
                                        .userDetails(userDetailsDto)
                                        .postId(commentEntity.getPostId())
                                        .parentCommentId(commentEntity.getParentCommentId())
                                        .replies(new ArrayList<>())
                                        .build())
                )
                .collectList()
                .flatMapMany(flatList -> {
                    List<CommentDto> tree = buildCommentTree(flatList);
                    return Flux.fromIterable(tree);
                });
    }

    public Mono<Void> publishComment(PublishCommentDto publishCommentDto, String username) {


        return rabbitCommentProducer.askForUserDetails(username)
                .flatMap(userDetails -> {
                    CommentEntity commentEntity = CommentEntity.builder()
                            .content(publishCommentDto.getContent())
                            .postId(publishCommentDto.getPostId())
                            .userId(userDetails.getId())
                            .createdAt(new Date()).build();


                    if (publishCommentDto.getParentCommentId() != null) {
                        commentEntity.setParentCommentId(publishCommentDto.getParentCommentId());
                    }

                    return commentRepository.save(commentEntity).onErrorResume(error -> {
                        log.error("There was some problem with saving comment to database");
                        return Mono.error(CommentDatabaseFetchException::new);
                    }).then();
                });


    }




    private List<CommentDto> buildCommentTree(List<CommentDto> flatList) {
        Map<String, CommentDto> map = new HashMap<>();
        List<CommentDto> roots = new ArrayList<>();

        for (CommentDto comment : flatList) {
            map.put(comment.getId(), comment);
        }

        for (CommentDto comment : flatList) {
            if (comment.getParentCommentId() == null) {
                roots.add(comment);
            } else {
                CommentDto parent = map.get(comment.getParentCommentId());
                if (parent != null) {
                    parent.getReplies().add(comment);
                }
            }
        }

        return roots;
    }

}
