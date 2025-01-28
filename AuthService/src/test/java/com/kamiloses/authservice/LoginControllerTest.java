package com.kamiloses.authservice;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import com.kamiloses.authservice.controller.AuthController;
import com.kamiloses.authservice.dto.AuthResponse;
import com.kamiloses.authservice.dto.LoginDetails;
import com.kamiloses.authservice.dto.UserDetailsDto;
import com.kamiloses.authservice.rabbit.RabbitAuthProducer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static reactor.core.publisher.Mono.when;



@SpringBootTest
@AutoConfigureWebTestClient
@WireMockTest(httpPort = 8081)
//todo dodaj konfuguracje testową
class LoginControllerTest {


    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    PasswordEncoder passwordEncoder;


    @Test
    void shouldLoginSuccessfully() {
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/api/user/kamiloses"))
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(HttpStatus.OK.value())
                        .withBody("{\"username\":\"kamiloses\", \"password\":\"$2a$10$/0mQ8xdA/8PEyjEtjfy57.v5JL0hNEbL7dqKk6TiYAC.XKBDtY20C\"}")));

        Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);





        LoginDetails loginRequest = new LoginDetails("kamiloses", "kamiloses");
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId("1");
        userDetailsDto.setUsername("kamiloses");
        userDetailsDto.setPassword("$2a$10$/0mQ8xdA/8PEyjEtjfy57.v5JL0hNEbL7dqKk6TiYAC.XKBDtY20C");




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


    @Test
    void shouldNotLogin() {

        LoginDetails loginDetails = new LoginDetails("WrongUsername", "WrongPassword");

        webTestClient.post()
                .uri("/api/user/login")
                .body(BodyInserters.fromValue(loginDetails))
                .exchange()
                .expectStatus().isUnauthorized();
    }

}