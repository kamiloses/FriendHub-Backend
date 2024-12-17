package com.kamiloses.authservice;

import com.kamiloses.authservice.dto.LoginDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest()
@AutoConfigureWebTestClient
class LoginControllerTest {



    @Autowired
    WebTestClient webTestClient;
    // todo Pamiętaj że tego użytkownika mam w bazie danych już,
    //  potem postaraj sie go w klasie testowej stworzyć

    @Test
    void shouldLoginSuccessfully() {
        LoginDetails loginDetails = new LoginDetails("kamiloses", "123");


        LoginDetails response = webTestClient.post()
                .uri("/api/user/login")
                .body(BodyInserters.fromValue(loginDetails))
                .exchange()
                .expectStatus().isOk()
                .expectBody(LoginDetails.class)
                .returnResult()
                .getResponseBody();

        // assertThat(response.token()).isNotBlank();
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