package com.kamiloses.authservice.security;

import com.kamiloses.authservice.dto.UserDetailsDto;
import com.kamiloses.authservice.rabbit.RabbitAuthProducer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
@Component
public class CustomUserService implements ReactiveUserDetailsService {

    private final RabbitAuthProducer rabbitAuthProducer;

    public CustomUserService(RabbitAuthProducer rabbitAuthProducer) {
        this.rabbitAuthProducer = rabbitAuthProducer;
    }


    @Override
    public Mono<UserDetails> findByUsername(String username) {
        UserDetailsDto userDetails = rabbitAuthProducer.askForUserDetails(username);
        System.err.println("User"+userDetails);
            if (userDetails == null) {
                return Mono.empty();
            }
        else return Mono.just(createUser(userDetails));


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