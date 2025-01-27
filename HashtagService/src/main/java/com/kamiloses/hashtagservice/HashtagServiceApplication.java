package com.kamiloses.hashtagservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HashtagServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HashtagServiceApplication.class, args);
    }

}
