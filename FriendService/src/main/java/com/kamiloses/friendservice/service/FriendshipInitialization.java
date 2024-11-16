package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class FriendshipInitialization {

    private final FriendshipRepository friendshipRepository;

    public FriendshipInitialization(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @PostConstruct
    public void init() {
        FriendshipEntity friendshipEntity = new FriendshipEntity();
        FriendshipEntity friendshipEntity = new FriendshipEntity();
        FriendshipEntity friendshipEntity = new FriendshipEntity();


    }




}
