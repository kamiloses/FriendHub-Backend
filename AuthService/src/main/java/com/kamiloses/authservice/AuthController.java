package com.kamiloses.authservice;

import com.kamiloses.authservice.security.jwt.LoginDetails;
import com.kamiloses.authservice.security.jwt.AuthResponse;
import com.kamiloses.authservice.security.jwt.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {


private JWTUtil jwtUtil;

    public AuthController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/loginJwt")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody LoginDetails loginDetails) {
        return WebClient.builder().baseUrl("http://localhost:8081")
                .build().get()
                .uri("/api/user/{username}", loginDetails.getUsername())
                .retrieve()
                .bodyToMono(LoginDetails.class)
                .flatMap(userDetails -> {
                    if (userDetails.getPassword().equals(loginDetails.getPassword())) {
                        String token = jwtUtil.generateToken(loginDetails.getUsername());
                        return Mono.just(ResponseEntity.ok(new AuthResponse(token)));
                    } else {
                        return Mono.error(new BadCredentialsException("Invalid username or password"));
                    }
                })
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")));
    }








}
