package com.kamiloses.authservice.controller;

import com.kamiloses.authservice.dto.LoginDetails;
import com.kamiloses.authservice.dto.AuthResponse;
import com.kamiloses.authservice.jwt.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class AuthController {


    private final JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

     //todo ROUTER
    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody LoginDetails loginDetails) {
        return WebClient.builder().baseUrl("http://localhost:8081")
                .build().get()
                .uri("/api/user/{username}", loginDetails.getUsername())
                .retrieve()
                .bodyToMono(LoginDetails.class)
                .onErrorResume(e -> {
                    System.err.println("Error with fetching data: " + e.getMessage());
                    return Mono.empty();
                })
                .flatMap(userDetails -> {
                    if (passwordEncoder.matches(loginDetails.getPassword(), userDetails.getPassword())) {
                        String token = jwtUtil.generateToken(loginDetails.getUsername());


                        return Mono.just(ResponseEntity.ok(new AuthResponse(token)));
                    } else {

                        return Mono.error(new BadCredentialsException("Invalid username or password"));
                    }
                })
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")));
    }


}
