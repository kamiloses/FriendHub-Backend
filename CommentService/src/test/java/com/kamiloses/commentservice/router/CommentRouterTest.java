//package com.kamiloses.commentservice.router;
//
//import com.kamiloses.commentservice.dto.PublishCommentDto;
//import com.kamiloses.commentservice.repository.CommentRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import java.time.Duration;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@AutoConfigureWebTestClient
//@EnableDiscoveryClient(autoRegister = false)
//class CommentRouterTest {
//    @Autowired
//    private WebTestClient webTestClient;
//
//
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    private PublishCommentDto publishCommentDto;
//
//    @BeforeEach
//    public void setUp() {
//        webTestClient = webTestClient.mutate()
//                .responseTimeout(Duration.ofMillis(30000))
//                .build();
//
//
//        createPostDto = new CreatePostDto("text");
//
//    }
//
//
//}