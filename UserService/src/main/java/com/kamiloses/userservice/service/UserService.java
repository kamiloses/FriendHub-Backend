package com.kamiloses.userservice.service;

import com.kamiloses.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {


private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<Boolean> existsByUsernameAndPassword(String username,String password){


    return userRepository.existsByUsernameAndPassword(username,password);}

}
