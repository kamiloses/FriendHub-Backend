package com.kamiloses.postservice.controller;

import com.kamiloses.postservice.dto.CreatePostDto;
import com.kamiloses.postservice.dto.PostDto;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.service.PostService;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})

@Import(RabbitPostProducer.class)
public class PostController {

    private RabbitPostProducer rabbitPostProducer;
    private PostService postService;

    public PostController(RabbitPostProducer rabbitPostProducer, PostService postService) {
        this.rabbitPostProducer = rabbitPostProducer;
        this.postService = postService;
    }

    //todo upewnij sie czy nie powinno tu być postModel zamiast dto
    public List getPostsByUserId() {
        PostEntity post1 = new PostEntity("1", "1", "Hello, this is my first post!", LocalDateTime.of(2024, 11, 8, 10, 0, 0), 15, 2, 3, false);
        PostEntity post2 = new PostEntity(
                "2", "1", "This is a post about Spring Boot and MongoDB!", LocalDateTime.of(2024, 11, 8, 12, 30, 0), 30, 5, 3, false
        );

        PostEntity post3 = new PostEntity("3", "2", "Having fun learning new technologies! #coding #java", LocalDateTime.of(2024, 11, 8, 15, 0, 0), 50, 10, 3, false
        );
        UserDetailsDto userDetailsDto = rabbitPostProducer.askForUserDetails("joe");

        return List.of(post1, post2, post3);
    }


    @GetMapping
    public Flux<PostDto> getPostsRelatedWithUser() {
        return postService.getPostsRelatedWithUser("test");


    }
    @PostMapping("/{username}")
       public void createPost(@RequestBody CreatePostDto postDto,@PathVariable String username){
         postService.createPost(postDto,username).subscribe();

    }

    @GetMapping("/{id}")
    public Mono<PostDto> getPostById(@PathVariable String id) {
    return postService.getPostById(id);
    }





}