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
        FriendshipEntity friendshipEntity1 = new FriendshipEntity("1","1","2",null,null,null);
        FriendshipEntity friendshipEntity2 = new FriendshipEntity("2","2","1",null,null,null);
        FriendshipEntity friendshipEntity3 = new FriendshipEntity("3","1","3",null,null,null);
         friendshipRepository.saveAll(List.of(friendshipEntity1,friendshipEntity2,friendshipEntity3)).collectList().block();

    }




}
