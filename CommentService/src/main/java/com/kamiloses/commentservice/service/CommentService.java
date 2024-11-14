package com.kamiloses.commentservice.service;

import com.kamiloses.commentservice.dto.CommentDto;
import com.kamiloses.commentservice.dto.UserDetailsDto;
import com.kamiloses.commentservice.rabbit.RabbitCommentProducer;
import com.kamiloses.commentservice.repository.CommentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Date;

@Service
public class CommentService {

private final CommentRepository commentRepository;
private final RabbitCommentProducer rabbitCommentProducer;
    public CommentService(CommentRepository commentRepository, RabbitCommentProducer rabbitCommentProducer) {
        this.commentRepository = commentRepository;
        this.rabbitCommentProducer = rabbitCommentProducer;
    }
//todo popraw
    public Flux<CommentDto> findCommentsRelatedWithPost(String postId){
        UserDetailsDto userDetails = rabbitCommentProducer.askForUserDetails(postId);
return         commentRepository.findCommentEntitiesByPostId(postId).map(commentEntity ->
            {

                CommentDto commentDto = new CommentDto();
                commentDto.setId(commentEntity.getId());
                commentDto.setContent(commentEntity.getContent());
                commentDto.setCreatedAt(new Date());
                commentDto.setUserDetails(userDetails);
                commentDto.setPostId(commentEntity.getPostId());
                commentDto.setParentCommentId(commentEntity.getParentCommentId());
                commentDto.setParentCommentId(commentEntity.getParentCommentId());
                return  commentDto;
            }

    );




}







}
