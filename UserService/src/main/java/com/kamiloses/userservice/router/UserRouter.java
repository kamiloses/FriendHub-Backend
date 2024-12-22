//package com.kamiloses.userservice.router;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//public class UserRouter {
//
//
//    @Bean
//    public RouterFunction<ServerResponse> route(CommentHandler commentHandler) {
//        return RouterFunctions.route()
//                .GET("/api/comments/{id}", commentHandler::findCommentsRelatedWithPost)
//                .POST()
//
//
//    }
//
//
//
//}