package com.kamiloses.postservice.repository;

import com.kamiloses.postservice.entity.RetweetEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RetweetRepository extends ReactiveMongoRepository<RetweetEntity, String> {



         Mono<Void> deleteByOriginalPostIdAndRetweetedByUserId(String postId, String userId);

    Mono<Boolean> existsByOriginalPostIdAndRetweetedByUserId(String postId, String userId);
    Flux<RetweetEntity> findByRetweetedByUserId(String userId);
}
