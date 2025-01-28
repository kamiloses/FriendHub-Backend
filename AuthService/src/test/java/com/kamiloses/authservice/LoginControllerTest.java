package com.kamiloses.authservice;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.kamiloses.authservice.dto.AuthResponse;
import com.kamiloses.authservice.dto.LoginDetails;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;


@SpringBootTest
@AutoConfigureWebTestClient
@WireMockTest(httpPort = 8081)
@ActiveProfiles("test")
class LoginControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    PasswordEncoder passwordEncoder;


    @Test
    void shouldLoginSuccessfully() {
        LoginDetails loginDetails = new LoginDetails("kamiloses", "kamiloses");

        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/user/" + loginDetails.getUsername()))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(HttpStatus.OK.value())
                        .withBody("{\"username\":\"kamiloses\", \"password\":\"$2a$10$/0mQ8xdA/8PEyjEtjfy57.v5JL0hNEbL7dqKk6TiYAC.XKBDtY20C\"}")));

        Mockito.when(passwordEncoder.matches(loginDetails.getPassword(), "$2a$10$/0mQ8xdA/8PEyjEtjfy57.v5JL0hNEbL7dqKk6TiYAC.XKBDtY20C")).thenReturn(true);


        AuthResponse response = webTestClient.post()
                .uri("/api/login")
                .body(BodyInserters.fromValue(loginDetails))
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(response.getToken()).isNotBlank();
        System.err.println(response);
    }


    @Test
    void shouldNotLogin() {

        LoginDetails loginDetails = new LoginDetails("WrongUsername", "WrongPassword");


        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/user/WrongUsername"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);


        webTestClient.post()
                .uri("/api/login")
                .body(BodyInserters.fromValue(loginDetails))
                .exchange()
                .expectStatus().isUnauthorized();
    }

}