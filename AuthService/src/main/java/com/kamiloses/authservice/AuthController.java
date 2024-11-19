package com.kamiloses.authservice;

import com.kamiloses.authservice.security.jwt.LoginDetails;
import com.kamiloses.authservice.security.jwt.AuthResponse;
import com.kamiloses.authservice.security.jwt.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class AuthController {


private JWTUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/test")
    public Mono<String> a(){

        return Mono.just("test");
    }




    @PostMapping("/loginJwt")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody LoginDetails loginDetails) {
        return WebClient.builder().baseUrl("http://localhost:8081")
                .build().get()
                .uri("/api/user/{username}", loginDetails.getUsername())
                .retrieve()
                .bodyToMono(LoginDetails.class)
                .flatMap(userDetails -> {
                    if (passwordEncoder.matches(loginDetails.getPassword(),userDetails.getPassword())) {
                        String token = jwtUtil.generateToken(loginDetails.getUsername());
                        return Mono.just(ResponseEntity.ok(new AuthResponse(token)));
                    } else {

                        return Mono.error(new BadCredentialsException("Invalid username or password"));
                    }
                })//todo usuń to niżej (chyba)
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid username or password")));
    }








}
