package com.kamiloses.friendservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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


    public List<UserDetailsDto> askForFriendsDetails(List<String> friendsId) {


        String friendsIdAsString = convertListOfFriendsIdToString(friendsId);
        String listOfFriendsDetails = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_To_User_Service, RabbitConfig.Routing_Key_Friends_Details, friendsIdAsString);

        return convertStringOfUserDetailsToList(listOfFriendsDetails);


    }


    private String convertListOfFriendsIdToString(List<String> friendsId){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
    return  objectMapper.writeValueAsString(friendsId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
     private List<UserDetailsDto> convertStringOfUserDetailsToList(String friendsId){
         ObjectMapper objectMapper = new ObjectMapper();
         try {
        return      objectMapper.readValue(friendsId,  new TypeReference<List<UserDetailsDto>>() {}) ;
         } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
         }


     }



}
