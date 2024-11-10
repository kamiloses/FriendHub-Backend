package com.kamiloses.userservice.repository;

import com.kamiloses.userservice.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserRepository extends ReactiveMongoRepository<UserEntity,String> {
}
