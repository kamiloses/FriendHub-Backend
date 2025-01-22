package com.kamiloses.postservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.rabbitmq.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RabbitPostProducer {


    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;


    public RabbitPostProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
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
        return Mono.fromCallable(() -> objectMapper.readValue(userDetailsAsString, UserDetailsDto.class)).
                onErrorResume(JsonProcessingException.class, e -> {
                    log.error("Error occurred while reading value, error: {}", e.getMessage());
                    return Mono.error(new RuntimeException("There was some problem with converting value"));
                });


    }




    public String isPostLiked(String postId, String loggedUserUsername) {
        String dataToBeDelivered = postId + ":" + loggedUserUsername;
//        return Mono.fromSupplier(() ->
               return  (String) rabbitTemplate.convertSendAndReceive(
                       RabbitConfig.POST_OPERATIONS_EXCHANGE, RabbitConfig.IS_POST_LIKED_ROUTING_KEY,dataToBeDelivered)
        ;


    }


}
