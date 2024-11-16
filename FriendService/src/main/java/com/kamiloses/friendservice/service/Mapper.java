package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.UserDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

private RabbitCommentProducer rabbitCommentProducer;

    public Mapper(RabbitCommentProducer rabbitCommentProducer) {
        this.rabbitCommentProducer = rabbitCommentProducer;
    }

    public UserDetailsDto mapUserIdToUserDto(String userId){
        UserDetailsDto userDetails = rabbitCommentProducer.askForUserDetails(userId);
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(userId);
        userDetailsDto.setUsername(userDetails.getUsername());
        userDetailsDto.setPassword(userDetails.getPassword());
        userDetailsDto.setFirstName(userDetails.getFirstName());
        userDetailsDto.setLastName(userDetails.getLastName());
return userDetailsDto;
   }



}
