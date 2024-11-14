package com.kamiloses.userservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document @Data
@AllArgsConstructor @NoArgsConstructor
public class UserEntity {
    @Id
    private String id;

    @Indexed(unique = true)
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
