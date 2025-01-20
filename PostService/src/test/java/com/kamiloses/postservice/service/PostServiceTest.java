package com.kamiloses.postservice.service;

import com.kamiloses.postservice.dto.CreatePostDto;
import com.kamiloses.postservice.dto.UserDetailsDto;
import com.kamiloses.postservice.exception.PostDatabaseFetchException;
import com.kamiloses.postservice.rabbit.RabbitPostProducer;
import com.kamiloses.postservice.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostServiceTest {


    @MockBean
    private RabbitPostProducer rabbitPostProducer;

    @SpyBean
    private PostRepository postRepository;

    @Autowired
    PostService postService;


    private CreatePostDto createPostDto;

    private UserDetailsDto userDetailsDto;


    @BeforeAll
    void setUp() {
        postRepository.deleteAll().block();
        createPostDto = new CreatePostDto("text");

        userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUsername("JNowak");
        userDetailsDto.setFirstName("Jan");
        userDetailsDto.setLastName("Nowak");


        Mockito.when(rabbitPostProducer.askForUserDetails(anyString())).thenReturn(Mono.just(userDetailsDto));


    }

    @Test
    @Order(1)
    void should_create_post() {

        StepVerifier.create(postService.createPost(createPostDto, userDetailsDto.getUsername()))
                .expectComplete().verify();


        Assertions.assertEquals(1, postRepository.findAll().collectList().block().size());


    }


    @Test
    @Order(2)
    void should_remove_post() {


    }


}