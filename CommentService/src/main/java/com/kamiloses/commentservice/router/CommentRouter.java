package com.kamiloses.commentservice.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CommentRouter {


    @Bean
    public RouterFunction<ServerResponse> route(CommentHandler commentHandler) {
        return RouterFunctions.route()
                .GET("/api/comments/{id}", commentHandler::findCommentsRelatedWithPost)
                .POST("/api/comments", commentHandler::publishComment)
                .build();


    }
}
