package com.kamiloses.commentservice.service;

import com.kamiloses.commentservice.entity.CommentEntity;
import com.kamiloses.commentservice.repository.CommentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public class CommentsInitialization {

private final CommentRepository commentRepository;

    public CommentsInitialization(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostConstruct
    public void init() {
        if (commentRepository.findAll().collectList().block().size()==0){
        CommentEntity first = new CommentEntity("1", "Pierwszy komentarz", new Date(), "1",
                "2", null, 0, 5, 0);
        CommentEntity second = new CommentEntity("2", "Drugi komentarz", new Date(), "user2", "post1", null, 2, 3, 1);
        CommentEntity third = new CommentEntity("3", "Pierwsza odpowiedź na drugi komentarz", new Date(), "user3", "post1", "2", 0, 1, 0);
        CommentEntity fourth = new CommentEntity("4", "Druga odpowiedź na drugi komentarz", new Date(), "user2", "post1", "2", 2, 3, 1);

        commentRepository.saveAll(List.of(first, second, third, fourth)).collectList().block();
    }}


}
