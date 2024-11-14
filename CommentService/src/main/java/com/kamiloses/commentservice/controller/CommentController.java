package com.kamiloses.commentservice.controller;

import com.kamiloses.commentservice.dto.CommentDto;
import com.kamiloses.commentservice.service.CommentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/comments")
public class CommentController {


private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

@GetMapping("/{id}")
public Flux<CommentDto> findCommentsRelatedWithPost(@PathVariable(name = "id") String postId){
        return commentService.findCommentsRelatedWithPost(postId);


}




}
