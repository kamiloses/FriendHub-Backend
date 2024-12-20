package com.kamiloses.messageservice.router;

import com.kamiloses.messageservice.MessageServiceApplication;
import com.kamiloses.messageservice.dto.MessageDto;
import com.kamiloses.messageservice.dto.SendMessageDto;
import com.kamiloses.messageservice.repository.MessageRepository;
import com.kamiloses.messageservice.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;


@SpringBootTest
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class MessageRouterTest {




    @Autowired
    private WebTestClient webTestClient;

     @Autowired
    private MessageRepository messageRepository;



    @Test
    void should_check_sendMessage_Works(){
        SendMessageDto sendMessageDto = new SendMessageDto("1","kamiloses","content");

        messageRepository.deleteAll().block();
        webTestClient.post().uri("/api/message").bodyValue(sendMessageDto).exchange()
                .expectStatus().isOk();

        StepVerifier.create(messageRepository.findAll())
                .expectNextCount(1)
                .verifyComplete();
    }

}