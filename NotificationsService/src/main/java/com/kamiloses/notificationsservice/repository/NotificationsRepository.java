package com.kamiloses.notificationsservice.repository;

import com.kamiloses.notificationsservice.entity.NotificationsEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface NotificationsRepository extends ReactiveMongoRepository<NotificationsEntity, String> {
}
