package com.kamiloses.userservice.router;

import com.kamiloses.userservice.entity.UserEntity;
import com.kamiloses.userservice.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {



    private final UserService userService;


    public UserHandler(UserService userService) {
        this.userService = userService;
    }
    public Mono<ServerResponse> saveUser(ServerRequest request) {
        return request.bodyToMono(UserEntity.class).flatMap(user->ServerResponse.ok().bodyValue("User signed up successfully"));


    }

    public Mono<ServerResponse> getUsernameAndPasswordOfUser(ServerRequest request) {
       return userService.findByUsernameOrId(request.pathVariable("username"))
                .flatMap(user->ServerResponse.ok().bodyValue(user));
    }




}