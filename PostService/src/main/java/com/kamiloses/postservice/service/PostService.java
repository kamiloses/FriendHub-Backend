package com.kamiloses.postservice.service;

import com.kamiloses.postservice.dto.PostDto;
import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.repository.PostRepository;
import com.kamiloses.rabbitmq.service.CredentialsService;
import com.kamiloses.rabbitmq.service.UserDetailsDto;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Import(CredentialsService.class)
public class PostService {

    private final PostRepository postRepository;
    private final CredentialsService credentialsService;

    public PostService(CredentialsService credentialsService, PostRepository postRepository) {
        this.credentialsService = credentialsService;
        this.postRepository = postRepository;
    }



    public Mono<PostEntity> createPost(PostDto postDto) {
        UserDetailsDto userDetails = credentialsService.askForUserDetails();
        PostEntity postEntity = new PostEntity();
        postEntity.setUserId(userDetails.getId());
        postEntity.setContent(postDto.getContent());
        postEntity.setCreatedAt(LocalDateTime.now());
        return postRepository.save(postEntity);
    }


}





