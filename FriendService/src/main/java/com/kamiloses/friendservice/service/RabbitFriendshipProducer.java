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


    public List<UserDetailsDto> askForUserAndFriendDetails(String userId,String friendId) {
        String userAndFriendDetailsAsString = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_To_User_Service, RabbitConfig.ROUTING_KEY_,userId+" "+friendId);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(userAndFriendDetailsAsString, new TypeReference<List<UserDetailsDto>>() {});


        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
