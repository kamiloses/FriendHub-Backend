package com.kamiloses.hashtagservice.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HashtagService {

    private RedisTemplate<String, String> redisTemplate;

 public List<String> getMostPopularHashtags() {
     Map<String,String> allHashtags = redisTemplate.opsForHash().;




 }









