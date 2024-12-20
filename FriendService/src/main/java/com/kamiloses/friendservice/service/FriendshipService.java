package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.dto.SearchedPeopleDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final RabbitFriendshipProducer rabbitFriendshipProducer;
    private Boolean isYourFriend = false;

    private final RedisTemplate<String, String> redisTemplate;

    public FriendshipService(FriendshipRepository friendshipRepository, RabbitFriendshipProducer rabbitFriendshipProducer, RedisTemplate<String, String> redisTemplate) {
        this.friendshipRepository = friendshipRepository;
        this.rabbitFriendshipProducer = rabbitFriendshipProducer;
        this.redisTemplate = redisTemplate;
    }

    public Flux<UserDetailsDto> getAllUserFriends(String loggedUserId) {
        UserDetailsDto userDetailsDto = rabbitFriendshipProducer.askForUserDetails(loggedUserId);
        Flux<FriendshipEntity> friendshipEntitiesByUserIdOrFriendId = friendshipRepository.getFriendshipEntitiesByUserIdOrFriendId(userDetailsDto.getId(), userDetailsDto.getId());


        return getYourFriendsId(friendshipEntitiesByUserIdOrFriendId, userDetailsDto.getId())
                .flatMapMany(yourFriendsId -> Flux.fromIterable(rabbitFriendshipProducer.askForFriendsDetails(yourFriendsId)))
                .map(user -> {
                    if (isOnline(redisTemplate, user.getUsername())) {
                        user.setIsOnline(true);
                    } else {
                        user.setIsOnline(false);
                    }
                    return user;

                });


    }


    public Mono<List<FriendShipDto>> getYourFriendsId(Flux<FriendshipEntity> friendshipEntities, String loggedUserId) {
        return friendshipEntities.filter(friendshipEntity ->
                        friendshipEntity.getFriendId().equals(loggedUserId) ||
                                friendshipEntity.getUserId().equals(loggedUserId))
                .map(friendshipEntity -> {
                    if (!friendshipEntity.getFriendId().equals(loggedUserId)) {
                        return new FriendShipDto(friendshipEntity.getFriendId(), friendshipEntity.getId());
                    } else {
                        return new FriendShipDto(friendshipEntity.getUserId(), friendshipEntity.getId());
                    }
                })
                .collectList();
    }

    public List<SearchedPeopleDto> getPeopleWithSimilarUsername(String username, String myUsername) {

        List<UserDetailsDto> searchedPeople = rabbitFriendshipProducer.getSimilarPeopleNameToUsername(username);

    return     searchedPeople.stream().filter(userDetailsDto -> !userDetailsDto.getUsername().equals(myUsername)).map(userDetails -> {
            SearchedPeopleDto searchedPeopleDto = new SearchedPeopleDto();
            searchedPeopleDto.setFirstName(userDetails.getFirstName());
            searchedPeopleDto.setLastName(userDetails.getLastName());
            searchedPeopleDto.setId(userDetails.getId());
            searchedPeopleDto.setUsername(userDetails.getUsername());
            UserDetailsDto searchedFriendDetails = rabbitFriendshipProducer.askForUserDetails(userDetails.getUsername());
            UserDetailsDto myDetails = rabbitFriendshipProducer.askForUserDetails(myUsername);


            List<FriendshipEntity> allFriendsRelatedWithMe = friendshipRepository.getFriendshipEntityByUserIdOrFriendId(myDetails.getId(), myDetails.getId()).collectList().block();
            System.err.println(allFriendsRelatedWithMe);
            if (allFriendsRelatedWithMe != null) {
                for (FriendshipEntity friendshipEntity : allFriendsRelatedWithMe) {
                    if (friendshipEntity.getFriendId().equals(searchedFriendDetails.getId()) || friendshipEntity.getUserId().equals(searchedFriendDetails.getId())) {
                        isYourFriend = true;

                        searchedPeopleDto.setIsYourFriend(isYourFriend);

                    }
                }
            }
            return searchedPeopleDto;
        }).toList();
    }



public Mono<FriendshipEntity> addToFriendList(String friendUsername, String myUsername) {

    UserDetailsDto friendDetails = rabbitFriendshipProducer.askForUserDetails(friendUsername);
    UserDetailsDto myDetails = rabbitFriendshipProducer.askForUserDetails(myUsername);
    FriendshipEntity friendshipEntity = new FriendshipEntity();
    friendshipEntity.setFriendId(friendDetails.getId());
    friendshipEntity.setUserId(myDetails.getId());
    friendshipEntity.setCreatedAt(new Date());
    return friendshipRepository.save(friendshipEntity);
}



public Mono<Void> removeFriend(String friendUsername, String myUsername) {
    UserDetailsDto friendDetails = rabbitFriendshipProducer.askForUserDetails(friendUsername);
    UserDetailsDto myDetails = rabbitFriendshipProducer.askForUserDetails(myUsername);
    return friendshipRepository.deleteByUserIdAndFriendId(myDetails.getId(), friendDetails.getId())
            .then(friendshipRepository.deleteByUserIdAndFriendId(friendDetails.getId(), myDetails.getId()));

}




public boolean isOnline(RedisTemplate<String, String> redisTemplate, String username) {
    Set<String> keys = redisTemplate.keys("*");
    if (keys != null) {
        for (String key : keys) {
            String value = redisTemplate.opsForValue().get(key);
            if (username.equals(value)) {
                return true;
            }
        }
    }
    return false;
}

}