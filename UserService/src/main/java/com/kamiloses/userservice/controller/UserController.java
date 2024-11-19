package com.kamiloses.userservice.controller;

import com.kamiloses.userservice.dto.LoginDetails;
import com.kamiloses.userservice.dto.RegistrationDto;
import com.kamiloses.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
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









    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> signup(@RequestBody RegistrationDto user) {
        return userService.save(user)
                .map(savedUser -> ResponseEntity.ok("User signed up successfully"));
    }


    @GetMapping("/{username}")
    public Mono<LoginDetails> getUsernameAndPasswordOfUser(@PathVariable String username) {
        return userService.findByUsername(username)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }


}
