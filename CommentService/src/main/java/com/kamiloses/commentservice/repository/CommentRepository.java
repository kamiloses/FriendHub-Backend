package com.kamiloses.commentservice.repository;

import com.kamiloses.commentservice.entity.CommentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CommentRepository extends ReactiveMongoRepository<CommentEntity, String> {
}
