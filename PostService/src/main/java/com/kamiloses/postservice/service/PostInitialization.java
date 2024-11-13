package com.kamiloses.postservice.service;

import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostInitialization {

 private PostRepository postRepository;

 public PostInitialization(PostRepository postRepository) {
  this.postRepository = postRepository;
 }

// @PostConstruct
//public void init(){
// PostEntity  postEntity=PostEntity.builder()
//            .userId("user123")
//            .content("This is a sample post content")
//            .createdAt(LocalDateTime.now())
//            .likeCount(5)
//            .retweetCount(2)
//            .commentsCount(3)
//            .isDeleted(false)
//            .build();
//  System.err.println("zapisało");
//postRepository.save(postEntity).block();
//}


}
