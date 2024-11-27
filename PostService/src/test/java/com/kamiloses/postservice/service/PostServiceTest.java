package com.kamiloses.postservice.service;

import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import com.kamiloses.postservice.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
@ActiveProfiles("test")
class PostServiceTest {

    @MockBean
    private RabbitPostProducer rabbitPostProducer;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    PostService postService;


    private UserDetailsDto user1;
    private UserDetailsDto user2;



    @BeforeEach
    void setUp() {
        postRepository.deleteAll().block();
        user1 = UserDetailsDto.builder().id("1").username("kamiloses1").build();
        user2 = UserDetailsDto.builder().id("2").username("kamiloses2").build();


        PostEntity postEntity1 = new PostEntity();
        postEntity1.setUserId(user1.getId());

        PostEntity postEntity2 = new PostEntity();
        postEntity2.setUserId(user1.getId());

        PostEntity postEntity3 = new PostEntity();
        postEntity3.setUserId(user2.getId());

        postRepository.saveAll(List.of(postEntity1, postEntity2, postEntity3)).collectList().block();

    }

    @Test
    void should_check_if_getPostsRelatedWithUser_works() {
             doReturn(user1).when(rabbitPostProducer).askForUserDetails(anyString());

        assertEquals(2, postService.getPostsRelatedWithUser("").collectList().block().size());

         Mockito.reset(rabbitPostProducer);


        doReturn(user2).when(rabbitPostProducer).askForUserDetails(anyString());

        assertEquals(1, postService.getPostsRelatedWithUser("").collectList().block().size());


//        StepVerifier.create(postService.getPostsRelatedWithUser("").collectList())
//                .expectNextMatches(post)


    }
}