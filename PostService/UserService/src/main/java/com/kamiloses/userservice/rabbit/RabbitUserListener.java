package com.kamiloses.userservice.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitUserListener {



    @RabbitListener
     public String receiveMessage(String message) {

        return "5";
    }




}
