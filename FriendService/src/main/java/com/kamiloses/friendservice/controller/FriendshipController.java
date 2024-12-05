package com.kamiloses.friendservice.controller;

import com.kamiloses.friendservice.dto.SearchedPeopleDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.service.FriendshipService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST,RequestMethod.DELETE})
public class FriendshipController {

private final FriendshipService friendshipService;


    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @GetMapping
public Flux<UserDetailsDto> getAllFriendsRelatedWithUser(@RequestParam(name = "username") String loggedUser) {
        return friendshipService.getAllUserFriends(loggedUser);
    }







            @GetMapping("/{username}")
            public List<SearchedPeopleDto> getPeopleByUsername(@PathVariable String username,@RequestHeader String myUsername ){

              return friendshipService.getPeopleWithSimilarUsername(username,myUsername);
            }





            @PostMapping()
            public Mono<Void> addFriend(@RequestHeader String friendUsername, @RequestHeader String myUsername) {



         return friendshipService.addToFriendList(friendUsername,myUsername).then(); }


          @DeleteMapping()
    public Mono<Void> deleteFriend(@RequestHeader String friendUsername, @RequestHeader String myUsername) {

    return friendshipService.removeFriend(friendUsername,myUsername);
          }



}
