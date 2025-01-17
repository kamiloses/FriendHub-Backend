package com.kamiloses.postservice.service;

import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.entity.LikeEntity;
import com.kamiloses.postservice.entity.RetweetEntity;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import com.kamiloses.postservice.repository.LikeRepository;
import com.kamiloses.postservice.repository.PostRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
@Service
public class LikeService {

private final LikeRepository likeRepository;
private final RabbitPostProducer rabbitPostProducer;
private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, RabbitPostProducer rabbitPostProducer, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.rabbitPostProducer = rabbitPostProducer;
        this.postRepository = postRepository;
    }


    public Mono<Void> likeThePost(String postId, String likedUserId) {
        UserDetailsDto userDetailsDto = rabbitPostProducer.askForUserDetails(likedUserId);
        return Mono.just(LikeEntity.builder()
                        .likedByUserId(userDetailsDto.getId())
                        .originalPostId(postId)
                        .build())
                .flatMap(likeEntity ->
                        likeRepository.save(likeEntity).then(
                                postRepository.findById(postId)
                                        .flatMap(fetchedPost -> {
                                            fetchedPost.setLikeCount(fetchedPost.getLikeCount() + 1);
                                            return postRepository.save(fetchedPost).then();
                                        })
                        )
                );
    }

    public Mono<Void> unlikeThePost(String postId, String likedUserId) {
        UserDetailsDto userDetailsDto = rabbitPostProducer.askForUserDetails(likedUserId);
        return likeRepository.deleteByOriginalPostIdAndLikedByUserId(postId, userDetailsDto.getId())
                .then(postRepository.findById(postId)
                        .flatMap(postEntity -> {
                            postEntity.setLikeCount(postEntity.getLikeCount() - 1);
                            return postRepository.save(postEntity);
                        })
                )
                .then();
    }



    public Mono<Boolean> isPostLikedByMe(String postId, String loggedUserUsername) {
        UserDetailsDto userDetailsDto = rabbitPostProducer.askForUserDetails(loggedUserUsername);
        return likeRepository.existsByOriginalPostIdAndLikedByUserId(postId, userDetailsDto.getId());


    }
}
