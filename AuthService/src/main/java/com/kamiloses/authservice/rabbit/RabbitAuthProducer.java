package com.kamiloses.authservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.authservice.dto.UserDetailsDto;
import com.kamiloses.rabbitmq.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitAuthProducer {


    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitAuthProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public UserDetailsDto askForUserDetails(String username) {
        String userDetailsAsString = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_To_User_Service, RabbitConfig.ROUTING_KEY_, username);

        return convertStringToUserDetailsDto(userDetailsAsString);

    }


    private UserDetailsDto convertStringToUserDetailsDto(String userDetailsAsString) {
        try {
            return objectMapper.readValue(userDetailsAsString, UserDetailsDto.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }


}
