package com.kamiloses.friendservice.controller;

import com.kamiloses.friendservice.dto.FriendShipDto;
import com.kamiloses.friendservice.service.FriendshipService;
import com.kamiloses.friendservice.service.Mapper;
import com.kamiloses.friendservice.service.RabbitFriendshipProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/friendList")
public class FriendshipController {

private final FriendshipService friendshipService;
private RabbitFriendshipProducer rabbitFriendshipProducer;
private Mapper mapper;
    public FriendshipController(FriendshipService friendshipService, RabbitFriendshipProducer rabbitFriendshipProducer, Mapper mapper) {
        this.friendshipService = friendshipService;
        this.rabbitFriendshipProducer = rabbitFriendshipProducer;
        this.mapper = mapper;
    }
//@GetMapping
//public Flux<FriendShipDto> getAllFriendsRelatedWithUser(@RequestParam(name = "username") String loggedUser){
//
//
//
//    return friendshipService.getAllUserFriends(loggedUser);
//}


}
