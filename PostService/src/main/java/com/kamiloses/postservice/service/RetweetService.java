package com.kamiloses.postservice.service;

import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.entity.RetweetEntity;
import com.kamiloses.postservice.repository.PostRepository;
import com.kamiloses.postservice.repository.RetweetRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class RetweetService {

    private final RetweetRepository retweetRepository;
    private final PostRepository postRepository;
    public RetweetService(RetweetRepository retweetRepository, PostRepository postRepository) {
        this.retweetRepository = retweetRepository;
        this.postRepository = postRepository;
    }

    public Mono<Void> retweetPost(String postId, String retweetedUserId) {
        return Mono.just(RetweetEntity.builder()
                        .retweetedByUserId(retweetedUserId)
                        .originalPostId(postId)
                        .build())
                .flatMap(retweetEntity ->
                        retweetRepository.save(retweetEntity).then(
                                 postRepository.findById(postId)
                                        .flatMap(fetchedPost -> {
                                            fetchedPost.setRetweetCount(fetchedPost.getRetweetCount() + 1);
                                            return postRepository.save(fetchedPost).then();
                                        })
                        )
                );
    }

    public Mono<Void> undoRetweet(String postId, String retweetedUserId) {
        return retweetRepository.deleteByOriginalPostIdAndRetweetedByUserId(postId, retweetedUserId)
                .then(postRepository.findById(postId)
                        .flatMap(postEntity -> {
                            postEntity.setRetweetCount(postEntity.getRetweetCount() - 1);
                            return postRepository.save(postEntity);
                        })
                )
                .then();
    }



    public Mono<Boolean> isPostRetweetedByMe(String postId, String browsingUserId) {
       return retweetRepository.existsByOriginalPostIdAndRetweetedByUserId(postId, browsingUserId);


    }



}
