package com.kamiloses.commentservice.service;

import com.kamiloses.commentservice.entity.CommentEntity;
import com.kamiloses.commentservice.repository.CommentRepository;
import jakarta.annotation.PostConstruct;

import java.util.Date;
import java.util.List;

public class CommentInitialization {

    private final CommentRepository commentRepository;

    public CommentInitialization(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

//    @PostConstruct
//    public void init() {
//        CommentEntity commentEntity = new CommentEntity("1", "Pierwszy komentarz", new Date(), "user1", "67357f8a4229ec65dea898dd", null, 0, 5, 0);
//        CommentEntity commentEntity1 = new CommentEntity("2", "Drugi komentarz", new Date(), "user2", "67357f8a4229ec65dea898dd", null, 2, 3, 1);
//        CommentEntity commentEntity2 = new CommentEntity("3", "Pierwsza odpowiedź na drugi komentarz", new Date(), "user3", "67357f8a4229ec65dea898dd", "2", 0, 1, 0);
//        CommentEntity commentEntity3 = new CommentEntity("4", "Druga odpowiedz na drugi komentarz", new Date(), "user2", "67357f8a4229ec65dea898dd", "2", 2, 3, 1);
//        CommentEntity commentEntity4 = new CommentEntity("5", "ADSDAdsa", new Date(), "user3", "67357f8a4229ec65dea898dd", "6", 0, 1, 0);
//        CommentEntity commentEntity5 = new CommentEntity("6", "abc", new Date(), "user3", "67357f8a4229ec65dea898dd", "3", 0, 1, 0);
//
//
//    commentRepository.saveAll(List.of(commentEntity, commentEntity1, commentEntity2, commentEntity3, commentEntity4));}
//
//}
}