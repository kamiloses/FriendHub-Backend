package com.kamiloses.likeservice.repository;

import com.kamiloses.likeservice.entity.LikeEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LikeRepository extends ReactiveMongoRepository<LikeEntity,String> {
}
