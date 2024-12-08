package com.kamiloses.postservice.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PostRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PostHandler postHandler) {
        return RouterFunctions.route()
                .GET("/api/posts", postHandler::getAllPosts)
                .GET("/api/posts/{id}",postHandler::getPostById)
                .POST("/api/posts/{username}",postHandler::createPost)
                .build();


    }
}