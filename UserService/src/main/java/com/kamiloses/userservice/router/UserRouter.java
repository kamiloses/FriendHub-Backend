//package com.kamiloses.userservice.router;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//@Configuration
//public class UserRouter {
//
//
//    //todo sprawdz integracje @valid
//
//   @Bean
//    public RouterFunction<ServerResponse> route(UserHandler userHandler) {
//
//        return RouterFunctions.route()
//                .GET("/api/user/{username}", userHandler::getUsernameAndPasswordOfUser)
//                .POST("/api/user/signup",userHandler::saveUser)
//                .build();
//
//
//    }
//
//
//
//}