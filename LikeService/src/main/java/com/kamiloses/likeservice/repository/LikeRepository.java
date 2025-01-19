package com.kamiloses.likeservice.repository;

import com.kamiloses.likeservice.entity.LikeEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface LikeRepository extends ReactiveMongoRepository<LikeEntity, String> {


    Mono<Void> deleteByOriginalPostIdAndLikedByUserId(String postId, String userId);

    Mono<Boolean> existsByOriginalPostIdAndLikedByUserId(String postId, String userId);




}
