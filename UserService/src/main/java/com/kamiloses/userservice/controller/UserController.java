package com.kamiloses.userservice.controller;

import com.kamiloses.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class UserController {

private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
public Mono<Boolean> areCredentialsValid(@RequestParam String username, @RequestParam String password) {


return userService.existsByUsernameAndPassword(username,password);}


}
