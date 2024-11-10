package com.kamiloses.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

public static String Queue_To_User_Service="requestToUserService";
public static String Exchange_To_User_Service="exchangeToUserService";
    public static final String ROUTING_KEY_ = "userService";


@Bean
    public Queue queue_To_User_Service() {
    return new Queue(Queue_To_User_Service);
}


    @Bean
    public DirectExchange exchange_userCredentials() {
        return new DirectExchange(Exchange_To_User_Service);
    }



    @Bean//todo upewnij sie że nazwa jest dobra
    public Binding binding_User_Service() {
        return BindingBuilder.bind(queue_To_User_Service()).to(exchange_userCredentials()).with(ROUTING_KEY_);
    }

}
