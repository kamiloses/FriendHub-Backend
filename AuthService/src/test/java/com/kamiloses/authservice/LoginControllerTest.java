package com.kamiloses.authservice;

import com.kamiloses.authservice.security.jwt.LoginDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginControllerTest {



    @Autowired
    WebTestClient webTestClient;

    @Test
    void shouldLogin() {
        // Pamiętaj że tego użytkownika mam w bazie danych już, potem postaraj sie go w klasie testowej stworzyć
        LoginDetails loginDetails = new LoginDetails("kamiloses", "123");


        LoginDetails response = webTestClient.post()
                .uri("/api/user/login")
                .body(BodyInserters.fromValue(loginDetails))
                .exchange()
                .expectStatus().isOk()
                .expectBody(LoginDetails.class)
                .returnResult()
                .getResponseBody();

         assertThat(response.token()).isNotBlank();
    }

    @Test
    void shouldNotLoginWithWrongPassword() {

        LoginDetails loginDetails = new LoginDetails("Wrong username", "Wrong password");

        webTestClient.post()
                .uri("/api/user/login")
                .body(BodyInserters.fromValue(loginDetails))
                .exchange()
                .expectStatus().isUnauthorized();
    }

}