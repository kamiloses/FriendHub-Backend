package com.kamiloses.postservice.controller;

import com.kamiloses.postservice.dto.CreatePostDto;
import com.kamiloses.postservice.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureWebTestClient
@EnableDiscoveryClient(autoRegister = false)
class PostControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    private CreatePostDto createPostDto;
    private String username;
     @Autowired
     private  PostRepository postRepository;

    // Pamiętaj że uzytkownik już itnieje w bazie danych
    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();


        createPostDto = new CreatePostDto();
        createPostDto.setContent("Content");
        createPostDto.setCreatedAt(LocalDateTime.now());
        username = "Piotr";

    }
    @Test
    public void should_Check_If_You_Can_Create_Post() {
          postRepository.deleteAll().block();
     webTestClient.post().uri("/api/posts/"+username).bodyValue(createPostDto).exchange()
             .expectStatus().isOk();

         assertEquals(1,postRepository.findAll().collectList().block().size());


    }




}