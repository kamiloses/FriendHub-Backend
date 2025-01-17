package com.kamiloses.postservice.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class PostRouter {

    @Bean
    public RouterFunction<ServerResponse> route(PostHandler postHandler,RetweetHandler retweetHandler,LikeHandler likeHandler) {
        return RouterFunctions.route()
                .GET("/api/posts", postHandler::getAllPosts)
                .GET("/api/posts/{id}",postHandler::getPostById)
                .POST("/api/posts/{username}",postHandler::createPost)
                .POST("/api/retweet",retweetHandler::retweetPost)
                .DELETE("/api/retweet",retweetHandler::undoRetweet)
                .POST("/api/like",likeHandler::likePost)
                .DELETE("/api/like",likeHandler::unlikeThePost)
                .GET("/api/posts/user/{username}",postHandler::getPostsAndRetweetsRelatedWithUser)
                .build();


    }
}