package com.kamiloses.postservice.rabbitMq;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Set;
@Data
public class UserDetailsDto {


    private String username;

    private String password;

    private String email;

    private String firstName;
    private String lastName;

    private String bio;

    private String profileImageUrl;


    private Set<String> tweetsIds;

    private Set<String> followersIds;

    private Set<String> followingIds;



}
