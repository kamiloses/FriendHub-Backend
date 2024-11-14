package com.kamiloses.commentservice.repository;

import com.kamiloses.commentservice.entity.CommentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CommentRepository extends ReactiveMongoRepository<CommentEntity, String> {

    Flux<CommentEntity> findCommentEntitiesByParentCommentId(String parentCommentId);



}
