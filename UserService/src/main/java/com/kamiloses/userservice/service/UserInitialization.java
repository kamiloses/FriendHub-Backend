package com.kamiloses.userservice.service;

import com.kamiloses.userservice.entity.UserEntity;
import com.kamiloses.userservice.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class UserInitialization {

private final UserRepository userRepository;

    public UserInitialization(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findAll().collectList().block().size()==0) {
            UserEntity user1 = new UserEntity("1", "marcin", "123", "kamiloses@mail.com", "Kamil", "Oses", "Developer", "url1", new HashSet<>(), new HashSet<>(), new HashSet<>());
            UserEntity user2 = new UserEntity("2", "adam", "123", "johndoe@mail.com", "John", "Doe", "Software Engineer", "url2", new HashSet<>(), new HashSet<>(), new HashSet<>());
            UserEntity user3 = new UserEntity("3", "maciej", "123", "janedoe@mail.com", "Jane", "Doe", "Product Manager", "url3", new HashSet<>(), new HashSet<>(), new HashSet<>());
            UserEntity user4 = new UserEntity("4", "jan", "123", "janedoe@mail.com", "Jane", "Doe", "Product Manager", "url3", new HashSet<>(), new HashSet<>(), new HashSet<>());
            UserEntity user5 = new UserEntity("5", "piotr", "123", "janedoe@mail.com", "Jane", "Doe", "Product Manager", "url3", new HashSet<>(), new HashSet<>(), new HashSet<>());
             userRepository.saveAll(List.of(user1, user2, user3,user4,user5)).collectList().block();
        }


    }





}
