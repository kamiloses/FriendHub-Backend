package com.kamiloses.commentservice.controller;

import com.kamiloses.commentservice.dto.CommentDto;
import com.kamiloses.commentservice.dto.PublishCommentDto;
import com.kamiloses.commentservice.service.CommentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
    @RequestMapping("/api/comments")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})

public class CommentController {


private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

@GetMapping("/{id}")
public Flux<CommentDto> findCommentsRelatedWithPost(@PathVariable(name = "id") String postId) {
    return commentService.findCommentsRelatedWithPost(postId);
}

//    @PostMapping
//    public void publishComments(@RequestBody CommentDto commentDto ) {
//      commentService.publishComment(commentDto);
//


    @PostMapping
    public Mono<Void> publishComment(@RequestBody PublishCommentDto commentDto,@RequestParam String username) {
        return commentService.publishComment(commentDto,username);

    }







}
