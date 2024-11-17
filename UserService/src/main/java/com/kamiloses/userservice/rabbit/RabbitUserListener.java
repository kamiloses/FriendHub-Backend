package com.kamiloses.userservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.rabbitmq.RabbitConfig;
import com.kamiloses.userservice.entity.UserEntity;
import com.kamiloses.userservice.dto.UserDetailsDto;
import com.kamiloses.userservice.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

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


    @RabbitListener(queues = RabbitConfig.Queue_To_User_Service_From_FriendService)
    public String receive_And_Resend_UserDetails_To_FriendService(String usersIds) throws JsonProcessingException {
        String[] users = usersIds.split(" ");

        String userId=users[0];
        String friendId=users[1];

        UserEntity user = userRepository.findById(userId).block();
        UserEntity friend = userRepository.findById(friendId).block();
        UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        UserDetailsDto friendDetailsDto = UserDetailsDto.builder()
                .id(friend.getId())
                .username(friend.getUsername())
                .password(friend.getPassword())
                .firstName(friend.getFirstName())
                .lastName(friend.getLastName())
                .build();


        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(List.of(userDetailsDto, friendDetailsDto));
    }


}