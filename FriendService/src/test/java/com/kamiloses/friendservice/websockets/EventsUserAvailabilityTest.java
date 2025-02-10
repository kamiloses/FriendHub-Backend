//package com.kamiloses.friendservice.websockets;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class EventsUserAvailabilityTest {
//
//    @Mock
//    private StringRedisTemplate redisTemplate;
//
//
//    @Mock
//    private StompHeaderAccessor stompHeaderAccessor;
//
//
//
//    @Test
//    void testHandleWebSocketConnectListener() {
//        String testUsername = "jNowak";
//        String testSessionId = "12345";
//
//        Mockito.when(stompHeaderAccessor.getSessionId()).thenReturn(testSessionId);
//
//
//        System.err.println(stompHeaderAccessor.getSessionId());
//        //when(stompHeaderAccessor.getMessage()).thenReturn("username=[" + testUsername + "]");
//       // when(stompHeaderAccessor.toString()).thenReturn("username=[" + testUsername + "]");
//
//        //eventsUserAvailability.handleWebSocketConnectListener(sessionConnectedEvent);
//
//    //    verify(redisTemplate).opsForValue().set(testSessionId, testUsername);
//    }
//}