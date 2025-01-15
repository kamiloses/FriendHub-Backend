package com.kamiloses.postservice.service;

import com.kamiloses.postservice.dto.CreatePostDto;
import com.kamiloses.postservice.dto.PostDto;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.exception.PostDatabaseFetchException;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import com.kamiloses.postservice.repository.PostRepository;
import com.kamiloses.rabbitmq.exception.RabbitExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Service
@Import({RabbitPostProducer.class, RabbitExceptionHandler.class})
public class PostService {

    private final PostRepository postRepository;
    private final RabbitPostProducer rabbitPostProducer;
    private final RetweetService retweetService;

    public PostService(PostRepository postRepository, RabbitPostProducer rabbitPostProducer, RetweetService retweetService) {
        this.postRepository = postRepository;
        this.rabbitPostProducer = rabbitPostProducer;
        this.retweetService = retweetService;
    }

    public Mono<Void> createPost(CreatePostDto post, String username) {
        return Mono.fromSupplier(() ->
                rabbitPostProducer.askForUserDetails(username)).flatMap(user -> {

            PostEntity createdPost = PostEntity.builder()
                    .userId(user.getId())
                    .content(post.getContent())
                    .createdAt(new Date())
                    .build();
            return postRepository.save(createdPost).
                    onErrorResume(error->{
                       log.error("There was some problem with saving post to database");
                      return Mono.error(PostDatabaseFetchException::new);
                    }).
                    then();
        });

    }


    public Mono<PostDto> getPostById(String id) {
        return postRepository.findById(id).onErrorResume(error->{
            log.error("There was some problem with fetching specific post");
            return Mono.error(PostDatabaseFetchException::new);
        })
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


    public Flux<PostDto> getAllPosts(String loggedUserId) {
        return postRepository.findAll().onErrorResume(error->{
            log.error("There was some problem with fetching all posts");
            return Mono.error(PostDatabaseFetchException::new);
        })
                .flatMap(postEntity -> Mono.fromSupplier(() -> rabbitPostProducer.askForUserDetails(postEntity.getUserId()))
                        .map(userDetails -> {
                            UserDetailsDto userDetailsDto = UserDetailsDto.builder()
                                    .firstName(userDetails.getFirstName())
                                    .lastName(userDetails.getLastName())
                                    .username(userDetails.getUsername()).build();
                            System.err.println("WYWOŁUJE SIE ");


                            return retweetService.isPostRetweetedByMe(postEntity.getId(), loggedUserId)
                                    .map(isRetweetedByMe -> PostDto.builder()
                                            .id(postEntity.getId())
                                            .user(userDetailsDto)
                                            .content(postEntity.getContent())
                                            .createdAt(postEntity.getCreatedAt())
                                            .retweetCount(postEntity.getRetweetCount())
                                            .isRetweetedByMe(isRetweetedByMe)
                                            .build());

                        })).flatMap(postDto->postDto);

    }
}





