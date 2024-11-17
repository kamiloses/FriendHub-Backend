package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final Mapper mapper;
    private final RabbitFriendshipProducer rabbitFriendshipProducer;

    public FriendshipService(FriendshipRepository friendshipRepository, Mapper mapper, RabbitFriendshipProducer rabbitFriendshipProducer) {
        this.friendshipRepository = friendshipRepository;
        this.mapper = mapper;
        this.rabbitFriendshipProducer = rabbitFriendshipProducer;
    }

    public Flux<UserDetailsDto> getAllUserFriends(String loggedUserId) {
        Flux<FriendshipEntity> friendshipEntitiesByUserIdOrFriendId = friendshipRepository.getFriendshipEntitiesByUserIdOrFriendId("1", "1");
        List<String> yourFriendsId = getYourFriendsId(friendshipEntitiesByUserIdOrFriendId, loggedUserId);

            return Flux.fromIterable(rabbitFriendshipProducer.askForFriendsDetails(yourFriendsId));

    }



    public List<String> getYourFriendsId(Flux<FriendshipEntity> friendshipEntities, String loggedUserId) {
        return friendshipEntities.map(friendshipEntity -> {

            if (!friendshipEntity.getFriendId().equals(loggedUserId)) {
                return friendshipEntity.getFriendId();
            } else {
                return friendshipEntity.getUserId();
            }
        }).collectList().block();
    }  //this method must be written synchronously because rabbit must get all data at one time
}
