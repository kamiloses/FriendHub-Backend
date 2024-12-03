package com.kamiloses.commentservice.service;

import com.kamiloses.commentservice.entity.CommentEntity;
import com.kamiloses.commentservice.repository.CommentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
public class CommentInitialization {

    private final CommentRepository commentRepository;

    public CommentInitialization(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostConstruct
    public void init() {
        commentRepository.deleteAll().block();
        CommentEntity commentEntity = new CommentEntity("1", "Pierwszy komentarz", new Date(), "1", "674be6fe34d59a4ae01dd7c7", null, 0, 5, 0);
        CommentEntity commentEntity1 = new CommentEntity("2", "Drugi komentarz", new Date(), "2", "674be6fe34d59a4ae01dd7c7", null, 2, 3, 1);
        CommentEntity commentEntity2 = new CommentEntity("3", "Pierwsza odpowiedź na drugi komentarz", new Date(), "3", "674be6fe34d59a4ae01dd7c7", "2", 0, 1, 0);
        CommentEntity commentEntity3 = new CommentEntity("4", "Druga odpowiedz na drugi komentarz", new Date(), "2", "674be6fe34d59a4ae01dd7c7", "2", 2, 3, 1);
        CommentEntity commentEntity4 = new CommentEntity("5", "ADSDAdsa", new Date(), "3", "674be6fe34d59a4ae01dd7c7", "6", 0, 1, 0);
        CommentEntity commentEntity5 = new CommentEntity("6", "abc", new Date(), "3", "674be6fe34d59a4ae01dd7c7", "3", 0, 1, 0);


    commentRepository.saveAll(List.of(commentEntity, commentEntity1, commentEntity2, commentEntity3, commentEntity4,commentEntity5)).collectList().block();}

}
