package com.kamiloses.postservice.router;

import com.kamiloses.postservice.dto.CreatePostDto;
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
public Mono<ServerResponse> createPost(ServerRequest request) {
return request.bodyToMono(CreatePostDto.class).flatMap(post ->

    postService.createPost(post,request.pathVariable("username")).then(ServerResponse.ok().build()));}

public Mono<ServerResponse> getPostById(ServerRequest request) {
 return postService.getPostById(request.pathVariable("id")).flatMap(post ->ServerResponse.ok().bodyValue(post));


}
public Mono<ServerResponse> getAllPosts(ServerRequest request) {
    String param = request.queryParam("username").get();
    return postService.getAllPosts(param).collectList().flatMap(post -> ServerResponse.ok().bodyValue(post));

}


//    public Mono<ServerResponse> getPostsAndRetweetsRelatedWithUser(ServerRequest request) {
//        return postService.getPostsAndRetweetsRelatedWithUser(request.pathVariable("username")).collectList().flatMap(post -> ServerResponse.ok().bodyValue(post));
//
//    }


}
