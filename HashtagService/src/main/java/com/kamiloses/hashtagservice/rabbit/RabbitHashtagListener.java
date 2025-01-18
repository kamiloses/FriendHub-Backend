//package com.kamiloses.hashtagservice.rabbit;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.time.Instant;
//import java.util.List;
//
//@Component
//public class RabbitHashtagListener {
//
//
//    private RedisTemplate<String, String> redisTemplate;
//
//
//    public RabbitHashtagListener(RedisTemplate<String, String> redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//
//    @RabbitListener()
//    public void receiveHashtagsFromPostAndAddToRedis(String hashtags) throws JsonProcessingException {
//        long currentTime = Instant.now().getEpochSecond();
//
//        List.of("#LOL","#LOL","#HAHA","#HAHA","#HAHA","#XD").forEach(hashtag-> {
//                                                   //key             value            score
//            redisTemplate.opsForZSet().add("hashtag:"+hashtag,"",currentTime);
//
//            long oldestAllowedTime = currentTime - 86400;
//
//            redisTemplate.opsForZSet().removeRangeByScore("hashtag", 0, oldestAllowedTime);
//
//                    }
//       );
//
//    }
//}