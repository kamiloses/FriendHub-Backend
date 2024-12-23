package com.kamiloses.commentservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.commentservice.dto.UserDetailsDto;
import com.kamiloses.rabbitmq.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitCommentProducer {


    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitCommentProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public UserDetailsDto askForUserDetails(String username) {
        String userDetailsAsString = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.USER_INFO_EXCHANGE, RabbitConfig.USER_INFO_ROUTING_KEY, username);

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
