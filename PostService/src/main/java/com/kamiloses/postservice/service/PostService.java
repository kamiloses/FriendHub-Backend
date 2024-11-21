package com.kamiloses.postservice.service;

import com.kamiloses.postservice.dto.CreatePostDto;
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
import java.util.Date;

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




    public Mono<PostEntity> createPost(CreatePostDto post,String username) {
        UserDetailsDto userDetails = rabbitPostProducer.askForUserDetails(username);
        PostEntity createdPost = PostEntity.builder()
                .userId(userDetails.getId())
                .content(post.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        return postRepository.save(createdPost);
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
public Mono<PostDto> getPostById(String id){
    UserDetailsDto userDetailsDto = rabbitPostProducer.askForUserDetails(id);
     return    postRepository.findById(id).map(
                     postEntity -> {
                         PostDto postDto = new PostDto();
                         postDto.setId(postEntity.getId());
                         postDto.setUser(userDetailsDto);
                         postDto.setContent(postEntity.getContent());
                         postDto.setCreatedAt(postEntity.getCreatedAt());
                         return postDto;

                     });}




}





