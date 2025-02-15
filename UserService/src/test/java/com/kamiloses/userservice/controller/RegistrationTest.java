package com.kamiloses.userservice.controller;

import com.kamiloses.userservice.dto.RegistrationDto;
import com.kamiloses.userservice.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegistrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private UserRepository userRepository;
    private RegistrationDto registrationDto;


      @MockBean
      private RabbitTemplate rabbitTemplate;


    @BeforeEach
    public void setUp() {

        registrationDto = new RegistrationDto("kamiloses", "kamiloses123", "Jan", "Nowak");


        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();

        Mockito.when(rabbitTemplate.convertSendAndReceive(anyString(),anyString(),anyString())).thenReturn(registrationDto.getPassword());
    }


    @Test
    @Order(1)
    void should_return_successful_signup() {

        userRepository.deleteAll().block();
        webTestClient.post().uri("/api/user/signup").bodyValue(registrationDto).exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.message").isEqualTo("User signed up successfully");

        Assertions.assertEquals(1, userRepository.findAll().count().block());

    }

    @Test
    @Order(2)
    void should_return_UsernameDoesExist() {
        webTestClient.post().uri("/api/user/signup").bodyValue(registrationDto).exchange().expectStatus().
                isBadRequest().expectBody(List.class).isEqualTo(List.of("this Username already exists"));



        Assertions.assertEquals(1, userRepository.findAll().count().block());
    }

        @Test
        @Order(3)
        void should_return_validPasswordAndFirstName(){
        registrationDto.setFirstName("123sda");
        registrationDto.setPassword("1");
            webTestClient.post().uri("/api/user/signup").bodyValue(registrationDto).exchange().expectStatus().
                    isBadRequest().expectBody(List.class).isEqualTo(List.of("Password cannot be blank and must be at least 6 characters long.","First Name cannot be blank and must only contain letters."));
//sometimes it returns in different order

            Assertions.assertEquals(1, userRepository.findAll().count().block());

        }




    }













