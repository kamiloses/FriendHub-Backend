package com.kamiloses.friendservice.service;

import com.kamiloses.friendservice.dto.SearchedPeopleDto;
import com.kamiloses.friendservice.dto.UserDetailsDto;
import com.kamiloses.friendservice.entity.FriendshipEntity;
import com.kamiloses.friendservice.repository.FriendshipRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.Disposable;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class FriendsServiceTest {


    @Autowired
    FriendsService friendsService;

    @SpyBean
    FriendshipRepository friendshipRepository;

    @MockBean
    RabbitFriendsProducer rabbitFriendsProducer;

    final String searchedUsername="kamiloses";

    UserDetailsDto user1 = new UserDetailsDto("1", null, "kamiloses1", false, "Maciej", "Nowak");
    UserDetailsDto user2 = new UserDetailsDto("2", null, "kamiloses2", false, "Jan", "Kowalski");
    UserDetailsDto user3 = new UserDetailsDto("3", null, "kamiloses3", true, "Anna", "Wiśniewska");
    UserDetailsDto myUsername = new UserDetailsDto("4", null, "kamiloses", true, "Kamil", "Kurzaj");


    List<UserDetailsDto> searchedUsers = new ArrayList<>(List.of(user1, user2, user3));


    @BeforeEach
    void setUp() {
        friendshipRepository.deleteAll().block();
        FriendshipEntity friendshipEntity1 = new FriendshipEntity("1", "4", "2", new Date());
        FriendshipEntity friendshipEntity2 = new FriendshipEntity("2", "3", "4", new Date());
        friendshipRepository.saveAll(List.of(friendshipEntity1, friendshipEntity2)).collectList().block();

    }



    @Test
    @DisplayName("Should return 3 users: 2 displayed as friends, 1 as non-friend")
    void should_check_getPeopleWithSimilarUsername() {
        Mockito.when(rabbitFriendsProducer.getSimilarPeopleNameToUsername(searchedUsername)).thenReturn(searchedUsers);


        Mockito.when(rabbitFriendsProducer.askForUserDetails("kamiloses1")).thenReturn(user1);
        Mockito.when(rabbitFriendsProducer.askForUserDetails("kamiloses2")).thenReturn(user2);
        Mockito.when(rabbitFriendsProducer.askForUserDetails("kamiloses3")).thenReturn(user3);
        Mockito.when(rabbitFriendsProducer.askForUserDetails("kamiloses")).thenReturn(myUsername);

        StepVerifier.create(friendsService.getPeopleWithSimilarUsername(searchedUsername, myUsername.getUsername()).collectList())
                .expectNextMatches(users ->
                    users.size() == 3 &&
                            users.stream().anyMatch(user -> user.getUsername().equals("kamiloses1") && !user.getIsYourFriend()) &&
                            users.stream().anyMatch(user -> user.getUsername().equals("kamiloses2") && user.getIsYourFriend()) &&
                            users.stream().anyMatch(user -> user.getUsername().equals("kamiloses3") && user.getIsYourFriend())
                )
                .verifyComplete();
    }

               // .expectNextCount(3)

}