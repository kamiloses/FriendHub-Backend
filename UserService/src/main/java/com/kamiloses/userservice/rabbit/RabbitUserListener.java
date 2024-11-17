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

    public RabbitUserListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = RabbitConfig.Queue_To_User_Service)
    public String receive_And_Resend_UserDetails(String username) throws JsonProcessingException {
        UserEntity userEntity = userRepository.findByUsername(username).block();
        System.err.println(userEntity);
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
    public String receive_And_Resend_FriendsDetails(String listOfUsersId){
        List<String> usersId = convertToListOfString(listOfUsersId);
        List<UserDetailsDto> friendsDetailsList = userRepository.findUserEntitiesByIdIn(usersId).stream().map(userEntity ->
        {
            UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setId(userEntity.getId());
            userDetailsDto.setUsername(userEntity.getUsername());
            userDetailsDto.setPassword(userEntity.getPassword());
            userDetailsDto.setEmail(userEntity.getEmail());
            userDetailsDto.setFirstName(userEntity.getFirstName());
            userDetailsDto.setLastName(userEntity.getLastName());
            userDetailsDto.setBio(userEntity.getBio());
            userDetailsDto.setProfileImageUrl(userEntity.getProfileImageUrl());
            return userDetailsDto;
        }).toList();

        return convertListOfUserDetailsToString(friendsDetailsList);}



    private List<String>convertToListOfString(String listOfFriendsId){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
           return objectMapper.readValue(listOfFriendsId, new TypeReference<List<String>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

     private String convertListOfUserDetailsToString(List<UserDetailsDto> userDetails){
         ObjectMapper objectMapper = new ObjectMapper();
         try {
          return    objectMapper.writeValueAsString(userDetails);
         } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
         }

     }

}