package com.kamiloses.postservice.service;

import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.rabbitMq.RabbitPostSender;
import com.kamiloses.postservice.repository.PostRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class PostService {

private PostRepository postRepository;
private final RabbitPostSender rabbitPostSender;
    public PostService(PostRepository postRepository, RabbitPostSender rabbitPostSender) {
        this.postRepository = postRepository;
        this.rabbitPostSender = rabbitPostSender;
    }

    public Flux<PostEntity> findPostsRelatedWithUser(String userId){
       rabbitPostSender.askForUserDetails();

}
    //todo potem zamień na postDto




}
