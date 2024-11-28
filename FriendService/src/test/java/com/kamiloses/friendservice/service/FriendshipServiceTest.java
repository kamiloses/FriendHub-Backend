package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
class FriendshipServiceTest {


     @Autowired
    private FriendshipService friendshipService;

    private List<FriendshipEntity> friendshipEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {
        FriendshipEntity friendshipEntity1 = new FriendshipEntity("1", "1", "2", null, null, null);
        FriendshipEntity friendshipEntity2 = new FriendshipEntity("2", "2", "1", null, null, null);
        FriendshipEntity friendshipEntity3 = new FriendshipEntity("3", "3", "1", null, null, null);
        friendshipEntities.addAll(List.of(friendshipEntity1, friendshipEntity2, friendshipEntity3));
    }

    //todo teraz przesyłam już obiekt wieć zmienić musze liste.
    @Test
    void should_check_getYourFriendsIds_method() {
        List<FriendShipDto> yourFriendsId = friendshipService.getYourFriendsId(Flux.fromIterable(friendshipEntities), "1").block();


        Assertions.assertEquals(List.of("2","2","3"),yourFriendsId);



    }
    @Test
    void should_check_getYourFriendsIds_method_Return_NUll() {
        List<FriendShipDto> yourFriendsId = friendshipService.getYourFriendsId(Flux.fromIterable(friendshipEntities), "10").block();


        Assertions.assertEquals(List.of(),yourFriendsId);



    }



}