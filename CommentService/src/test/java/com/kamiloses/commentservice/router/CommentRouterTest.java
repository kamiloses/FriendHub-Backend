package com.kamiloses.commentservice.router;

import com.kamiloses.commentservice.dto.CommentDto;
import com.kamiloses.commentservice.dto.PublishCommentDto;
import com.kamiloses.commentservice.dto.UserDetailsDto;
import com.kamiloses.commentservice.entity.CommentEntity;
import com.kamiloses.commentservice.rabbit.RabbitCommentProducer;
import com.kamiloses.commentservice.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@AutoConfigureWebTestClient
@EnableDiscoveryClient(autoRegister = false)
@ActiveProfiles("test")
class CommentRouterTest {
    @Autowired
    private WebTestClient webTestClient;


    @Autowired
    private CommentRepository commentRepository;


    @MockBean
    private RabbitCommentProducer rabbitCommentProducer;

    private final UserDetailsDto userDetailsDto = new UserDetailsDto("1", "kamiloses", "Jan", "Kowalski");

    @BeforeEach
    public void setUp() {
        commentRepository.deleteAll().block();
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();

        Mockito.when(rabbitCommentProducer.askForUserDetails(anyString())).thenReturn(Mono.just(userDetailsDto));




    }

    @Test
    public void should_check_publishComment_method() {

        PublishCommentDto publishCommentDto = new PublishCommentDto("content1", "1", null);

        webTestClient.post()
                .uri("/api/comments?username=" + userDetailsDto.getUsername())
                .body(BodyInserters.fromValue(publishCommentDto))
                .exchange().expectStatus().isOk();


        assertEquals(1, commentRepository.findAll().collectList().block().size());

    }

    @Test
    public void should_check_findCommentsRelatedWithPost_method() {
        //related with other post
        CommentEntity commentEntity1 = new CommentEntity("1", "content1", null, "1", "2", null);
        CommentEntity commentEntity2 = new CommentEntity("2", "content1", null, "1", "2", "1");


        //related with postId 1
        CommentEntity commentEntity3 = new CommentEntity("3", "content1", null, "1", "1", "2");
        CommentEntity commentEntity4 = new CommentEntity("4", "content1", null, "1", "1", null);
        commentRepository.saveAll(List.of(commentEntity1, commentEntity2, commentEntity3, commentEntity4)).collectList().block();


        webTestClient.get()
                .uri("/api/comments/1" )
                .exchange().expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .hasSize(2);







    }


}