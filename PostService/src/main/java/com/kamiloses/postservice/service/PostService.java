package com.kamiloses.postservice.service;

import com.kamiloses.postservice.dto.PostDto;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.rabbitMq.RabbitPostSender;
import com.kamiloses.postservice.repository.PostRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PostService {

private PostRepository postRepository;
private final RabbitPostSender rabbitPostSender;
    public PostService(PostRepository postRepository, RabbitPostSender rabbitPostSender) {
        this.postRepository = postRepository;
        this.rabbitPostSender = rabbitPostSender;
    }

//    public Flux<PostEntity> findPostsRelatedWithMe(String userId){
//       rabbitPostSender.askForUserDetails();
//         postRepository.findByUserId()
//        return
//

  public Mono<Void> createPost(PostDto postDto) {
      UserDetailsDto userDetails = rabbitPostSender.askForUserDetails();
      PostEntity postEntity = new PostEntity();
      postEntity.setUserId();
      postEntity.setContent(postDto.getContent());
      postEntity.setCreatedAt(LocalDateTime.now());

  }


    }


//    public Mono<List<PostDto>> getPosts() {
//        return postRepository.findAll()
//                .flatMap(post ->
//                        rabbitPostSender.askForUserDetails()
//                                .map(userDetailsDto ->
//                                        new PostDto(
//                                                post.getId(),
//                                                userDetailsDto,
//                                                post.getContent(),
//                                                post.getCreatedAt(),
//                                                post.getLikeCount(),
//                                                post.getRetweetCount(),
//                                                post.getCommentsCount(),
//                                                post.isDeleted()
//                                        )
//                                )
//                )
//                .collectList();
//    }



