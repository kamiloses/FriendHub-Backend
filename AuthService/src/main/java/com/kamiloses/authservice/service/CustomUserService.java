package com.kamiloses.authservice.service;

import com.kamiloses.authservice.dto.UserDetailsDto;
import com.kamiloses.authservice.rabbit.RabbitAuthProducer;
import com.kamiloses.rabbitmq.RabbitConfig;
import com.kamiloses.rabbitmq.exception.RabbitExceptionHandler;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
@Component @Import({RabbitConfig.class, RabbitExceptionHandler.class})
public class CustomUserService implements ReactiveUserDetailsService {

    private final RabbitAuthProducer rabbitAuthProducer;

    public CustomUserService(RabbitAuthProducer rabbitAuthProducer) {
        this.rabbitAuthProducer = rabbitAuthProducer;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return Mono.fromSupplier(()->rabbitAuthProducer.askForUserDetails(username))
                 .flatMap(user-> {
                     if (user == null) return Mono.empty();
                     else return Mono.just(createUser(user));});

    }



private UserDetails createUser(UserDetailsDto user){

    return new User(user.getUsername(),user.getPassword(),roleToGrantedAuthority(user));
}








    private List<GrantedAuthority> roleToGrantedAuthority(UserDetailsDto user) {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(user.getRole()));
        return list;
    }


}