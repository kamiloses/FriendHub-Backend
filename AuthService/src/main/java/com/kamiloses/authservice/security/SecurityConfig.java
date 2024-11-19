package com.kamiloses.authservice.security;

import com.kamiloses.authservice.security.jwt.JWTAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private final JWTAuthenticationManager authenticationManager;

    public SecurityConfig(JWTAuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http
                .authorizeExchange(exchange -> exchange
                        .pathMatchers("/api/user/signup", "/api/user/test").permitAll()
                        .pathMatchers("/api/user/**").authenticated()
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .authenticationManager(authenticationManager)
                .build();


    }


//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception {
//        http.authorizeExchange(request->request.anyExchange().permitAll()).csrf(ServerHttpSecurity.CsrfSpec::disable);
//
//        return http.build();
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}