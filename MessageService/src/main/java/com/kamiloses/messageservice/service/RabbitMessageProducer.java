package com.kamiloses.messageservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.messageservice.dto.UserDetailsDto;
import com.kamiloses.rabbitmq.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMessageProducer {


    private final RabbitTemplate rabbitTemplate;

    public RabbitMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public UserDetailsDto askForUserDetails(String username) {
        String userDetailsAsString = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_To_User_Service, RabbitConfig.ROUTING_KEY_, username);
        System.err.println(userDetailsAsString);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(userDetailsAsString, UserDetailsDto.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
