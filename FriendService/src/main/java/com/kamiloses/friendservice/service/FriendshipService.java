package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final Mapper mapper;

    public FriendshipService(FriendshipRepository friendshipRepository, Mapper mapper) {
        this.friendshipRepository = friendshipRepository;
        this.mapper = mapper;
    }

//    public Flux<FriendShipDto> getAllUserFriends(String loggedUserId) {
//        Flux<FriendshipEntity> friendshipEntitiesByUserIdOrFriendId = friendshipRepository.getFriendshipEntitiesByUserIdOrFriendId("1", "1");
//
//    }


    public List<String> getYourFriendsIds(Flux<FriendshipEntity> friendshipEntities, String loggedUserId) {
        return friendshipEntities.map(friendshipEntity -> {

            if (!friendshipEntity.getFriendId().equals(loggedUserId)) {
                return friendshipEntity.getFriendId();
            } else {
                return friendshipEntity.getUserId();
            }
        }).collectList().block();
    }
}
