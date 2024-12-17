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

import java.util.Date;

@Service @Import(RabbitExceptionHandler.class)
@Slf4j
public class CommentService {

    private final CommentRepository commentRepository;
    private final RabbitCommentProducer rabbitCommentProducer;

    public CommentService(CommentRepository commentRepository, RabbitCommentProducer rabbitCommentProducer) {
        this.commentRepository = commentRepository;
        this.rabbitCommentProducer = rabbitCommentProducer;
    }


    public Flux<CommentDto> findCommentsRelatedWithPost(String postId) {
        return commentRepository.findCommentEntitiesByPostId(postId).onErrorResume(error->{
                    log.error("There was some problem with fetching comments related with post");
                    return Mono.error(()->new CommentDatabaseFetchException("There was some problem with fetching comments related with post"));
                })
                .flatMap(commentEntity -> Mono.fromSupplier(() -> rabbitCommentProducer.askForUserDetails(commentEntity.getUserId()))
                        .map(userDetailsDto -> {
                            CommentDto commendDto = CommentDto.builder()
                                    .id(commentEntity.getId())
                                    .content(commentEntity.getContent())
                                    .createdAt(commentEntity.getCreatedAt())
                                    .userDetails(userDetailsDto)
                                    .postId(commentEntity.getPostId())
                                    .parentCommentId(commentEntity.getParentCommentId()).build();

                            return commendDto;
                        }));


    }

    public Mono<Void> publishComment(PublishCommentDto publishCommentDto, String username) {


        return Mono.fromSupplier(() -> rabbitCommentProducer.askForUserDetails(username))
                .flatMap(userDetails -> {

                    CommentEntity commentEntity = CommentEntity.builder()
                            .content(publishCommentDto.getContent())
                            .postId(publishCommentDto.getPostId())
                            .userId(userDetails.getId())
                            .createdAt(new Date()).build();


                    if (publishCommentDto.getParentCommentId() != null) {
                        commentEntity.setParentCommentId(publishCommentDto.getParentCommentId());
                    }

                    return commentRepository.save(commentEntity).onErrorResume(error->{
                        log.error("There was some problem with saving comment to database");
                        return Mono.error(()->new CommentDatabaseFetchException("There was some problem with saving comment to database"));
                    }).then();
                });


    }


}
