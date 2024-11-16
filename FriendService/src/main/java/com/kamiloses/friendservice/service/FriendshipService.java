package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {


private FriendshipRepository friendshipRepository;
private Mapper mapper;
    public FriendshipService(FriendshipRepository friendshipRepository, Mapper mapper) {
        this.friendshipRepository = friendshipRepository;
        this.mapper = mapper;
    }

    public List<FriendShipDto> getAllUserFriends(String userId, String friendId){

        UserDetailsDto userDto = mapper.mapUserIdToUserDto(userId);
        UserDetailsDto friendDto = mapper.mapUserIdToUserDto(friendId);
        return friendshipRepository.
                getFriendshipEntitiesByUserIdOrFriendId(userId, friendId).map(
                        friendshipEntity -> {
                            FriendShipDto friendShipDto = new FriendShipDto();
                            friendShipDto.setId(friendshipEntity.getId());
                            friendShipDto.setUser(userDto);
                            friendShipDto.setFriend(friendDto);
                            friendShipDto.setCreatedAt(friendshipEntity.getCreatedAt());
                            friendShipDto.setStatus(null);
                            friendShipDto.setMessagesId(friendshipEntity.getMessagesId());
                            return friendShipDto;
                        }
                ).collectList().block();
    }







}
