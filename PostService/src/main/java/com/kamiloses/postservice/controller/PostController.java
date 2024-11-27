package com.kamiloses.postservice.controller;

import com.kamiloses.postservice.dto.CreatePostDto;
import com.kamiloses.postservice.dto.PostDto;
import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.service.PostService;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})

@Import(RabbitPostProducer.class)
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public  Flux<PostDto>getAllPosts(){
        return postService.getPosts();
    }



    @PostMapping("/{username}")
       public Mono<Void> createPost(@RequestBody CreatePostDto postDto,@PathVariable String username){
      return    postService.createPost(postDto,username).then();

    }

    @GetMapping("/{id}")
    public Mono<PostDto> getPostById(@PathVariable String id) {
    return postService.getPostById(id);
    }





}