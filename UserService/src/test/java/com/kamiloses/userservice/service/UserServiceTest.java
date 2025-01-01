package com.kamiloses.userservice.service;

import com.kamiloses.userservice.dto.RegistrationDto;
import com.kamiloses.userservice.exception.UsernameAlreadyExistsException;
import com.kamiloses.userservice.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {


    @MockBean
    private RabbitTemplate rabbitTemplate;
    @SpyBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    RegistrationDto user = new RegistrationDto("kamiloses123", "kamiloses123", "Maciej", "Nowak");



    @BeforeEach
    void setUp() {
        Mockito.when(rabbitTemplate.convertSendAndReceive(any())).thenReturn(user.getPassword());
    }


    @Test
    void should_check_If_SaveMethod_Works() {
        userRepository.deleteAll().block();
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(Mono.just(false));



        userService.save(user).block();

        Assertions.assertEquals(1, userRepository.findAll().collectList().block().size());


    }

    @Test
    void should_check_if_SaveMethod_Throws_UsernameAlreadyExists() {
     //   Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(Mono.just(true));

        Assertions.assertThrows(UsernameAlreadyExistsException.class,()->userService.save(user).block());

    }


}