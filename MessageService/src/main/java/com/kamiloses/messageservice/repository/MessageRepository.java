package com.kamiloses.messageservice.repository;

import com.kamiloses.messageservice.entity.MessageEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface MessageRepository extends ReactiveMongoRepository<MessageEntity, String> {

Flux<MessageEntity> findByChatId(String chatId);

}
