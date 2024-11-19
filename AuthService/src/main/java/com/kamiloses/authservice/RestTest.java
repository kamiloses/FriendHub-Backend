package com.kamiloses.authservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RestTest {

@GetMapping("/a")
public Mono<String>a(){
    return Mono.just("hej");
}

}
