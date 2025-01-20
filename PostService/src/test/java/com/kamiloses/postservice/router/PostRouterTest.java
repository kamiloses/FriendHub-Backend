package com.kamiloses.postservice.router;

import com.kamiloses.postservice.dto.CreatePostDto;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import com.kamiloses.postservice.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@AutoConfigureWebTestClient
@EnableDiscoveryClient(autoRegister = false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")

class PostRouterTest {

    @Autowired
    private WebTestClient webTestClient;


    @Autowired
    private PostRepository postRepository;

    private CreatePostDto createPostDto;

    private static final String username = "kamiloses";


    @MockBean
    private RabbitPostProducer rabbitPostProducer;

    private final UserDetailsDto userDetailsDto = new UserDetailsDto("1", "kamiloses", null, false, "Jan", "Kowalski");

    @BeforeEach
    public void setUp() {

        Mockito.when(rabbitPostProducer.askForUserDetails(anyString())).thenReturn(Mono.just(userDetailsDto));

        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();


        createPostDto = new CreatePostDto("text");

    }


    @Test
    @Order(1)
    public void should_Check_CreatePost_Method() {
        postRepository.deleteAll().block();
        webTestClient.post().uri("/api/posts/" + username).bodyValue(createPostDto).exchange()
                .expectStatus().isOk();

        StepVerifier.create(postRepository.findAll())
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    @Order(2)
    public void should_Check_GetAllPosts_Method() {
        webTestClient.get().uri("/api/posts?username="+username).exchange().expectStatus().isOk().expectBody(String.class)
                .consumeWith(response -> {
                    String body = response.getResponseBody();
                    assertNotNull(body);
                    assertTrue(body.contains("text"));
                });


    }
@Test
    @Order(3)
    public void should_Check_GetPostById_Method() {
    PostEntity postEntity = postRepository.findAll().collectList().block().get(0);
    webTestClient.get().uri("/api/posts/" +postEntity.getId()).exchange()
            .expectStatus().isOk().expectBody(String.class)
            .consumeWith(response -> {
                String body = response.getResponseBody();
                assertNotNull(body);
                assertTrue(body.contains("text"));
            });
    ;

}




}