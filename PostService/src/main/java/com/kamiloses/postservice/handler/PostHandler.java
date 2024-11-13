package com.kamiloses.postservice.handler;

import com.kamiloses.postservice.dto.PostDto;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.service.PostService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class PostHandler {



private final PostService postService;

    public PostHandler(PostService postService) {
        this.postService = postService;
    }

    public Mono<ServerResponse> createPost(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(PostDto.class)
                .flatMap(post -> postService.createPost(post)
                        .flatMap(savedPost -> ServerResponse.ok().bodyValue(savedPost)));
    }





}









