package com.kamiloses.commentservice.service;

import com.kamiloses.commentservice.dto.CommentDto;
import com.kamiloses.commentservice.dto.PublishCommentDto;
import com.kamiloses.commentservice.dto.UserDetailsDto;
import com.kamiloses.commentservice.entity.CommentEntity;
import com.kamiloses.commentservice.rabbit.RabbitCommentProducer;
import com.kamiloses.commentservice.repository.CommentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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
    public Flux<CommentDto> findCommentsRelatedWithPost(String postId){
        UserDetailsDto userDetails = rabbitCommentProducer.askForUserDetails(postId);
return         commentRepository.findCommentEntitiesByPostId(postId).map(commentEntity ->
            {
                UserDetailsDto user = new UserDetailsDto("1", "john_doe", "password123", "john.doe@example.com", "John", "Doe", "Software developer and tech enthusiast.", "https://example.com/profile.jpg", Set.of("tweet1", "tweet2", "tweet3"), Set.of("user2", "user3"), Set.of("user4", "user5"));

                CommentDto commentDto = new CommentDto();
                commentDto.setId(commentEntity.getId());
                commentDto.setContent(commentEntity.getContent());
                commentDto.setCreatedAt(new Date());
                commentDto.setUserDetails(user);
                commentDto.setPostId(commentEntity.getPostId());
                commentDto.setParentCommentId(commentEntity.getParentCommentId());
                commentDto.setParentCommentId(commentEntity.getParentCommentId());
                return  commentDto;
            }

    );




}


    public void publishPost(PublishCommentDto commentDto) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDto.getContent());
        commentEntity.setCreatedAt(commentDto.getCreatedAt());
        commentEntity.setUserId(commentDto.getUserDetails().getId());
        commentEntity.setPostId(commentDto.getPostId());
        commentEntity.setParentCommentId(commentDto.getParentCommentId());
        commentRepository.save(commentEntity).block();

    }
}
