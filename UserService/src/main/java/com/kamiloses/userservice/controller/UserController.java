package com.kamiloses.userservice.controller;

import com.kamiloses.userservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
public class UserController {

private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
public Mono<Boolean> areCredentialsValid(@RequestParam String username, @RequestParam String password) {


return userService.existsByUsernameAndPassword(username,password);}


}
