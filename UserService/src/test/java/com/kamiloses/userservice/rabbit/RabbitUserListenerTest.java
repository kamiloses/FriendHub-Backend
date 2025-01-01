package com.kamiloses.userservice.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kamiloses.userservice.dto.FriendShipDto;
import com.kamiloses.userservice.entity.UserEntity;
import com.kamiloses.userservice.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class RabbitUserListenerTest {

    @Autowired
    private RabbitUserListener rabbitListener;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
     void setUp(){

        userRepository.deleteAll().block();
        UserEntity user1 = new UserEntity("1", "kamiloses1", "password1", "Jan", "Kowalski");
        UserEntity user2 = new UserEntity("2", "kamiloses2", "password2", "Maciej", "Nowak");
        UserEntity user3 = new UserEntity("3", "kamiloses3", "password3", "Kamil", "Kurzaj");

        userRepository.saveAll(List.of(user1,user2,user3)).collectList().block();

    }

    @Test
     @Disabled
    void Should_Check_receiveAndResendFriendsDetails_Works() throws JsonProcessingException {
        FriendShipDto friendShipDto1 = new FriendShipDto("1",null);
        FriendShipDto friendShipDto2 = new FriendShipDto("2",null);
        FriendShipDto friendShipDto3 = new FriendShipDto("3",null);




        String usersIds = objectMapper.writeValueAsString(List.of(friendShipDto1,friendShipDto2,friendShipDto3));

        String friendsDetails = rabbitListener.receive_And_Resend_FriendsDetails(usersIds);
        Assertions.assertEquals(3,friendsDetails);

    }
}