package com.kamiloses.friendservice.controller;

import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.service.FriendshipService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/friends")
public class FriendshipController {

private final FriendshipService friendshipService;


    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping
public Flux<UserDetailsDto> getAllFriendsRelatedWithUser(@RequestParam(name = "username") String loggedUser){


    return friendshipService.getAllUserFriends(loggedUser);
}


}
