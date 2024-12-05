package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.dto.SearchedPeopleDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
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
        UserDetailsDto userDetailsDto = rabbitFriendshipProducer.askForUserDetails(loggedUserId);
        Flux<FriendshipEntity> friendshipEntitiesByUserIdOrFriendId = friendshipRepository.getFriendshipEntitiesByUserIdOrFriendId(userDetailsDto.getId(), userDetailsDto.getId());
        return getYourFriendsId(friendshipEntitiesByUserIdOrFriendId, userDetailsDto.getId())
                .flatMapMany(yourFriendsId -> Flux.fromIterable(rabbitFriendshipProducer.askForFriendsDetails(yourFriendsId)));
    }


    public Mono<List<FriendShipDto>> getYourFriendsId(Flux<FriendshipEntity> friendshipEntities, String loggedUserId) {
        return friendshipEntities.filter(friendshipEntity ->
                        friendshipEntity.getFriendId().equals(loggedUserId) ||
                                friendshipEntity.getUserId().equals(loggedUserId))
                .map(friendshipEntity -> {
                    if (!friendshipEntity.getFriendId().equals(loggedUserId)) {
                        return new FriendShipDto(friendshipEntity.getFriendId(), friendshipEntity.getId());
                    } else {
                        return new FriendShipDto(friendshipEntity.getFriendId(), friendshipEntity.getId());
                    }
                })
                .collectList();
    }

    public List<SearchedPeopleDto> getPeopleWithSimilarUsername(String username, String myUsername) {

        List<UserDetailsDto> searchedPeople = rabbitFriendshipProducer.getSimilarPeopleNameToUsername(username);
        return searchedPeople.stream().filter(userDetailsDto -> !userDetailsDto.getUsername().equals(myUsername)).map(userDetails -> {
            SearchedPeopleDto searchedPeopleDto = new SearchedPeopleDto();
            searchedPeopleDto.setFirstName(userDetails.getFirstName());
            searchedPeopleDto.setLastName(userDetails.getLastName());
            searchedPeopleDto.setId(userDetails.getId());
            searchedPeopleDto.setUsername(userDetails.getUsername());
            searchedPeopleDto.setBio(userDetails.getBio());
            searchedPeopleDto.setIsYourFriend(Boolean.FALSE);
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

}