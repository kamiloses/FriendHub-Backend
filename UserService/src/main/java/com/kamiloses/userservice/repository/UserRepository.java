package com.kamiloses.userservice.repository;

import com.kamiloses.userservice.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<UserEntity,String> {

Mono<UserEntity> findByUsername(String username);

}
