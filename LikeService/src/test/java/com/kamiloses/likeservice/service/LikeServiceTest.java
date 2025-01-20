package com.kamiloses.likeservice.service;

import com.kamiloses.postservice.entity.PostEntity;
import com.kamiloses.postservice.repository.LikeRepository;
import com.kamiloses.postservice.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


@ActiveProfiles("test")
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LikeServiceTest {


    @Autowired
    private LikeService likeService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    PostEntity post1;
    PostEntity post2;
    PostEntity post3;




    @BeforeAll
    public void setUp() {
        postRepository.deleteAll().block();
        likeRepository.deleteAll().block();
        post1 = PostEntity.builder().id("1").userId("1").content("post1").build();
        post2 = PostEntity.builder().id("2").userId("1").content("post2").build();
        post3 = PostEntity.builder().id("3").userId("3").content("post2").build();
        postRepository.saveAll(List.of(post1, post2, post3)).collectList().block();
    }


    @Test//first post should have 2 likes and third post should have 1 like
    @Order(1)
    void shouldLikePost() {
        likeService.likeThePost(post1.getId(), "kamiloses").block();

        likeService.likeThePost(post1.getId(), "kamiloses2").block();

        likeService.likeThePost(post3.getId(), "kamiloses").block();

        Assertions.assertEquals(3, postRepository.findAll().collectList().block().size());
        Assertions.assertEquals(3, likeRepository.findAll().collectList().block().size());


        Assertions.assertEquals(2, postRepository.findById("1").block().getLikeCount());
        Assertions.assertEquals(0, postRepository.findById("2").block().getLikeCount());
        Assertions.assertEquals(1, postRepository.findById("3").block().getLikeCount());
    }


    @Test
    @Order(2)
//The first post should have lost 1 like
    void shouldUnlikePost() {
        likeService.unlikeThePost("1", "kamiloses").block();

        Assertions.assertEquals(2, likeRepository.findAll().collectList().block().size());
        Assertions.assertEquals(1, postRepository.findById(post1.getId()).block().getLikeCount());
        Assertions.assertEquals(1, postRepository.findById(post3.getId()).block().getLikeCount());
    }












}