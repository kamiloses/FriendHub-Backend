package com.kamiloses.userservice.service;

import com.kamiloses.userservice.dto.LoginDetails;
import com.kamiloses.userservice.dto.RegistrationDto;
import com.kamiloses.userservice.entity.UserEntity;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserEntity> save(RegistrationDto user);

    Mono<LoginDetails> findByUsernameOrId(String usernameOrId);
}