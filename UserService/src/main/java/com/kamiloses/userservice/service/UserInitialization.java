package com.kamiloses.userservice.service;

import com.kamiloses.userservice.entity.UserEntity;
import com.kamiloses.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashSet;
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
            UserEntity user1 = new UserEntity("1", "kamil", "123", "kamiloses@mail.com", "Kamil", "A", "Developer", "url1", new HashSet<>(), new HashSet<>(), new HashSet<>());
            UserEntity user2 = new UserEntity("2", "adam", "123", "adam@mail.com", "Adam", "B", "Developer", "url2", new HashSet<>(), new HashSet<>(), new HashSet<>());
             userRepository.saveAll(List.of(user1, user2)).collectList().block();
        }


    }





}
