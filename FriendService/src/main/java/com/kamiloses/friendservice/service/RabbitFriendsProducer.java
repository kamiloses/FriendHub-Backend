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
                    log.error("Error occurred while reading value, error: {}", e.getMessage());
                    return Mono.error(new RuntimeException("There was some problem with converting value"));
                });



    }









    public List<UserDetailsDto> askForFriendsDetails(List<FriendShipDto> friendsId) {


        String friendsIdAsString = convertListOfFriendsIdToString(friendsId);
        String listOfFriendsDetails = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.USER_INFO_EXCHANGE, RabbitConfig.FRIENDS_INFO_ROUTING_KEY, friendsIdAsString);
        return convertStringOfUserDetailsToList(listOfFriendsDetails);


    }


    private String convertListOfFriendsIdToString(List<FriendShipDto> friendsId) {
        try {
            return objectMapper.writeValueAsString(friendsId);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<UserDetailsDto> convertStringOfUserDetailsToList(String friendsId) {
        try {
            return objectMapper.readValue(friendsId, new TypeReference<List<UserDetailsDto>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }

    public List<UserDetailsDto> getSimilarPeopleNameToUsername(String username) {
        String usersDetails = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_searchedPeople, RabbitConfig.ROUTING_KEY_searchedPeople, username);
        List<UserDetailsDto> userDetailsDtos = convertStringOfUserDetailsToList(usersDetails);

        return userDetailsDtos;   }


}
