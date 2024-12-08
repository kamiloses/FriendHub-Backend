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

import java.util.Date;

@Service
@Import(RabbitPostProducer.class)
public class PostService {

    private final PostRepository postRepository;
    private final RabbitPostProducer rabbitPostProducer;


    public PostService(PostRepository postRepository, RabbitPostProducer rabbitPostProducer) {
        this.postRepository = postRepository;
        this.rabbitPostProducer = rabbitPostProducer;
    }

    public Mono<Void> createPost(CreatePostDto post, String username) {
        return Mono.fromSupplier(() ->
                rabbitPostProducer.askForUserDetails(username)).flatMap(user -> {

            PostEntity createdPost = PostEntity.builder()
                    .userId(user.getId())
                    .content(post.getContent())
                    .createdAt(new Date())
                    .build();
            return postRepository.save(createdPost).then();
        });

    }


    public Mono<PostDto> getPostById(String id) {
        return postRepository.findById(id)
                .flatMap(postEntity ->
                        Mono.fromSupplier(() -> rabbitPostProducer.askForUserDetails(postEntity.getUserId()))
                                .map(userEntity -> {


                                    PostDto postDto = PostDto.builder()
                                            .id(postEntity.getId())
                                            .user(userEntity)
                                            .content(postEntity.getContent())
                                            .createdAt(postEntity.getCreatedAt()).build();

                                    return postDto;
                                }));
    }


    public Flux<PostDto> getAllPosts() {
        return postRepository.findAll()
                .flatMap(postEntity -> Mono.fromSupplier(() -> rabbitPostProducer.askForUserDetails(postEntity.getUserId()))
                        .map(userDetails -> {
                            UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                                    .firstName(userDetails.getFirstName())
                                    .lastName(userDetails.getLastName())
                                    .username(userDetails.getUsername()).build();

                            return PostDto.builder().
                                    id(postEntity.getId())
                                    .user(userDetailsDto)
                                    .content(postEntity.getContent())
                                    .createdAt(postEntity.getCreatedAt()).build();

                        }));


    }
























    // Nie używana , przyda sie w przypadku gdy będę implementował funkcje sprawdzania czyjegoś profilu
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





