package com.kamiloses.postservice.rabbit;

import com.kamiloses.postservice.repository.PostRepository;
import com.kamiloses.rabbitmq.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitPostListener {


private final PostRepository postRepository;

    public RabbitPostListener(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @RabbitListener(queues = RabbitConfig.AUTH_REQUEST_QUEUE)
    public void receivePostIdFromLikeModule(String postId){
        postRepository.findById(postId)
                .flatMap(fetchedPost -> {
                    fetchedPost.setLikeCount(fetchedPost.getLikeCount() + 1);
                    return postRepository.save(fetchedPost).then();
                }).subscribe();}






}
