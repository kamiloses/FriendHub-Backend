package com.kamiloses.postservice.service;

import com.kamiloses.postservice.dto.PostDto;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import com.kamiloses.postservice.repository.PostRepository;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Import(RabbitPostProducer.class)
public class PostService {

    private final PostRepository postRepository;
    private final RabbitPostProducer rabbitPostProducer;
    private final MapperService mapperService;


    public PostService(PostRepository postRepository, RabbitPostProducer rabbitPostProducer, MapperService mapperService) {
        this.postRepository = postRepository;
        this.rabbitPostProducer = rabbitPostProducer;
        this.mapperService = mapperService;
    }




    public Mono<PostEntity> createPost(PostDto postDto) {
        UserDetailsDto userDetails = rabbitPostProducer.askForUserDetails("Joe");
        PostEntity postEntity = new PostEntity();
        postEntity.setUserId(userDetails.getId());
        postEntity.setContent(postDto.getContent());
        postEntity.setCreatedAt(LocalDateTime.now());
        return postRepository.save(postEntity);
    }


    public Flux<PostDto> getPostsRelatedWithUser(String username) {
        UserDetailsDto userDetailsDto = rabbitPostProducer.askForUserDetails(username);

        return postRepository.findByUserId(userDetailsDto.getId()).map(
                postEntity -> {
                    PostDto postDto = new PostDto();
                    postDto.setId(postEntity.getId());
                    postDto.setUser(userDetailsDto);
                    postDto.setContent(postEntity.getContent());
                    postDto.setCreatedAt(postEntity.getCreatedAt());
            return postDto;
                }

        );



    }


}





