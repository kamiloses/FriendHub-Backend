package com.kamiloses.likeservice.service;

import com.kamiloses.likeservice.entity.LikeEntity;
import com.kamiloses.likeservice.rabbit.RabbitLikeProducer;
import com.kamiloses.likeservice.repository.LikeRepository;
import com.kamiloses.rabbitmq.exception.RabbitExceptionHandler;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Import({RabbitExceptionHandler.class})
public class LikeService {

    private final LikeRepository likeRepository;
    private final RabbitLikeProducer rabbitLikeProducer;

    public LikeService(LikeRepository likeRepository, RabbitLikeProducer rabbitLikeProducer) {
        this.likeRepository = likeRepository;
        this.rabbitLikeProducer = rabbitLikeProducer;
    }


    public Mono<Void> likeThePost(String postId, String likedUserIdOrUsername) {
        return rabbitLikeProducer.askForUserDetails(likedUserIdOrUsername)
                .flatMap(userDetailsDto -> {
                    LikeEntity likeEntity = LikeEntity.builder()
                            .likedByUserId(userDetailsDto.getId())
                            .originalPostId(postId)
                            .build();
                    return likeRepository.save(likeEntity)
                            .then(rabbitLikeProducer.sendPostIdForLikeAdding(postId));

                });
    }


    public Mono<Void> undoLike(String postId, String likedUserId) {
        return rabbitLikeProducer.askForUserDetails(likedUserId)
                .flatMap(userDetailsDto ->
                        likeRepository.deleteByOriginalPostIdAndLikedByUserId(postId, userDetailsDto.getId())
                                .then(rabbitLikeProducer.sendPostIdForLikeRemoval(postId)));

    }




}
