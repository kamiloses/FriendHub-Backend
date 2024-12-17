package com.kamiloses.authservice.rabbit;

import com.kamiloses.rabbitmq.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RabbitAuthListener {


    @RabbitListener(queues = RabbitConfig.Queue_Auth)
    public String receive_And_Resend_EncodedPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }


}
