package com.kamiloses.friendservice.controller;

import com.kamiloses.friendservice.dto.SearchedPeopleDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.service.FriendsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST,RequestMethod.DELETE})
public class FriendsController {


    //Router configuration does not work when WebSockets are included in the pom file.

private final FriendsService friendsService;


    public FriendsController(FriendsService friendsService) {
        this.friendsService = friendsService;
    }

    @GetMapping
public Flux<UserDetailsDto> getAllFriendsRelatedWithUser(@RequestParam(name = "username") String loggedUser) {
        return friendsService.getAllUserFriends(loggedUser);
    }


            @GetMapping("/{username}")
            public Flux<SearchedPeopleDto> getPeopleByUsername(@PathVariable String username,@RequestHeader String myUsername ){

              return friendsService.getPeopleWithSimilarUsername(username,myUsername);
            }





            @PostMapping()
            public Mono<Void> addFriend(@RequestHeader String friendUsername, @RequestHeader String myUsername) {



         return friendsService.addToFriendList(friendUsername,myUsername).then(); }


          @DeleteMapping()
    public Mono<Void> deleteFriend(@RequestHeader String friendUsername, @RequestHeader String myUsername) {

    return friendsService.removeFriend(friendUsername,myUsername);
          }



}
