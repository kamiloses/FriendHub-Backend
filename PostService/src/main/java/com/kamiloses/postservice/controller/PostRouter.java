package com.kamiloses.postservice.controller;

import com.kamiloses.postservice.handler.PostHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Controller
public class PostRouter {

@Bean
    public RouterFunction<ServerResponse> routerFunction(PostHandler postHandler){

    return RouterFunctions.route().POST("/api/posts",postHandler::createPost).build();

}

}
