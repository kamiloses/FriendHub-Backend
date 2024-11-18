package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final RabbitFriendshipProducer rabbitFriendshipProducer;

    public FriendshipService(FriendshipRepository friendshipRepository, RabbitFriendshipProducer rabbitFriendshipProducer) {
        this.friendshipRepository = friendshipRepository;
        this.rabbitFriendshipProducer = rabbitFriendshipProducer;
    }

    public Flux<UserDetailsDto> getAllUserFriends(String loggedUserId) {
        Flux<FriendshipEntity> friendshipEntitiesByUserIdOrFriendId = friendshipRepository.getFriendshipEntitiesByUserIdOrFriendId("1", "1");
        UserDetailsDto userDetailsDto = rabbitFriendshipProducer.askForUserDetails(loggedUserId);
        return getYourFriendsId(friendshipEntitiesByUserIdOrFriendId,userDetailsDto.getId())
                .flatMapMany(yourFriendsId -> Flux.fromIterable(rabbitFriendshipProducer.askForFriendsDetails(yourFriendsId)));
    }


 //todo naprawić tą metoed poniżej bo ona jest blędna

    public Mono<List<String>> getYourFriendsId(Flux<FriendshipEntity> friendshipEntities, String loggedUserId) {
        return friendshipEntities.filter(friendshipEntity ->
                        friendshipEntity.getFriendId().equals(loggedUserId) ||
                                friendshipEntity.getUserId().equals(loggedUserId))
                .map(friendshipEntity -> {
                    if (!friendshipEntity.getFriendId().equals(loggedUserId)) {
                        return friendshipEntity.getFriendId();
                    } else {
                        return friendshipEntity.getUserId();
                    }
                })
                .collectList(); // Collect list asynchronously without blocking
    }
    }  //this method must be written synchronously because rabbit must get all data at one time(I just mean in that way)
