package com.kamiloses.likeservice.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
@Configuration
public class LikeRouter {


    @Bean
    public RouterFunction<ServerResponse> route(LikeHandler likeHandler) {
        return RouterFunctions.route()
                .POST("/api/like", likeHandler::likePost).build();
//               .DELETE("/api/like", likeHandler::undoLike).build();
    }

}
