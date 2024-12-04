package com.kamiloses.userservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.rabbitmq.RabbitConfig;
import com.kamiloses.userservice.entity.UserEntity;
import com.kamiloses.userservice.dto.UserDetailsDto;
import com.kamiloses.userservice.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class RabbitUserListener {

    private final UserRepository userRepository;
    private Integer count = 0;

    public RabbitUserListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = RabbitConfig.Queue_To_User_Service)
    public String receive_And_Resend_UserDetails(String username) throws JsonProcessingException {
        UserEntity userEntity = userRepository.findByUsernameOrId(username,username).block();
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(userEntity.getId());
        userDetailsDto.setUsername(userEntity.getUsername());
        userDetailsDto.setPassword(userEntity.getPassword());
        userDetailsDto.setFirstName(userEntity.getFirstName());
        userDetailsDto.setLastName(userEntity.getLastName());


        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(userDetailsDto);
    }
//todo utwórz potem nowego listenera gdzie będą przesyłane tylko
// najważniejsze dane w tym hasło bo ten aktualny przesyła zbyt dużo wrażliwych danycxh


    @RabbitListener(queues = RabbitConfig.Queue_For_Friends_Details)
    public String receive_And_Resend_FriendsDetails(String listOfUsersId) {
        List<FriendShipDto> usersIdAndChatId = convertToListOfString(listOfUsersId);
        Flux<UserDetailsDto> fluxUserDetailsDto = userRepository.findUserEntitiesByIdIn(usersIdAndChatId.stream().map(FriendShipDto::getUserIdOrFriendId).toList()).map(userEntity ->
        {
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setId(userEntity.getId());
            userDetailsDto.setUsername(userEntity.getUsername());
            userDetailsDto.setPassword(userEntity.getPassword());
            userDetailsDto.setFirstName(userEntity.getFirstName());
            userDetailsDto.setLastName(userEntity.getLastName());
            userDetailsDto.setBio(userEntity.getBio());
            userDetailsDto.setChatId(usersIdAndChatId.stream().map(FriendShipDto::getChatId).toList().get(count));
            count++;
            // userDetailsDto.setChatId(usersIdAndChatId.get(0).getChatId());
            userDetailsDto.setProfileImageUrl(userEntity.getProfileImageUrl());
            return userDetailsDto;
        });
        List<UserDetailsDto> userDetailsList = fluxUserDetailsDto.collectList().block();
        count = 0;
        return convertListOfUserDetailsToString(userDetailsList);
    }


    private List<FriendShipDto> convertToListOfString(String listOfFriendsId) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(listOfFriendsId, new TypeReference<List<FriendShipDto>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private String convertListOfUserDetailsToString(List<UserDetailsDto> userDetails) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.err.println(userDetails);
            return objectMapper.writeValueAsString(userDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @RabbitListener(queues = RabbitConfig.Queue_Searched_People)
    private String receive_And_Resend_Similar_Users_With_Details(String username) {
        List<UserDetailsDto> userDetails = userRepository.findByUsernameContaining(username).map(
                userEntity -> {
                    UserDetailsDto userDetailsDto = new UserDetailsDto();
                    userDetailsDto.setChatId(userEntity.getId());
                    userDetailsDto.setUsername(userEntity.getUsername());
                    userDetailsDto.setBio(userEntity.getBio());
                    userDetailsDto.setFirstName(userEntity.getFirstName());
                    userDetailsDto.setLastName(userEntity.getLastName());
                    userDetailsDto.setBio(userEntity.getBio());
                    return userDetailsDto;
                }).collectList().block();
    return     convertListOfUserDetailsToString(userDetails);
    }













}