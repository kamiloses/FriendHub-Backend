package com.kamiloses.friendservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.rabbitmq.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RabbitFriendsProducer {


    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitFriendsProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public UserDetailsDto askForUserDetails(String username) {
        String userDetailsAsString = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.USER_INFO_EXCHANGE, RabbitConfig.USER_INFO_ROUTING_KEY, username);

       return convertStringToUserDetailsDto(userDetailsAsString); }



    private UserDetailsDto convertStringToUserDetailsDto(String userDetailsAsString) {
        try {
            return objectMapper.readValue(userDetailsAsString, UserDetailsDto.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }









    public List<UserDetailsDto> askForFriendsDetails(List<FriendShipDto> friendsId) {


        String friendsIdAsString = convertListOfFriendsIdToString(friendsId);
        String listOfFriendsDetails = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.USER_INFO_EXCHANGE, RabbitConfig.FRIENDS_INFO_ROUTING_KEY, friendsIdAsString);
        return convertStringOfUserDetailsToList(listOfFriendsDetails);


    }


    private String convertListOfFriendsIdToString(List<FriendShipDto> friendsId) {
        try {
            return objectMapper.writeValueAsString(friendsId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<UserDetailsDto> convertStringOfUserDetailsToList(String friendsId) {
        try {
            return objectMapper.readValue(friendsId, new TypeReference<List<UserDetailsDto>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    public List<UserDetailsDto> getSimilarPeopleNameToUsername(String username) {
        String usersDetails = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_searchedPeople, RabbitConfig.ROUTING_KEY_searchedPeople, username);
        List<UserDetailsDto> userDetailsDtos = convertStringOfUserDetailsToList(usersDetails);

        return userDetailsDtos;   }


}
