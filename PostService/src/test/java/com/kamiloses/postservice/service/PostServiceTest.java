package com.kamiloses.postservice.service;

import com.kamiloses.postservice.dto.PostDto;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import com.kamiloses.postservice.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static reactor.core.publisher.Mono.when;

@SpringBootTest
@Import({RabbitPostProducer.class})
class PostServiceTest {

    @Mock
    private RabbitPostProducer rabbitPostProducer;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private PostDto postDto;
    private UserDetailsDto userDetails;
    private PostEntity postEntity;

    @BeforeEach
    void setUp() {
        postDto = new PostDto();
        postDto.setContent("Test content");

        userDetails = new UserDetailsDto();
        userDetails.setId("1");
        userDetails.setUsername("Jan");

        postEntity = new PostEntity();
        postEntity.setId("1");
        postEntity.setContent("Test content");
        postEntity.setUserId("1");
    }//todo zamień potem te mocki na normalne JUnit

    @Test
    void test_create_post() {
        doReturn(userDetails).when(rabbitPostProducer).askForUserDetails("");
        doReturn(Mono.just(postEntity)).when(postRepository).save(any(PostEntity.class));

        Mono<PostEntity> result = postService.createPost(postDto,userDetails.getUsername());

        StepVerifier.create(result).expectNextMatches(
                savedPost -> savedPost.getUserId().equals(userDetails.getId())
                        && savedPost.getContent().equals(postEntity.getContent())).verifyComplete();

    }
}