package com.kamiloses.likeservice.service;

import com.kamiloses.likeservice.dto.UserDetailsDto;
import com.kamiloses.likeservice.entity.LikeEntity;
import com.kamiloses.likeservice.rabbit.RabbitPostProducer;
import com.kamiloses.likeservice.repository.LikeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final RabbitPostProducer rabbitPostProducer;

    public LikeService(LikeRepository likeRepository, RabbitPostProducer rabbitPostProducer) {
        this.likeRepository = likeRepository;
        this.rabbitPostProducer = rabbitPostProducer;
    }

    //from suppliier
    public Mono<Void> likeThePost(String postId, String likedUserId) {
        System.err.println("WYWOŁUJE");
        return Mono.fromSupplier(() -> rabbitPostProducer.askForUserDetails(likedUserId))
                .flatMap(userDetailsDto -> {
                    LikeEntity likeEntity = LikeEntity.builder()
                            .likedByUserId(userDetailsDto.getId())
                            .originalPostId(postId)
                            .build();
                    System.err.println("DZIAŁA "+userDetailsDto.getId());
                    return likeRepository.save(likeEntity)
                            .then(Mono.defer(() -> rabbitPostProducer.sendPostIdToPostModule(postId)));

                });
    }





    //from suppliier
//    public Mono<Void> undoLike(String postId, String likedUserId) {
//        UserDetailsDto userDetailsDto = rabbitPostProducer.askForUserDetails(likedUserId);
//        return likeRepository.deleteByOriginalPostIdAndLikedByUserId(postId, userDetailsDto.getId())
//                .then(postRepository.findById(postId)
//                        .flatMap(postEntity -> {
//                            postEntity.setLikeCount(postEntity.getLikeCount() - 1);
//                            return postRepository.save(postEntity);
//                        })
//                )
//                .then();
//    }


    //from suppliier
    public Mono<Boolean> isPostLikedByMe(String postId, String loggedUserUsername) {
        UserDetailsDto userDetailsDto = rabbitPostProducer.askForUserDetails(loggedUserUsername);
        return likeRepository.existsByOriginalPostIdAndLikedByUserId(postId, userDetailsDto.getId());


    }
}
