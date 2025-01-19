package com.kamiloses.likeservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.likeservice.dto.UserDetailsDto;
import com.kamiloses.rabbitmq.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RabbitLikeProducer {


    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;



    public RabbitLikeProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public Mono askForUserDetails(String username) {
        return Mono.fromCallable(() ->
                (String) rabbitTemplate.convertSendAndReceive(
                        RabbitConfig.USER_INFO_EXCHANGE, RabbitConfig.USER_INFO_ROUTING_KEY, username)
        );

    }

    public Mono<Void> sendPostIdToPostModule(String postId){
       return Mono.fromRunnable(()->
                rabbitTemplate.convertAndSend(RabbitConfig.AUTH_EXCHANGE,
                        RabbitConfig.AUTH_ROUTING_KEY
                        ,postId));

    }





    private UserDetailsDto convertStringToUserDetailsDto(String userDetailsAsString) {
        try {
            return objectMapper.readValue(userDetailsAsString, UserDetailsDto.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }


}
