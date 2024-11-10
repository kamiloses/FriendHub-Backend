package com.kamiloses.postservice.controller;

import com.kamiloses.postservice.dto.PostDto;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.rabbitMq.RabbitPostSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {
 private RabbitPostSender rabbitPostSender;

    public PostController(RabbitPostSender rabbitPostSender) {
        this.rabbitPostSender = rabbitPostSender;
    }

    @GetMapping //todo upewnij sie czy nie powinno tu być postModel zamiast dto
    public List getPostsByUserId() {
        PostEntity post1 = new PostEntity("1", "1", "Hello, this is my first post!", LocalDateTime.of(2024, 11, 8, 10, 0, 0), 15, 2,3, false);
        PostEntity post2 = new PostEntity(
                "2", "1", "This is a post about Spring Boot and MongoDB!", LocalDateTime.of(2024, 11, 8, 12, 30, 0), 30, 5,3, false
        );

        PostEntity post3 = new PostEntity("3", "2", "Having fun learning new technologies! #coding #java", LocalDateTime.of(2024, 11, 8, 15, 0, 0), 50, 10,3, false
        );
        UserDetailsDto userDetailsDto = rabbitPostSender.askForUserDetails();
          post1.setContent(userDetailsDto.getFirstName());

          return List.of(post1,post2,post3);
    }



}
