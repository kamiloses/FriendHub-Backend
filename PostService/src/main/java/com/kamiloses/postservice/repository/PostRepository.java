package com.kamiloses.postservice.repository;


import com.kamiloses.postservice.entity.PostEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.util.List;

public interface PostRepository extends ReactiveMongoRepository<PostEntity, String> {





Flux<PostEntity> findByUserId(String userId);





}
