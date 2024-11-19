package com.kamiloses.authservice.security.jwt;


import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JWTAuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;

    public JWTAuthenticationManager(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }


    @Override
    public Mono<Authentication> authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        String username = jwtUtil.extractUsername(token);
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build()
                .get()
                .uri("/api/user/{username}", username)
                .retrieve()
                .bodyToMono(LoginDetails.class)
                .flatMap(loginDetails -> {
                    if (jwtUtil.validateToken(token,loginDetails.getUsername())) {
                        Authentication auth = new UsernamePasswordAuthenticationToken(username, token, authentication.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                        return Mono.just(auth);
                    } else {
                        System.err.println("ŻLe");
                        return Mono.error(new AuthenticationException("Invalid JWT token") {
                        });
                    }
                });
    }

    public ServerAuthenticationConverter authenticationConverter() {
        return exchange -> {
            String token = exchange.getRequest().getHeaders().getFirst("Authorization");
            System.err.println("Received token: " + token);

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                return Mono.just(new UsernamePasswordAuthenticationToken(token, token));
            }

            System.out.println("Authorization header is missing or invalid");
            return Mono.empty();
        };
    }
}
