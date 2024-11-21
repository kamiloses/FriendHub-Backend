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
        FriendshipEntity friendshipEntity1 = new FriendshipEntity("1","673e829b7cbf3a6de6551280","673e82ab7cbf3a6de6551281",null,null,null);
         friendshipRepository.saveAll(List.of(friendshipEntity1)).collectList().block();

    }




}
