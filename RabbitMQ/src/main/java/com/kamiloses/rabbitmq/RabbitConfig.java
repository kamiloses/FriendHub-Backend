package com.kamiloses.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String Queue_To_User_Service = "requestToUserService";
    public static final String Exchange_To_User_Service = "exchangeToUserService";
    public static final String ROUTING_KEY_ = "userService";
    public static final String Routing_Key_Friends_Details = "friendsDetails";


    public static final String Queue_For_Friends_Details = "requestForFriendsDetails";


    @Bean
    public Queue queue_To_User_Service() {
        return new Queue(Queue_To_User_Service);
    }

    @Bean
    public Queue queue_For_Friends_Details() {
        return new Queue(Queue_For_Friends_Details);
    }

    @Bean
    public DirectExchange exchange_userCredentials() {
        return new DirectExchange(Exchange_To_User_Service);
    }


    @Bean//todo upewnij sie że nazwa jest dobra
    public Binding binding_User_Service() {
        return BindingBuilder.bind(queue_To_User_Service()).to(exchange_userCredentials()).with(ROUTING_KEY_);
    }
    @Bean
    public Binding Binding_Friends_Details_Key_With_Friend_Details_Queue() {
        return BindingBuilder.bind(queue_For_Friends_Details()).to(exchange_userCredentials()).with(Routing_Key_Friends_Details);
    }



}
