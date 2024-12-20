package com.kamiloses.authservice;

import com.kamiloses.authservice.dto.AuthResponse;
import com.kamiloses.authservice.dto.LoginDetails;
import com.kamiloses.authservice.dto.UserDetailsDto;
import com.kamiloses.authservice.rabbit.RabbitAuthProducer;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static reactor.core.publisher.Mono.when;

@SpringBootTest()
@AutoConfigureWebTestClient
@EnableDiscoveryClient(autoRegister = false)
class LoginControllerTest {


//todo Sprawdz jak zamockować webClient w metodzie login by nie musieć mieć modułu user ciągle włączonego

    @Autowired
    WebTestClient webTestClient;





    @Test
    void shouldLoginSuccessfully() {
        LoginDetails loginRequest = new LoginDetails("kamiloses", "kamiloses");
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId("1");
        userDetailsDto.setUsername("kamiloses");
        userDetailsDto.setPassword("kamiloses");



      // Mockito.when(rabbitAuthProducer.askForUserDetails(loginRequest.getUsername())).thenReturn(userDetailsDto);



        AuthResponse response = webTestClient.post()
                .uri("/api/login")
                .body(BodyInserters.fromValue(loginRequest))
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponse.class)
                .returnResult()
                .getResponseBody();


        assertThat(response.getToken()).isNotBlank();
    }

//    @Test
//    void shouldNotLogin() {
//
//        LoginDetails loginDetails = new LoginDetails("Wrong username", "Wrong password");
//
//        webTestClient.post()
//                .uri("/api/user/login")
//                .body(BodyInserters.fromValue(loginDetails))
//                .exchange()
//                .expectStatus().isUnauthorized();
//    }

}