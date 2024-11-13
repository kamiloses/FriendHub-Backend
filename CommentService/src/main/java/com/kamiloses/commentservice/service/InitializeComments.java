package com.kamiloses.commentservice.service;

import com.kamiloses.commentservice.entity.CommentEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InitializeComments {


    @PostConstruct
    public void init() {

        new CommentEntity("1", "Pierwszy komentarz", new Date("2024-11-01T12:00:00"), "1",
                "2", null, 0, 5, 0);
        new CommentEntity("2", "Drugi komentarz", new Date("2024-11-02T12:10:00"), "user2", "post1", null, 2, 3, 1);
        new CommentEntity("3", "Pierwsza odpowiedź na drugi komentarz", new Date("2024-11-02T12:20:00"), "user3", "post1", "2", 0, 1, 0);
        new CommentEntity("4", "Druga odpowiedź na drugi komentarz", new Date("2024-11-02T12:10:00"), "user2", "post1", "2", 2, 3, 1);


    }


}
