package com.kamiloses.postservice.router;

import com.kamiloses.postservice.service.RetweetService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
@Component
public class RetweetHandler {


 private final RetweetService retweetService;

    public RetweetHandler(RetweetService retweetService) {
        this.retweetService = retweetService;
    }
//
//    public Mono<ServerResponse> retweetPost(ServerRequest request) {
//        String postId = request.queryParam("postId").get();
//        String retweetedUserUsername = request.queryParam("username").get();
//
//        return retweetService.retweetPost(postId, retweetedUserUsername).then(ServerResponse.ok().build());
//
//    }
//
//        public Mono<ServerResponse> undoRetweet(ServerRequest request) {
//            String postId = request.queryParam("postId").get();
//            String retweetedUserUsername= request.queryParam("username").get();
//
//            return retweetService.undoRetweet(postId,retweetedUserUsername).then(ServerResponse.ok().build());
//
//
//        }



}