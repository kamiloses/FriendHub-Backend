package com.kamiloses.friendservice.controller;

import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import com.kamiloses.friendservice.service.FriendsService;
import com.kamiloses.friendservice.service.RabbitFriendsProducer;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Disabled("UnsatisfiedDependencyException-WebTestClient")
class FriendsControllerTest {


      //most likely websockets cause inability to test the router.


    @Autowired
    private FriendshipRepository friendshipRepository;

    @MockBean
    private RabbitFriendsProducer rabbitFriendsProducer;

    @Autowired
    WebTestClient webTestClient;




    private UserDetailsDto myUserDetails=new UserDetailsDto("1",null,"JNowak",false,"Jan","Nowak");
    private UserDetailsDto friendDetails=new UserDetailsDto("2",null,"MKowalski",false,"Maciej","Kowalski");


    @Test
    @Order(1)
    public void should_check_addToFriend() {
        friendshipRepository.deleteAll().block();
        Mockito.when(rabbitFriendsProducer.askForUserDetails(friendDetails.getUsername())).thenReturn(Mono.just(friendDetails));
        Mockito.when(rabbitFriendsProducer.askForUserDetails(myUserDetails.getUsername())).thenReturn(Mono.just(myUserDetails));




       webTestClient.post().uri("/api/friends").header("friendUsername", friendDetails.getUsername())
               .header("myUsername",myUserDetails.getUsername()).exchange().expectStatus().isOk();

        Assertions.assertEquals(1,friendshipRepository.findAll().collectList().block().size());
    }



    @Test
    @Order(2)
    public void should_check_removeFromFriends() {
        Mockito.when(rabbitFriendsProducer.askForUserDetails(friendDetails.getUsername())).thenReturn(Mono.just(friendDetails));
        Mockito.when(rabbitFriendsProducer.askForUserDetails(myUserDetails.getUsername())).thenReturn(Mono.just(myUserDetails));


        webTestClient.delete().uri("/api/friends").header("friendUsername", friendDetails.getUsername())
                .header("myUsername",myUserDetails.getUsername()).exchange().expectStatus().isOk();

        Assertions.assertEquals(0,friendshipRepository.findAll().collectList().block().size());
    }







    }


