package com.kamiloses.userservice.repository;

import com.kamiloses.userservice.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserRepository extends ReactiveMongoRepository<UserEntity,String> {

Mono<UserEntity> findByUsername(String username);

Mono<Boolean> existsByUsernameAndPassword(String username,String password);

Flux<UserEntity> findUserEntitiesByIdIn(List<String> userIds);

    Mono<Boolean> existsByUsername(String username);
}
