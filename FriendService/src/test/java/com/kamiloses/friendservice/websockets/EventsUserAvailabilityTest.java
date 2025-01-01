//package com.kamiloses.friendservice.websockets;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
////@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//class EventsUserAvailabilityTest {
//
//    @Mock
//    private StringRedisTemplate redisTemplate;
//
//    @Mock
//    private SimpMessagingTemplate messagingTemplate;
//
//    @InjectMocks
//    private EventsUserAvailability eventsUserAvailability;
//
//    @Mock
//    private SessionConnectedEvent sessionConnectedEvent;
//
//    @Mock
//    private StompHeaderAccessor stompHeaderAccessor;
//
//
//    nativeHeaders={username=[jNowak]
//
//    @Test
//    void testHandleWebSocketConnectListener() {
//        String testUsername = "jNowak";
//        String testSessionId = "12345";
//        when(stompHeaderAccessor.getSessionId()).thenReturn(testSessionId);
//        when(stompHeaderAccessor.getMessage()).thenReturn("username=[" + testUsername + "]");
//       // when(stompHeaderAccessor.toString()).thenReturn("username=[" + testUsername + "]");
//
//        eventsUserAvailability.handleWebSocketConnectListener(sessionConnectedEvent);
//
//    //    verify(redisTemplate).opsForValue().set(testSessionId, testUsername);
//    }
//}