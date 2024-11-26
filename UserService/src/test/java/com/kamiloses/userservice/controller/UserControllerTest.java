package com.kamiloses.userservice.controller;
import com.kamiloses.userservice.dto.RegistrationDto;
import com.kamiloses.userservice.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.RabbitMQContainer;

import java.time.Duration;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class UserControllerTest {

    @Autowired
 private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
    }



    @Test
    void should_Check_If_Registration_Works() {
        RegistrationDto registrationDto = new RegistrationDto("kamiloses", "123", "Jan", "Nowak");





        webTestClient.post().uri("/api/user/signup").bodyValue(registrationDto).exchange().expectStatus().isOk()
                .expectBody(String.class).isEqualTo("User signed up successfully");
    }


    }