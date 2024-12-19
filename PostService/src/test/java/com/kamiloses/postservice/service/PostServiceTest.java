package com.kamiloses.postservice.service;

import com.kamiloses.postservice.dto.CreatePostDto;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.exception.PostDatabaseFetchException;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import com.kamiloses.postservice.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {

    @MockBean
    private RabbitPostProducer rabbitPostProducer;

    @SpyBean
    private PostRepository postRepository;

    @Autowired
    PostService postService;


    private CreatePostDto createPostDto;
    private UserDetailsDto userDetailsDto;


    @BeforeEach
    void setUp() {
        postRepository.deleteAll().block();
          createPostDto = new CreatePostDto("text");

          userDetailsDto = new UserDetailsDto();
          userDetailsDto.setUsername("JNowak");
          userDetailsDto.setFirstName("Jan");
          userDetailsDto.setLastName("Nowak");

        doReturn(userDetailsDto).when(rabbitPostProducer).askForUserDetails(anyString());




    }

    @Test
    void should_create_post() {

        StepVerifier.create(postService.createPost(createPostDto,userDetailsDto.getUsername()))
                .expectComplete().verify();



        Assertions.assertEquals(1, postRepository.findAll().collectList().block().size());


    }
    @Test
    void should_check_create_post_throws_PostDatabaseFetchException() {

        doReturn(Mono.error(new PostDatabaseFetchException()))
                .when(postRepository).save(any());

        StepVerifier.create(postService.createPost(createPostDto, userDetailsDto.getUsername()))
                .expectError(PostDatabaseFetchException.class)
                .verify();

       Assertions.assertEquals(0, postRepository.findAll().collectList().block().size());
    }
















}