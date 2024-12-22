package com.kamiloses.friendservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class SearchedPeopleDto {

    private String id;
    private String username;

    private String firstName;
    private String lastName;

    private Boolean isYourFriend;


}
