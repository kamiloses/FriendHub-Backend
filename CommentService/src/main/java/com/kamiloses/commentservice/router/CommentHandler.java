package com.kamiloses.commentservice.router;

import com.kamiloses.commentservice.dto.PublishCommentDto;
import com.kamiloses.commentservice.service.CommentService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class CommentHandler {


    private final CommentService commentService;

    public CommentHandler(CommentService commentService) {
        this.commentService = commentService;
    }


    public Mono<ServerResponse> findCommentsRelatedWithPost(ServerRequest request) {

        return commentService.findCommentsRelatedWithPost(request.pathVariable("id")).collectList()
                .flatMap(comments -> ServerResponse.ok().bodyValue(comments));
    }


    public Mono<ServerResponse> publishComment(ServerRequest request) {
        String username = request.queryParam("username").get();

        return request.bodyToMono(PublishCommentDto.class)
                .flatMap(comment -> commentService.publishComment(comment, username))
                .then(ServerResponse.ok().build());

    }


}
