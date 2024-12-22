package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.entity.FriendshipEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
class FriendsServiceTest {


     @Autowired
    private FriendsService friendsService;

    private List<FriendshipEntity> friendshipEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {

    }

    @Test
    void should_check_getPeopleWithSimilarUsername() {




    }




}