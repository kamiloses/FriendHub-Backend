    package com.kamiloses.friendservice.websockets;

    import com.kamiloses.friendservice.dto.UserDetailsDto;
    import com.kamiloses.friendservice.service.FriendshipService;
    import org.springframework.data.redis.core.RedisTemplate;
    import org.springframework.messaging.handler.annotation.MessageMapping;
    import org.springframework.messaging.simp.SimpMessagingTemplate;
    import org.springframework.stereotype.Controller;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Set;

    @Controller
    public class WebSocketsController {

    private final FriendshipService friendshipService;
    private final RedisTemplate<String,String> redisTemplate;
        private final SimpMessagingTemplate messagingTemplate;


        public WebSocketsController(FriendshipService friendshipService, RedisTemplate<String, String> redisTemplate, SimpMessagingTemplate messagingTemplate) {
            this.friendshipService = friendshipService;
            this.redisTemplate = redisTemplate;
            this.messagingTemplate = messagingTemplate;
        }

        @MessageMapping("/chat.activeFriends")
        public void checkFriendsActivity(String myUsername) {

            List<String> friendsUsername = friendshipService.getAllUserFriends(myUsername).collectList().block()
                    .stream().map(UserDetailsDto::getUsername).toList();


            List<String> allYourFriendsOnline = checkFriendsOnlineStatus(friendsUsername);


            System.err.println("znajomi  użytkownika "+myUsername +"którzy są online" +allYourFriendsOnline);




            messagingTemplate.convertAndSend("/topic/public/friendsOnline",allYourFriendsOnline );

        }



        //todo jak wymyśle sposób lepszy na wyszukiwanie użytkowników online to wtedy zmienić ten kod bo jest beznadziejny

        public boolean isUserOnline(String friendUsername) {
            Set<String> sessionIds = redisTemplate.keys("*");
            for (String sessionId : sessionIds) {
                String storedUsername = redisTemplate.opsForValue().get(sessionId);
                if (storedUsername != null && storedUsername.equals(friendUsername)) {
                    return true;
                }
            }
            return false;
        }
        public List<String> checkFriendsOnlineStatus(List<String> friendsUsernames) {
            List<String> onlineFriends = new ArrayList<>();
            for (String friend : friendsUsernames) {
                if (isUserOnline(friend)) {
                    onlineFriends.add(friend);
                }
            }
            return onlineFriends;
        }


    }
