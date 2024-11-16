package com.kamiloses.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {


    private String id;
    private String username;

    private String password;

    private String role = "ROLE_USER";
//todo uśuń potem od tąd i chyba jeszcze id
    private String firstName;
    private String lastName;

    private String bio;

    private String profileImageUrl;


    private Set<String> tweetsIds;

    private Set<String> followersIds;

    private Set<String> followingIds;


}
