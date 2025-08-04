package com.kamiloses.userservice.service.impl;

import com.kamiloses.userservice.entity.UserEntity;
import com.kamiloses.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;

import java.util.List;

//@Component
public class UserInitialization {

private final UserRepository userRepository;

    public UserInitialization(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findAll().collectList().block().size()==0) {
            UserEntity user1 = new UserEntity("1", "kamiloses", "kamiloses", "Kamil", "A");
            UserEntity user2 = new UserEntity("2", "adam", "123", "Adam", "B");
             userRepository.saveAll(List.of(user1, user2)).collectList().block();
        }


    }





}
