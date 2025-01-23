package com.kamiloses.likeservice.router;

import com.kamiloses.likeservice.dto.UserDetailsDto;
import com.kamiloses.likeservice.rabbit.RabbitLikeProducer;
import com.kamiloses.likeservice.repository.LikeRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class LikeRouterTest {

UserDetailsDto user =new UserDetailsDto("1","kamiloses",null,false,"Jan","Nowak");


    @MockitoBean
    RabbitLikeProducer rabbitLikeProducer;


    @Autowired
    WebTestClient webTestClient;

    @Autowired
    LikeRepository likeRepository;

    @BeforeEach
    void setUp() {

        Mockito.when(rabbitLikeProducer.askForUserDetails(anyString())).thenReturn(Mono.just(user));

        Mockito.when(rabbitLikeProducer.sendPostIdForLikeAdding(anyString())).thenReturn(Mono.empty());

        Mockito.when(rabbitLikeProducer.sendPostIdForLikeRemoval(anyString())).thenReturn(Mono.empty());


    }




    @Test
    @Order(1)
    void should_LikeThePost(){
        likeRepository.deleteAll().block();
        webTestClient.post()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/like").queryParam("postId", 1).queryParam("username", user.getUsername()).build()
                ).exchange().expectStatus().isOk();

        Assertions.assertEquals(1, likeRepository.findAll().count().block());



    }



    @Test
    @Order(2)
    void should_UndoLike(){
        webTestClient.delete()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/like").queryParam("postId", 1).queryParam("username", user.getUsername()).build()
                ).exchange().expectStatus().isOk();

        Assertions.assertEquals(0, likeRepository.findAll().count().block());





    }
}