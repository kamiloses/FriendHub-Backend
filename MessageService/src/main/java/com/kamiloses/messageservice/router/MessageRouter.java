//package com.kamiloses.messageservice.router;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//
//@Configuration
//public class MessageRouter {
//
//
//    @Bean
//    public RouterFunction<ServerResponse> route(MessageHandler messageHandler) {
//        return RouterFunctions.route()
//                .GET("/api/message/{chatId}", messageHandler::showMessageRelatedWithChat)
//                .build();
//    }
//}
