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
public class RabbitFriendshipProducer {


    private final RabbitTemplate rabbitTemplate;

    public RabbitFriendshipProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public UserDetailsDto askForUserDetails(String username) {
        String userDetailsAsString = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_To_User_Service, RabbitConfig.ROUTING_KEY_, username);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(userDetailsAsString, UserDetailsDto.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }


    public List<UserDetailsDto> askForFriendsDetails(List<FriendShipDto> friendsId) {


        String friendsIdAsString = convertListOfFriendsIdToString(friendsId);
        String listOfFriendsDetails = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_To_User_Service, RabbitConfig.Routing_Key_Friends_Details, friendsIdAsString);
        return convertStringOfUserDetailsToList(listOfFriendsDetails);


    }


    private String convertListOfFriendsIdToString(List<FriendShipDto> friendsId) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(friendsId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<UserDetailsDto> convertStringOfUserDetailsToList(String friendsId) {
        ObjectMapper objectMapper = new ObjectMapper();
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
