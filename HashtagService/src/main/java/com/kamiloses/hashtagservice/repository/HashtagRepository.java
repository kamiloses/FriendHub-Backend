package com.kamiloses.hashtagservice.repository;

import com.kamiloses.hashtagservice.entity.HashtagEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface HashtagRepository extends ReactiveMongoRepository<HashtagEntity, String> {
}
