package com.kamiloses.userservice.service;

import com.kamiloses.rabbitmq.RabbitConfig;
import com.kamiloses.userservice.dto.LoginDetails;
import com.kamiloses.userservice.dto.RegistrationDto;
import com.kamiloses.userservice.entity.UserEntity;
import com.kamiloses.userservice.exception.UsernameAlreadyExistsException;
import com.kamiloses.userservice.repository.UserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final RabbitTemplate rabbitTemplate;

    public UserService(UserRepository userRepository, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Mono<UserEntity> save(RegistrationDto user) {
        String encodedPassword = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.AUTH_EXCHANGE, RabbitConfig.AUTH_ROUTING_KEY, user.getPassword());
        UserEntity userEntity = registrationDtoToUserEntity(user, encodedPassword);
             return userRepository.existsByUsername(userEntity.getUsername())
                     .flatMap(exists->{
                       if (exists) {

                           return Mono.error(new UsernameAlreadyExistsException("Username already exists"));}


                    return userRepository.save(userEntity); });
    }

    public Mono<LoginDetails> findByUsernameOrId(String usernameOrId) {
        return userRepository.findByUsernameOrId(usernameOrId,usernameOrId).map(userEntity -> new LoginDetails(userEntity.getUsername(), userEntity.getPassword()));
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
