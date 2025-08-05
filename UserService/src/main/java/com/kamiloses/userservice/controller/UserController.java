package com.kamiloses.userservice.controller;

import com.kamiloses.userservice.dto.LoginDetails;
import com.kamiloses.userservice.dto.RegistrationDto;
import com.kamiloses.userservice.dto.RegistrationResponse;
import com.kamiloses.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public Mono<ResponseEntity<RegistrationResponse>> signup(@RequestBody @Valid RegistrationDto user) {
        return userService.save(user)
                .map(savedUser -> ResponseEntity.ok(new RegistrationResponse("User signed up successfully")));
    }








    @GetMapping("/{username}")
    public Mono<LoginDetails> getUsernameAndPasswordOfUser(@PathVariable String username) {
        return userService.findByUsernameOrId(username)
                .switchIfEmpty(Mono.error(new UserDoesNotExistException("User not found")));}


}