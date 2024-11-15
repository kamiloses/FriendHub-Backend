package com.kamiloses.userservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.rabbitmq.RabbitConfig;
import com.kamiloses.userservice.entity.UserEntity;
import com.kamiloses.userservice.dto.UserDetailsDto;
import com.kamiloses.userservice.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitUserListener {

   private final UserRepository userRepository;

    public RabbitUserListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = RabbitConfig.Queue_To_User_Service)
     public String receive_And_Resend_UserDetails(String username) throws JsonProcessingException {
        UserEntity userEntity = userRepository.findByUsername("kamiloses").block();
        System.out.println(userEntity);
        UserDetailsDto userDetailsDto = new UserDetailsDto();
            userDetailsDto.setId(userEntity.getId());
            userDetailsDto.setUsername(userEntity.getUsername());
            userDetailsDto.setFirstName(userEntity.getFirstName());
            userDetailsDto.setLastName(userEntity.getLastName());




        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(userDetailsDto);
    }




}
