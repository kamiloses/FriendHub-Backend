package com.kamiloses.friendservice.repository;

import com.kamiloses.friendservice.entity.FriendshipEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FriendshipRepository extends ReactiveMongoRepository<FriendshipEntity,String> {



Flux<FriendshipEntity> getFriendshipEntitiesByUserIdOrFriendId(String userId, String friendId);
Flux<FriendshipEntity> getFriendshipEntityByUserIdOrFriendId(String userId, String friendId);

Mono<Void> deleteByUserIdAndFriendId(String userId, String friendId);


}
