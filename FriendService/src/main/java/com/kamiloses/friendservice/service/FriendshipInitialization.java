package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FriendshipInitialization {

    private final FriendshipRepository friendshipRepository;

    public FriendshipInitialization(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @PostConstruct
    public void init() {
        FriendshipEntity friendshipEntity1 = new FriendshipEntity("1","67428dabc29ece16da45e9f7","67428f82c29ece16da45e9f8",null,null,null);
        FriendshipEntity friendshipEntity2 = new FriendshipEntity("2","67428dabc29ece16da45e9f7","67429928629b3959ef671a93",null,null,null);
         friendshipRepository.saveAll(List.of(friendshipEntity1,friendshipEntity2)).collectList().block();

    }




}
