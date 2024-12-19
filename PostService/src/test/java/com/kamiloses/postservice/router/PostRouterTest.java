package com.kamiloses.postservice.router;

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

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureWebTestClient
@EnableDiscoveryClient(autoRegister = false)
class PostRouterTest {

    @Autowired
    private WebTestClient webTestClient;



    @Autowired
    private PostRepository postRepository;

    private CreatePostDto createPostDto;

    private static final String username="kamiloses";

    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();


        createPostDto = new CreatePostDto("text");

    }


    @Test
    public void should_Check_CreatePost_Method() {
        postRepository.deleteAll().block();
        webTestClient.post().uri("/api/posts/" + username).bodyValue(createPostDto).exchange()
                .expectStatus().isOk();

        StepVerifier.create(postRepository.findAll())
                .expectNextCount(1)
                .verifyComplete();


    }

    @Test
    public void should_Check_GetAllPosts_Method() {
     webTestClient.get().uri("/api/posts").exchange().expectStatus().isOk().expectBody(String.class)
             .consumeWith(response -> {
                 String body = response.getResponseBody();
                 assertNotNull(body);
                 assertTrue(body.contains("text"));
             });



    }





}