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
        FriendshipEntity friendshipEntity1 = new FriendshipEntity("1","67470e35190aac51104c4565","67470e63190aac51104c4566",null,null,null);
         friendshipRepository.saveAll(List.of(friendshipEntity1)).collectList().block();

    }




}
