package com.kamiloses.friendservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.rabbitmq.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
@Slf4j
@Component
public class RabbitFriendsProducer {


    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public RabbitFriendsProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
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
                    log.error("Error occurred in convertStringToUserDetailsDto while reading value: {}", e.getMessage());
                    return Mono.error(new RuntimeException("There was some problem with converting value"));
                });



    }

    public Mono<List<UserDetailsDto>> askForFriendsDetails(List<FriendShipDto> friendsId) {


     return convertListOfFriendsIdToString(friendsId)
             .flatMap(convertedFriendId->Mono.fromSupplier(()->
                     (String)    rabbitTemplate.convertSendAndReceive(RabbitConfig.USER_INFO_EXCHANGE, RabbitConfig.FRIENDS_INFO_ROUTING_KEY, convertedFriendId)
             ).flatMap(this::convertStringOfUserDetailsToList));



    }


    private Mono<String> convertListOfFriendsIdToString(List<FriendShipDto> friendsId) {

            return Mono.fromCallable(()->objectMapper.writeValueAsString(friendsId))
                    .onErrorResume(JsonProcessingException.class, e -> {
                        log.error("Error occurred in convertListOfFriendsIdToString while writing value: {}", e.getMessage());
            return Mono.error(new RuntimeException("There was some problem with converting value"));

        });


        }


    private Mono<List<UserDetailsDto>> convertStringOfUserDetailsToList(String friendsId) {

         return  Mono.fromCallable(()->  objectMapper
                 .readValue(friendsId, new TypeReference<List<UserDetailsDto>>() {}))
                 .onErrorResume(JsonProcessingException.class, e -> {
                     log.error("Error occurred in convertStringOfUserDetailsToList while reading value: {}", e.getMessage());
                     return Mono.error(new RuntimeException("There was some problem with converting value"));

                 });


    }





    public Mono<List<UserDetailsDto>> getSimilarPeopleNameToUsername(String username) {
       return Mono.fromSupplier(()->(String)rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_searchedPeople, RabbitConfig.ROUTING_KEY_searchedPeople, username))
                .flatMap(this::convertStringOfUserDetailsToList);

    }


}
