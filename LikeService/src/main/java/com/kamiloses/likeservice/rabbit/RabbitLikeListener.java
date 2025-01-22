package com.kamiloses.likeservice.rabbit;

import com.kamiloses.likeservice.repository.LikeRepository;
import com.kamiloses.rabbitmq.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitLikeListener {


    private final RabbitLikeProducer rabbitLikeProducer;
    private final LikeRepository likeRepository;

    public RabbitLikeListener(RabbitLikeProducer rabbitLikeProducer, LikeRepository likeRepository) {
        this.rabbitLikeProducer = rabbitLikeProducer;
        this.likeRepository = likeRepository;
    }

    @RabbitListener(queues = RabbitConfig.IS_POST_LIKED_QUEUE)
    public Boolean receive_And_Resend_EncodedPassword(String postAndUserUsername) {
        String[] data = postAndUserUsername.split(":");
        String postId =  data[0];
        String username =  data[1];

        return isPostLikedByMe(postId,username);

    }






    private Boolean isPostLikedByMe(String postId, String loggedUserUsername) {
        return rabbitLikeProducer.askForUserDetails(loggedUserUsername)
                .flatMap(userDetailsDto ->likeRepository.existsByOriginalPostIdAndLikedByUserId(postId, userDetailsDto.getId())).block();


    }
}
