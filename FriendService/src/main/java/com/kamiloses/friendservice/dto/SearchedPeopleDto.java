package com.kamiloses.friendservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class SearchedPeopleDto {

    private String id;
    private String username;

    private String firstName;
    private String lastName;

    private Boolean isYourFriend;


}
