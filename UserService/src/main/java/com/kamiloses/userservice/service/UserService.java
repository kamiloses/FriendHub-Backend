package com.kamiloses.userservice.service;

import com.kamiloses.rabbitmq.RabbitConfig;
import com.kamiloses.userservice.dto.LoginDetails;
import com.kamiloses.userservice.dto.RegistrationDto;
import com.kamiloses.userservice.entity.UserEntity;
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

    public Mono<Boolean> existsByUsernameAndPassword(String username,String password){


    return userRepository.existsByUsernameAndPassword(username,password);}





    public Mono<UserEntity> save(RegistrationDto user) {
        String encodedPassword = (String) rabbitTemplate.convertSendAndReceive(RabbitConfig.Exchange_Auth, RabbitConfig.ROUTING_KEY_Auth, user.getPassword());
        UserEntity userEntity = registrationDtoToUserEntity(user, encodedPassword);
        return userRepository.save(userEntity);
    }

private UserEntity registrationDtoToUserEntity(RegistrationDto user,String encodedPassword){

    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(user.getUsername());
    userEntity.setPassword(encodedPassword);
    userEntity.setFirstName(user.getFirstName());
    userEntity.setLastName(user.getLastName());
    return userEntity;

}

          //todo zmień nazwe potem tego registration albo utwórz nowe dto
    public Mono<LoginDetails> findByUsername(String username) {
        return userRepository.findByUsername(username).map(userEntity -> new LoginDetails(userEntity.getUsername(),userEntity.getPassword()));
    }
}
