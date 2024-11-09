package com.kamiloses.postservice.service;

import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.repository.PostRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PostService {

private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Flux<PostEntity> findPostByUserId(String userId){

    return postRepository.findByUserId("1");
}
    //todo potem zamień na postDto




}
