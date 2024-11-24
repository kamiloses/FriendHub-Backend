package com.kamiloses.postservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data @AllArgsConstructor @NoArgsConstructor
public class UserDetailsDto {


    private String id;
    private String username;
    private String chatId;
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
