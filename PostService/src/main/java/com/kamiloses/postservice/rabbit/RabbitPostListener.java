package com.kamiloses.postservice.rabbit;

import com.kamiloses.postservice.exception.PostDatabaseFetchException;
import com.kamiloses.postservice.repository.PostRepository;
import com.kamiloses.rabbitmq.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
@Slf4j
@Component
public class RabbitPostListener {


    private final PostRepository postRepository;

    public RabbitPostListener(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @RabbitListener(queues = RabbitConfig.POST_ADD_REQUEST_QUEUE)
    public Mono<Void> receivePostIdFromLikeModuleAndSave(String postId) {
        System.err.println("ADD");
        return postRepository.findById(postId)
                .onErrorResume(error -> {
                    log.error("Failed to fetch post from database, error: {}", error.getMessage());
                    return Mono.error(PostDatabaseFetchException::new);
                })
                .map(fetchedPost -> {
                    fetchedPost.setLikeCount(fetchedPost.getLikeCount() + 1);
                    return fetchedPost;
                })
                .flatMap(postRepository::save).then();


    }

    @RabbitListener(queues = RabbitConfig.POST_REMOVE_REQUEST_QUEUE)
    public Mono<Void> receivePostIdFromLikeModuleAndRemove(String postId) {
        return postRepository.findById(postId)
                .onErrorResume(error -> {
                    log.error("Failed to fetch post from database, error: {}", error.getMessage());
                    return Mono.error(PostDatabaseFetchException::new);
                })
                .map(fetchedPost -> {
                    fetchedPost.setLikeCount(fetchedPost.getLikeCount() - 1);
                    return fetchedPost;
                })
                .flatMap(postRepository::save).then();



    }

}