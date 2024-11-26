package com.kamiloses.userservice.controller;

import com.kamiloses.userservice.dto.RegistrationDto;
import com.kamiloses.userservice.repository.UserRepository;
import com.kamiloses.userservice.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;


@SpringBootTest
//@ActiveProfiles("test")
@AutoConfigureWebTestClient
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private UserRepository userRepository;
    private RegistrationDto registrationDto;


    @BeforeEach
    public void setUp() {

        registrationDto = new RegistrationDto("kamiloses", "123", "Jan", "Nowak");


        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
    }


    @Test
    void should_return_successful_signup() {

        userRepository.deleteAll().block();
        webTestClient.post().uri("/api/user/signup").bodyValue(registrationDto).exchange().expectStatus().isOk()
                .expectBody(String.class).isEqualTo("User signed up successfully");

        Assertions.assertEquals(1, userRepository.findAll().count().block());

    }

    //todo dodaj potem drugi przypadek w sytuacji gdy sie nie zarejestrujemy .




}