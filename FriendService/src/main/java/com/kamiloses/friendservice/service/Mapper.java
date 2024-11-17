package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.UserDetailsDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {

private RabbitFriendshipProducer rabbitFriendshipProducer;

    public Mapper(RabbitFriendshipProducer rabbitFriendshipProducer) {
        this.rabbitFriendshipProducer = rabbitFriendshipProducer;
    }

    public List<UserDetailsDto> mapUserIdAndFriendIdToDto(String userId,String friendId){

        List<UserDetailsDto> userDetails = rabbitFriendshipProducer.askForUserAndFriendDetails(userId,friendId);
        UserDetailsDto user=userDetails.get(0);
        UserDetailsDto friend=userDetails.get(1);

return List.of(user,friend);   }



}
