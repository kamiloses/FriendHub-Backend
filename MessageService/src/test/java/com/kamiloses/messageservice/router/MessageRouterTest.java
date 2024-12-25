package com.kamiloses.messageservice.router;

import com.kamiloses.messageservice.dto.MessageDto;
import com.kamiloses.messageservice.dto.SendMessageDto;
import com.kamiloses.messageservice.dto.UserDetailsDto;
import com.kamiloses.messageservice.repository.MessageRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
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

    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();


    }


    @Test
    @Order(1)
    void should_check_sendMessage_Works() {
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


        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUsername("kamiloses");

        MessageDto.builder()
                .chatId("1")
                .sender(userDetailsDto)
                .content("content")
                .build();


        webTestClient.get().uri("/api/message/1").exchange()
                .expectStatus().isOk().expectBodyList(MessageDto.class)
                .hasSize(1)

        ;


    }


}