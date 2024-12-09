package com.kamiloses.commentservice.service;

import com.kamiloses.commentservice.dto.CommentDto;
import com.kamiloses.commentservice.dto.PublishCommentDto;
import com.kamiloses.commentservice.dto.UserDetailsDto;
import com.kamiloses.commentservice.entity.CommentEntity;
import com.kamiloses.commentservice.rabbit.RabbitCommentProducer;
import com.kamiloses.commentservice.repository.CommentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Set;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final RabbitCommentProducer rabbitCommentProducer;

    public CommentService(CommentRepository commentRepository, RabbitCommentProducer rabbitCommentProducer) {
        this.commentRepository = commentRepository;
        this.rabbitCommentProducer = rabbitCommentProducer;
    }

    //todo popraw
    public Flux<CommentDto> findCommentsRelatedWithPost(String postId) {
        UserDetailsDto userDetails = rabbitCommentProducer.askForUserDetails(postId);
        return commentRepository.findCommentEntitiesByPostId(postId).map(commentEntity ->
                {

                    CommentDto commentDto = new CommentDto();
                    commentDto.setId(commentEntity.getId());
                    commentDto.setContent(commentEntity.getContent());
                    commentDto.setCreatedAt(new Date());
                //    commentDto.setUserDetails(user);
                    commentDto.setPostId(commentEntity.getPostId());
                    commentDto.setParentCommentId(commentEntity.getParentCommentId());
                    commentDto.setParentCommentId(commentEntity.getParentCommentId());
                    return commentDto;
                }

        );


    }

    public Mono<Void> publishComment(PublishCommentDto publishCommentDto, String username) {


        return Mono.fromSupplier(() -> rabbitCommentProducer.askForUserDetails(username))
                .flatMap(userDetails -> {

                    CommentEntity commentEntity = CommentEntity.builder()
                            .content(publishCommentDto.getContent())
                            .postId(publishCommentDto.getPostId())
                            .userId(userDetails.getId())
                            .createdAt(new Date()).build();
                    return commentRepository.save(commentEntity).then();
                });


    }
}
