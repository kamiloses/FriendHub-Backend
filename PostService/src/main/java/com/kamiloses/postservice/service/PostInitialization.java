package com.kamiloses.postservice.service;

import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.repository.PostRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostInitialization {

    private PostRepository postRepository;

    public PostInitialization(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostConstruct
    public void init() {
        postRepository.deleteAll().block();
        PostEntity postEntity = PostEntity.builder().userId("1").content("jakiś tekst").createdAt(LocalDateTime.now()).likeCount(5).retweetCount(2).commentsCount(3).isDeleted(false).build();
        PostEntity postEntity1 = PostEntity.builder().userId("2").content("asdsadsadasdassad").createdAt(LocalDateTime.of(2024, 10, 15, 14, 30)).likeCount(8).retweetCount(4).commentsCount(2).isDeleted(false).build();
        PostEntity postEntity2 = PostEntity.builder().userId("3").content("cxzczxcxzvxcvvcx").createdAt(LocalDateTime.of(2024, 9, 20, 9, 45)).likeCount(10).retweetCount(3).commentsCount(5).isDeleted(false).build();


        System.err.println("zapisało");
        postRepository.saveAll(List.of(postEntity,postEntity1,postEntity2)).collectList().block();
    }


}
