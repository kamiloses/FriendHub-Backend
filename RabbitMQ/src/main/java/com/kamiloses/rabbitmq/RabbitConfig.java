package com.kamiloses.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {



                           //user


    public static final String USER_INFO_REQUEST_QUEUE = "userInfoRequestQueue";
    public static final String USER_INFO_EXCHANGE = "userInfoExchange";
    public static final String USER_INFO_ROUTING_KEY = "userInfoRoutingKey";

    @Bean
    public Queue userInfoRequestQueue() {
        return new Queue(USER_INFO_REQUEST_QUEUE);
    }

    @Bean
    public Binding userRequestBinding() {
        return BindingBuilder.bind(userInfoRequestQueue())
                .to(userInfoExchange())
                .with(USER_INFO_ROUTING_KEY);
    }

    @Bean
    public DirectExchange userInfoExchange() {
        return new DirectExchange(USER_INFO_EXCHANGE);
    }



                                      //friend

    public static final String FRIENDS_INFO_REQUEST_QUEUE = "friendsInfoRequestQueue";
    public static final String FRIENDS_INFO_ROUTING_KEY = "friendsInfoRoutingKey";


    @Bean
    public Queue friendsInfoRequestQueue() {
        return new Queue(FRIENDS_INFO_REQUEST_QUEUE);
    }

    @Bean
    public Binding friendsInfoBinding() {
        return BindingBuilder.bind(friendsInfoRequestQueue())
                .to(userInfoExchange())
                .with(FRIENDS_INFO_ROUTING_KEY);
    }





                                    //auth
    public static final String AUTH_REQUEST_QUEUE = "authRequestQueue";
    public static final String AUTH_ROUTING_KEY = "authRoutingKey";
    public static final String AUTH_EXCHANGE = "authExchange";

    @Bean
    public Queue authRequestQueue() {
        return new Queue(AUTH_REQUEST_QUEUE);
    }

    @Bean
    public DirectExchange authExchange() {
        return new DirectExchange(AUTH_EXCHANGE);
    }

    @Bean
    public Binding authBinding() {
        return BindingBuilder.bind(authRequestQueue())
                .to(authExchange())
                .with(AUTH_ROUTING_KEY);
    }




    public static final String Queue_Searched_People = "searchedPeople";
    public static final String Exchange_searchedPeople = "exchangeSearchedPeople";
    public static final String ROUTING_KEY_searchedPeople = "searchedPeople";


    @Bean
    public Queue searchedPeopleQueue() {
        return new Queue(Queue_Searched_People);
    }

    @Bean
    public DirectExchange exchange_searchedPeople() {
        return new DirectExchange(Exchange_searchedPeople);
    }
    @Bean
    public Binding SearchedPeopleExchangeWithQueue() {
        return BindingBuilder.bind(searchedPeopleQueue()).to(exchange_searchedPeople()).with(ROUTING_KEY_searchedPeople);
    }






                                         //removing like amount of the post


    public static final String POST_ADD_REQUEST_QUEUE = "postAddRequestQueue";
    public static final String POST_REMOVE_REQUEST_QUEUE = "postRemoveRequestQueue";
    public static final String IS_POST_LIKED_QUEUE = "isPostLikedRequestQueue";

    public static final String POST_OPERATIONS_EXCHANGE = "postOperationsExchange";
    public static final String POST_ADD_ROUTING_KEY = "postAddRoutingKey";
    public static final String POST_REMOVE_ROUTING_KEY = "postRemoveRoutingKey";
    public static final String IS_POST_LIKED_ROUTING_KEY = "isPostLikedRoutingKey";


//
//    @Bean
//    public Queue postAddQueue() {
//        return new Queue(POST_ADD_REQUEST_QUEUE);
//    }
//
//
//    @Bean
//    public Queue postRemoveQueue() {
//        return new Queue(POST_REMOVE_REQUEST_QUEUE);
//    }
//
//
//    @Bean
//    public Queue isPostLikedQueue() {
//        return new Queue(IS_POST_LIKED_QUEUE);
//    }
//
//
//
//
//
//    @Bean
//    public DirectExchange exchange_postOperations() {
//        return new DirectExchange(POST_OPERATIONS_EXCHANGE);
//    }
//    @Bean
//    public Binding postAddBinding() {
//        return BindingBuilder.bind(postAddQueue()).to(exchange_postOperations()).with(POST_ADD_ROUTING_KEY);
//    }
//    @Bean
//    public Binding postRemoveBinding() {
//        return BindingBuilder.bind(postRemoveQueue()).to(exchange_postOperations()).with(POST_REMOVE_ROUTING_KEY);
//    }
//
//
//    @Bean
//    public Binding isPostLikedBinding() {
//        return BindingBuilder.bind(isPostLikedQueue()).to(exchange_postOperations()).with(IS_POST_LIKED_ROUTING_KEY);
//    }




}
