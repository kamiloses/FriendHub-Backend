package com.kamiloses.commentservice.router;

import com.kamiloses.commentservice.dto.PublishCommentDto;
import com.kamiloses.commentservice.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureWebTestClient
@EnableDiscoveryClient(autoRegister = false)
class CommentRouterTest {
    @Autowired
    private WebTestClient webTestClient;



    @Autowired
    private CommentRepository commentRepository;


    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();




    }
      @Test
      @Disabled
    public void should_check_if_PublishComments_works(){
         commentRepository.deleteAll().block();
          PublishCommentDto publishCommentDto = new PublishCommentDto("content","1",null);

          webTestClient.post()
                  .uri("/api/comments/kamiloses")
                  .body(BodyInserters.fromValue(publishCommentDto))
                  .exchange().expectStatus().isOk();


          assertEquals(1,commentRepository.findAll().collectList().block().size());

      }




}