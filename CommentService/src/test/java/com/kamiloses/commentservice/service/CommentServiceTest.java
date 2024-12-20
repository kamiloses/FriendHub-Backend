package com.kamiloses.commentservice.service;

import com.kamiloses.commentservice.dto.PublishCommentDto;
import com.kamiloses.commentservice.dto.UserDetailsDto;
import com.kamiloses.commentservice.entity.CommentEntity;
import com.kamiloses.commentservice.exception.CommentDatabaseFetchException;
import com.kamiloses.commentservice.rabbit.RabbitCommentProducer;
import com.kamiloses.commentservice.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {




    @Mock
    private RabbitCommentProducer rabbitCommentProducer;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private String username="Jnowak";


    @Test
    void should_Publish_Comment_Successfully() {
        PublishCommentDto comment = new PublishCommentDto("Test", "1", null);
        UserDetailsDto userDetails = new UserDetailsDto("1", "Jnowak","Jan","Nowak");
        CommentEntity expectedEntity = CommentEntity.builder()
                .content(comment.getContent())
                .postId(comment.getPostId())
                .userId(userDetails.getId())
                .createdAt(new Date())
                .build();




        when(rabbitCommentProducer.askForUserDetails(username)).thenReturn(userDetails);
        when(commentRepository.save(any(CommentEntity.class))).thenReturn(Mono.just(expectedEntity));




        StepVerifier.create(commentService.publishComment(comment, username))
                .verifyComplete();
    }



    @Test
    void should_throw_Error() {
        PublishCommentDto comment = new PublishCommentDto("Test", "1", null);
        UserDetailsDto userDetails = new UserDetailsDto("1", "Jnowak","Jan","Nowak");




        when(rabbitCommentProducer.askForUserDetails(username)).thenReturn(userDetails);
        when(commentRepository.save(any(CommentEntity.class)))
                .thenReturn(Mono.error(new RuntimeException()));




        StepVerifier.create(commentService.publishComment(comment, username))
                .expectError(CommentDatabaseFetchException.class)
                .verify();
    }




}