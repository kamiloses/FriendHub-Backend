package com.kamiloses.messageservice.router;

import com.kamiloses.messageservice.dto.MessageDto;
import com.kamiloses.messageservice.dto.SendMessageDto;
import com.kamiloses.messageservice.dto.UserDetailsDto;
import com.kamiloses.messageservice.entity.MessageEntity;
import com.kamiloses.messageservice.rabbit.RabbitMessageProducer;
import com.kamiloses.messageservice.repository.MessageRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;


@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MessageRouterTest {


    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MessageRepository messageRepository;

    @MockBean
    private RabbitMessageProducer rabbitMessageProducer;


    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();


        UserDetailsDto userDetailsDto = new UserDetailsDto("1","kamiloses","1","Jan","Nowak");
        Mockito.when(rabbitMessageProducer.askForUserDetails(Mockito.any())).thenReturn(Mono.just(userDetailsDto));
    }


    @Test
    @Order(1)
    void should_check_sendMessage() {
        messageRepository.deleteAll().block();
        SendMessageDto sendMessageDto = new SendMessageDto("1", "kamiloses", "content");
        webTestClient.post().uri("/api/message").bodyValue(sendMessageDto).exchange()
                .expectStatus().isOk();

        StepVerifier.create(messageRepository.findAll())
                .expectNextCount(1)
                .verifyComplete();
    }


    @Test
    @Order(2)
    void should_Check_showMessagesRelatedWithChat() {
        webTestClient.get().uri("/api/message/1").exchange()
                .expectStatus().isOk().expectBodyList(MessageDto.class)
                .hasSize(1)

        ;


    }


}