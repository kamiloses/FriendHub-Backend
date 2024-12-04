package com.kamiloses.friendservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data @NoArgsConstructor
public class SearchedPeopleDto {

    private String id;
    private String username;

    private String firstName;
    private String lastName;

    private String bio;

    private String profileImageUrl;

    private Boolean isYourFriend;


}
