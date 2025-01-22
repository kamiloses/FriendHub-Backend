package com.kamiloses.likeservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.likeservice.dto.UserDetailsDto;
import com.kamiloses.rabbitmq.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RabbitLikeProducer {


    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;



    public RabbitLikeProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public Mono<UserDetailsDto> askForUserDetails(String username) {
        return Mono.fromCallable(() ->
                (String) rabbitTemplate.convertSendAndReceive(
                        RabbitConfig.USER_INFO_EXCHANGE, RabbitConfig.USER_INFO_ROUTING_KEY, username)
        ).flatMap(this::convertStringToUserDetailsDto);

    }

    public Mono<Void> sendPostIdToPostModule(String postId){
       return Mono.fromRunnable(()->
                rabbitTemplate.convertAndSend(RabbitConfig.AUTH_EXCHANGE,
                        RabbitConfig.AUTH_ROUTING_KEY
                        ,postId));

    }





    private Mono<UserDetailsDto> convertStringToUserDetailsDto(String userDetailsAsString) {
        return Mono.fromCallable(()->objectMapper.readValue(userDetailsAsString, UserDetailsDto.class)).
                onErrorResume(JsonProcessingException.class, e -> {
                    log.error("Error occurred in convertStringToUserDetailsDto while reading value: {}", e.getMessage());
                    return Mono.error(new RuntimeException("There was some problem with converting value"));
                });


    }


}
