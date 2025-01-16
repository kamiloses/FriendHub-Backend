package com.kamiloses.postservice.router;

import com.kamiloses.postservice.service.LikeService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
@Component
public class LikeHandler {


    private final LikeService likeService;

    public LikeHandler(LikeService likeService) {
        this.likeService = likeService;
    }

    public Mono<ServerResponse> likePost(ServerRequest request) {
        String postId = request.queryParam("postId").get();
        String likedUserUsername = request.queryParam("username").get();

        return likeService.likeThePost(postId, likedUserUsername).then(ServerResponse.ok().build());
    }

    public Mono<ServerResponse> unlikeThePost(ServerRequest request) {
        String postId = request.queryParam("postId").get();
        String likedUserUsername = request.queryParam("username").get();

        return likeService.unlikeThePost(postId, likedUserUsername).then(ServerResponse.ok().build());
    }
}
