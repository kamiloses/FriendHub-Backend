package com.kamiloses.postservice.rabbitMq;

import com.kamiloses.rabbitmq.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitPostSender {

    private final RabbitTemplate rabbitTemplate;

        private UserDetailsDto userDetailsdto;

    public RabbitPostSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    //todo zamień na obiekt
     public void  askForUserDetails(){

        String userDetails =(String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_To_User_Service, RabbitConfig.ROUTING_KEY_, "");


     }



}
