package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.dto.SearchedPeopleDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.exception.FriendsDatabaseFetchException;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import com.kamiloses.rabbitmq.exception.RabbitExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Import(RabbitExceptionHandler.class)
@Slf4j
public class FriendsService {

    private final FriendshipRepository friendshipRepository;
    private final RabbitFriendsProducer rabbitFriendsProducer;
    private Boolean isYourFriend = false;

    private final RedisTemplate<String, String> redisTemplate;

    public FriendsService(FriendshipRepository friendshipRepository, RabbitFriendsProducer rabbitFriendsProducer, RedisTemplate<String, String> redisTemplate) {
        this.friendshipRepository = friendshipRepository;
        this.rabbitFriendsProducer = rabbitFriendsProducer;
        this.redisTemplate = redisTemplate;
    }

    public Flux<UserDetailsDto> getAllUserFriends(String loggedUserId) {
        return rabbitFriendsProducer.askForUserDetails(loggedUserId)
                .flatMapMany(userDetailsDto -> {
                    Flux<FriendshipEntity> friendshipEntities = friendshipRepository.getFriendshipEntitiesByUserIdOrFriendId(
                            userDetailsDto.getId(), userDetailsDto.getId());

                    return getYourFriendsId(friendshipEntities, userDetailsDto.getId())
                            .flatMapMany(friendIds -> Flux.fromIterable(rabbitFriendsProducer.askForFriendsDetails(friendIds))
                                    .map(friend -> {
                                        friend.setIsOnline(isOnline(redisTemplate, friend.getUsername()));
                                        return friend;
                                    }));
                });
    }






    public Flux<SearchedPeopleDto> getPeopleWithSimilarUsername(String username, String myUsername) {
        return Mono.fromSupplier(() -> rabbitFriendsProducer.getSimilarPeopleNameToUsername(username))
                .flatMapMany(searchedPeople -> Flux.fromStream(searchedPeople.stream())
                        .filter(userDetailsDto -> !userDetailsDto.getUsername().equals(myUsername))
                        .flatMap(userDetails -> Mono.just(SearchedPeopleDto.builder()
                                        .firstName(userDetails.getFirstName())
                                        .lastName(userDetails.getLastName())
                                        .id(userDetails.getId())
                                        .username(userDetails.getUsername())
                                        .build())   //found users that are similar to username that i  wrote, except of my username.
                                .flatMap(searchedPeopleDto ->  rabbitFriendsProducer.askForUserDetails(userDetails.getUsername())
                                        .flatMap(searchedFriendDetails ->  rabbitFriendsProducer.askForUserDetails(myUsername)
                                                .flatMap(myDetails -> friendshipRepository.getFriendshipEntityByUserIdOrFriendId(myDetails.getId(), myDetails.getId())
                                                        .onErrorResume(error->{
                                                            log.error("There was some problem with fetching friends");
                                                            return Mono.error(FriendsDatabaseFetchException::new);
                                                        })//asking for my and searchedUsers details and checking whether we are friends.
                                                        .collectList()
                                                        .map(allFriendsRelatedWithMe -> {
                                                            boolean isYourFriend = false;
                                                            if (allFriendsRelatedWithMe != null) {
                                                                for (FriendshipEntity friendshipEntity : allFriendsRelatedWithMe) {
                                                                    if (friendshipEntity.getFriendId().equals(searchedFriendDetails.getId()) ||
                                                                            friendshipEntity.getUserId().equals(searchedFriendDetails.getId())) {
                                                                        isYourFriend = true;
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                            searchedPeopleDto.setIsYourFriend(isYourFriend);
                                                            return searchedPeopleDto;
                                                        })
                                                )
                                        )
                                )
                        )
                );
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


    public Mono<FriendshipEntity> addToFriendList(String friendUsername, String myUsername) {

        return rabbitFriendsProducer.askForUserDetails(friendUsername)
                .flatMap(friendDetails ->
                                 rabbitFriendsProducer.askForUserDetails(myUsername)
                                        .flatMap(myAccountDetails -> {
                                            FriendshipEntity friendshipEntity = FriendshipEntity.builder()
                                                    .friendId(friendDetails.getId())
                                                    .userId(myAccountDetails.getId())
                                                    .createdAt(new Date()).build();

                                            return friendshipRepository.save(friendshipEntity)
                                                    .onErrorResume(error->{
                                                        log.error("There was some problem with saving - adding to friend list");
                                                        return Mono.error(FriendsDatabaseFetchException::new);
                                                    })

                                                    ;
                                        }));

    }

    public Mono<Void> removeFriend(String friendUsername, String myUsername) {

     return    rabbitFriendsProducer.askForUserDetails(friendUsername)
                .flatMap(friendDetails ->
                                rabbitFriendsProducer.askForUserDetails(myUsername)
                        .flatMap(myAccountDetails ->
                                friendshipRepository.deleteByUserIdAndFriendId(myAccountDetails.getId(), friendDetails.getId())
                                        .then(friendshipRepository.deleteByUserIdAndFriendId(friendDetails.getId(), myAccountDetails.getId()))));


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