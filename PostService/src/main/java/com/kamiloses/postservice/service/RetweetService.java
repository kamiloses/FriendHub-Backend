package com.kamiloses.postservice.service;

import com.kamiloses.postservice.entity.RetweetEntity;
import com.kamiloses.postservice.exception.RetweetDatabaseFetchException;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import com.kamiloses.postservice.repository.PostRepository;
import com.kamiloses.postservice.repository.RetweetRepository;
import com.kamiloses.rabbitmq.exception.RabbitExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Import({RabbitPostProducer.class, RabbitExceptionHandler.class})

public class RetweetService {

    private final RetweetRepository retweetRepository;
    private final PostRepository postRepository;
    private final RabbitPostProducer rabbitPostProducer;

    public RetweetService(RetweetRepository retweetRepository, PostRepository postRepository, RabbitPostProducer rabbitPostProducer) {
        this.retweetRepository = retweetRepository;
        this.postRepository = postRepository;
        this.rabbitPostProducer = rabbitPostProducer;
    }

    public Mono<Void> retweetPost(String postId, String retweetedUserUsername) {

        return rabbitPostProducer.askForUserDetails(retweetedUserUsername).flatMap(userDetails ->
                Mono.just(RetweetEntity.builder()
                                .retweetedByUserId(userDetails.getId())
                                .originalPostId(postId)
                                .build())
                        .flatMap(retweetEntity ->
                                retweetRepository.save(retweetEntity)
                                        .onErrorResume(error -> {
                                            log.error("There was some problem with saving retweet to database");
                                            return Mono.error(RetweetDatabaseFetchException::new);
                                        }).then(
                                                postRepository.findById(postId)
                                                        .flatMap(fetchedPost -> {
                                                            fetchedPost.setRetweetCount(fetchedPost.getRetweetCount() + 1);
                                                            return postRepository.save(fetchedPost).then();
                                                        })
                                        )
                        ));
    }

    public Mono<Void> undoRetweet(String postId, String retweetedUserUsername) {
        return rabbitPostProducer.askForUserDetails(retweetedUserUsername).flatMap(userDetails ->
                retweetRepository.deleteByOriginalPostIdAndRetweetedByUserId(postId, userDetails.getId())
                        .onErrorResume(error -> {
                            log.error("There was some problem with removing retweet from database");
                            return Mono.error(RetweetDatabaseFetchException::new);})
                                    .then(postRepository.findById(postId)
                                            .flatMap(postEntity -> {
                                                postEntity.setRetweetCount(postEntity.getRetweetCount() - 1);
                                                return postRepository.save(postEntity);
                                            })
                                    )
                                    .then());
                        }


        public Mono<Boolean> isPostRetweetedByMe (String postId, String loggedUserUsername){
            return rabbitPostProducer.askForUserDetails(loggedUserUsername)
                    .flatMap(userDetails -> retweetRepository.existsByOriginalPostIdAndRetweetedByUserId(postId, userDetails.getId())
                            .onErrorResume(error -> {
                                log.error("There was some problem with fetching retweet from database");
                                return Mono.error(RetweetDatabaseFetchException::new);})

                    );


        }


    }
