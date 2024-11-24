package com.kamiloses.userservice.controller;
import com.kamiloses.userservice.service.UserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class UserControllerTest {
@Autowired
 private WebTestClient webTestClient;

    @Mock
    private RabbitTemplate rabbitTemplate;
    @InjectMocks
    private UserService userService;}

//INITIALLY WANTED to test the method via testontainers but i can't configure configuration for rabbitListenr
// which is in different module



//    @Test
//    void shouldCheckIfRegistrationProcessingWell() {
//        UserDetailsDto userDetailsDto = new UserDetailsDto();
//        RegistrationDto registrationDto = new RegistrationDto("Abcd", "123", "Jan", "Nowak");
//        UserDetailsDto.builder().username(registrationDto.getUsername()).password(registrationDto.getPassword()).firstName(registrationDto.getFirstName()).lastName(registrationDto.getLastName()).build();
//
//        doReturn(userDetailsDto).when(rabbitTemplate).convertSendAndReceive(anyString(), anyString(), anyString());
//
//
//
//        webTestClient.post().uri("api/user/signup").body(registrationDto, RegistrationDto.class).exchange().expectStatus().isOk()
//                .expectBody();
//
//    }
//
//
//    }