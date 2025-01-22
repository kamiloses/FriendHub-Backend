package com.kamiloses.userservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.rabbitmq.RabbitConfig;
import com.kamiloses.userservice.dto.FriendShipDto;
import com.kamiloses.userservice.dto.UserDetailsDto;
import com.kamiloses.userservice.exception.UserDatabaseFetchException;
import com.kamiloses.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
public class RabbitUserListener {


    // rabbit listener can't resend data in reactive way. it causes too long receiving data.

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private Integer count = 0;

    public RabbitUserListener(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = RabbitConfig.USER_INFO_REQUEST_QUEUE)
    public String receive_And_Resend_UserDetails(String username) {
        return userRepository.findByUsernameOrId(username, username)
                .onErrorResume(error->{
                    log.error("There was some problem with fetching User in receive_And_Resend_UserDetails method");
                    return Mono.error(UserDatabaseFetchException::new);
                })
                .map(userEntity ->
                        UserDetailsDto
                                .builder()
                                .id(userEntity.getId())
                                .username(userEntity.getUsername())
                                .firstName(userEntity.getFirstName())
                                .lastName(userEntity.getLastName())
                                .build()).flatMap(userDetailsDto ->
                        Mono.fromCallable(() -> objectMapper.writeValueAsString(userDetailsDto))
                                .onErrorResume(JsonProcessingException.class, e -> {
                                    log.error("Error occurred in receiveAndResendUserDetails method while reading value, error: {}", e.getMessage());
                                    return Mono.error(new RuntimeException("There was some problem with converting value"));
                                })).block();


    }


    @RabbitListener(queues = RabbitConfig.FRIENDS_INFO_REQUEST_QUEUE)
    public String receive_And_Resend_FriendsDetails(String listOfUsersId) {
        return convertToListOfObjects(listOfUsersId)
                .map(convertedUserId -> userRepository.findUserEntitiesByIdIn(convertedUserId
                        .stream().map(FriendShipDto::getUserIdOrFriendId).toList())
                        .onErrorResume(error->{
                            log.error("There was some problem with fetching User in receive_And_Resend_FriendsDetails method");
                            return Mono.error(UserDatabaseFetchException::new);
                        })
                        .map(userEntity -> {
                    UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                            .id(userEntity.getId())
                            .username(userEntity.getUsername())
                            .firstName(userEntity.getFirstName())
                            .lastName(userEntity.getLastName())
                            .chatId(convertedUserId.stream().map(FriendShipDto::getChatId).toList().get(count))
                            .build();
                    count++;
                    return userDetailsDto;
                })).flatMap(userDetailsDto -> {
                    count = 0;
                    return convertListOfUserDetailsToString(userDetailsDto.collectList().block());
                }).block();
    }














    @RabbitListener(queues = RabbitConfig.Queue_Searched_People)
    private String receive_And_Resend_Similar_Users_With_Details(String username) {
        return userRepository.findByUsernameContaining(username).map(
                userEntity ->
                    UserDetailsDto.builder()
                            .chatId(userEntity.getId())
                            .username(userEntity.getUsername())
                            .firstName(userEntity.getFirstName())
                            .lastName(userEntity.getLastName()).build())
                .collectList()
                .flatMap(this::convertListOfUserDetailsToString).block();
    }


    private Mono<List<FriendShipDto>> convertToListOfObjects(String listOfFriendsId) {
        return Mono.fromCallable(() -> objectMapper.readValue(listOfFriendsId, new TypeReference<List<FriendShipDto>>() {
                }))
                .onErrorResume(JsonProcessingException.class, e -> {
                    log.error("Error occurred in convertToListOfString method while reading value, error: {}", e.getMessage());
                    return Mono.error(new RuntimeException("There was some problem with converting value"));
                });


    }

    private Mono<String> convertListOfUserDetailsToString(List<UserDetailsDto> userDetails) {

        return Mono.fromCallable(() -> objectMapper.writeValueAsString(userDetails))
                .onErrorResume(JsonProcessingException.class, e -> {
                    log.error("Error occurred in convertListOfUserDetailsToString method while reading value, error: {}", e.getMessage());
                    return Mono.error(new RuntimeException("There was some problem with converting value"));
                });

    }


}