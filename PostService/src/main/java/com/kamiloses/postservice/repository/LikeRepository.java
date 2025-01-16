package com.kamiloses.postservice.repository;

import com.kamiloses.postservice.entity.LikeEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LikeRepository extends ReactiveMongoRepository<LikeEntity, String> {


    Mono<Void> deleteByOriginalPostIdAndLikedByUserId(String postId, String userId);

    Mono<Boolean> existsByOriginalPostIdAndLikedByUserId(String postId, String userId);




}
