package com.kamiloses.commentservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.commentservice.dto.UserDetailsDto;
import com.kamiloses.rabbitmq.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Slf4j
@Component
public class RabbitCommentProducer {


    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitCommentProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }


    public Mono<UserDetailsDto> askForUserDetails(String usernameOrId) {
        return Mono.fromSupplier(() ->
                        (String) rabbitTemplate.convertSendAndReceive(
                                RabbitConfig.USER_INFO_EXCHANGE, RabbitConfig.USER_INFO_ROUTING_KEY, usernameOrId)
                )
                .flatMap(this::convertStringToUserDetailsDto);
    }





    private Mono<UserDetailsDto> convertStringToUserDetailsDto(String userDetailsAsString) {
        return Mono.fromCallable(()->objectMapper.readValue(userDetailsAsString, UserDetailsDto.class)).
                onErrorResume(JsonProcessingException.class, e -> {
                    log.error("Error occurred while reading value, error: {}", e.getMessage());
                    return Mono.error(new RuntimeException("There was some problem with converting value"));
                });


    }


}
