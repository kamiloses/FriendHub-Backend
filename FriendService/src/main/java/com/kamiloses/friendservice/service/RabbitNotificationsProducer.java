//package com.kamiloses.friendservice.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.kamiloses.friendservice.dto.FriendshipNotificationDto;
//import com.kamiloses.friendservice.dto.UserDetailsDto;
//import com.kamiloses.rabbitmq.RabbitConfig;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//@Component
//public class RabbitNotificationsProducer {
//
//private RabbitTemplate rabbitTemplate;
//
//
//    public RabbitNotificationsProducer(RabbitTemplate rabbitTemplate) {
//        this.rabbitTemplate = rabbitTemplate;
//    }
//
//
//
//    public UserDetailsDto createNotification(FriendshipNotificationDto friendshipNotificationDto) {
//                        (String) rabbitTemplate.convertSendAndReceive(
//                                RabbitConfig.USER_INFO_EXCHANGE, RabbitConfig.USER_INFO_ROUTING_KEY, usernameOrId)
//                )
//                .flatMap(this::convertStringToUserDetailsDto);
//    }
//
//
//    private Mono<UserDetailsDto> convertStringToUserDetailsDto(String userDetailsAsString) {
//        return Mono.fromCallable(()->objectMapper.readValue(userDetailsAsString, UserDetailsDto.class)).
//                onErrorResume(JsonProcessingException.class, e -> {
//                    log.error("Error occurred in convertStringToUserDetailsDto while reading value: {}", e.getMessage());
//                    return Mono.error(new RuntimeException("There was some problem with converting value"));
//                });
//
//
//
//
//
//
//
//}
