package com.kamiloses.likeservice.service;

import com.kamiloses.likeservice.entity.LikeEntity;
import com.kamiloses.likeservice.rabbit.RabbitLikeProducer;
import com.kamiloses.likeservice.repository.LikeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final RabbitLikeProducer rabbitLikeProducer;

    public LikeService(LikeRepository likeRepository, RabbitLikeProducer rabbitLikeProducer) {
        this.likeRepository = likeRepository;
        this.rabbitLikeProducer = rabbitLikeProducer;
    }

    //from suppliier
    public Mono<Void> likeThePost(String postId, String likedUserId) {
        return  rabbitLikeProducer.askForUserDetails(postId)
                .flatMap(userDetailsDto -> {
                    LikeEntity likeEntity = LikeEntity.builder()
                            .likedByUserId("123")
                            .originalPostId(postId)
                            .build();
                    System.err.println("DZIAŁA ");
                    return likeRepository.save(likeEntity)
                            .then(Mono.defer(() -> rabbitLikeProducer.sendPostIdToPostModule(postId)));

                });
    }





    //from suppliier
//    public Mono<Void> undoLike(String postId, String likedUserId) {
//       rabbitPostProducer.askForUserDetails(likedUserId)
//        return likeRepository.deleteByOriginalPostIdAndLikedByUserId(postId, userDetailsDto.getId())
//                .then(postRepository.findById(postId)
//                        .flatMap(postEntity -> {
//                            postEntity.setLikeCount(postEntity.getLikeCount() - 1);
//                            return postRepository.save(postEntity);
//                        })
//                )
//                .then();
    }


    //from suppliier
//    public Mono<Boolean> isPostLikedByMe(String postId, String loggedUserUsername) {
//        UserDetailsDto userDetailsDto = rabbitPostProducer.askForUserDetails(loggedUserUsername);
//        return likeRepository.existsByOriginalPostIdAndLikedByUserId(postId, userDetailsDto.getId());
//
//
//    }
//}
