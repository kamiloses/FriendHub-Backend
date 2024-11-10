package com.kamiloses.postservice.rabbitMq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.rabbitmq.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RabbitPostSender {

    private final RabbitTemplate rabbitTemplate;

        private UserDetailsDto userDetailsdto;


    public RabbitPostSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    //todo zamień na obiekt
     public UserDetailsDto  askForUserDetails() {
         System.err.println("PRZED");
         String userDetailsAsString = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_To_User_Service, RabbitConfig.ROUTING_KEY_, "x");
         System.err.println(userDetailsAsString);
         try {
             ObjectMapper objectMapper = new ObjectMapper();
             return objectMapper.readValue(userDetailsAsString, UserDetailsDto.class);

         } catch (JsonProcessingException e) {
             throw new RuntimeException(e);
         }


     }

}
