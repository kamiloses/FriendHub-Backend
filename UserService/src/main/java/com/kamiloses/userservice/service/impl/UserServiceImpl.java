package com.kamiloses.userservice.service.impl;

import com.kamiloses.rabbitmq.RabbitConfig;
import com.kamiloses.rabbitmq.exception.RabbitExceptionHandler;
import com.kamiloses.userservice.dto.LoginDetails;
import com.kamiloses.userservice.dto.RegistrationDto;
import com.kamiloses.userservice.entity.UserEntity;
import com.kamiloses.userservice.exception.UserDatabaseFetchException;
import com.kamiloses.userservice.exception.UsernameAlreadyExistsException;
import com.kamiloses.userservice.repository.UserRepository;
import com.kamiloses.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Import(RabbitExceptionHandler.class)
@Slf4j
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    public UserServiceImpl(UserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public Mono<UserEntity> save(RegistrationDto user) {
        return   Mono.fromSupplier(()->(String)rabbitTemplate.convertSendAndReceive(RabbitConfig.AUTH_EXCHANGE, RabbitConfig.AUTH_ROUTING_KEY, user.getPassword()))
                .map(encodedPassword->{
                    UserEntity build = UserEntity.builder()
                            .username(user.getUsername())
                            .password(encodedPassword)
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .build();
                    System.err.println(build);
                    return build;}
                ).flatMap(userEntity->userRepository.existsByUsername(userEntity.getUsername())
                        .onErrorResume(error->{
                            log.error("There was some problem with fetching User");
                            return Mono.error(UserDatabaseFetchException::new);
                        })
                        .flatMap(exists->{
                            if (exists) {

                                return Mono.error(new UsernameAlreadyExistsException("Username already exists"));}


                            return userRepository.save(userEntity); }));
    }
    @Override
    public Mono<LoginDetails> findByUsernameOrId(String usernameOrId) {
        return userRepository.findByUsernameOrId(usernameOrId,usernameOrId)
                .onErrorResume(error->{
                    log.error("There was some problem with fetching User by id");
                    return Mono.error(UserDatabaseFetchException::new);})
                .map(userEntity -> new LoginDetails(userEntity.getUsername(), userEntity.getPassword()));
    }





    private UserEntity registrationDtoToUserEntity(RegistrationDto user, String encodedPassword) {

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(encodedPassword);
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        return userEntity;

    }
    }
