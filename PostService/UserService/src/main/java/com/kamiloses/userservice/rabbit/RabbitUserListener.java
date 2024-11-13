package com.kamiloses.userservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.rabbitmq.RabbitConfig;
import com.kamiloses.userservice.dto.UserDetailsDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RabbitUserListener {



    @RabbitListener(queues = RabbitConfig.Queue_To_User_Service)
     public String receiveMessage(String message) throws JsonProcessingException {
        System.err.println("Otrzymałem");
        UserDetailsDto userDetailsDto = new UserDetailsDto("1","test1","test1","test","test1","test1","test","test", Set.of(),Set.of(),Set.of());
        ObjectMapper objectMapper = new ObjectMapper();
      return objectMapper.writeValueAsString(userDetailsDto);
    }




}
