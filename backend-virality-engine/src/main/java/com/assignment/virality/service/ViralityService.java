package com.assignment.virality.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViralityService {

    private final StringRedisTemplate redisTemplate;

    public void updateScore(Long postId, String type) {

        String key = "post:" + postId + ":virality_score";

        long increment = switch (type.toUpperCase()) {
            case "LIKE" -> 20;     // Human Like
            case "COMMENT" -> 50;  // Human Comment
            case "BOT" -> 1;       // Bot Reply
            default -> 0;
        };

        if (increment > 0) {
            redisTemplate.opsForValue().increment(key, increment);
        }
    }

    // ✅ Optional: Get current score (useful for testing/debug)
    public Long getScore(Long postId) {
        String key = "post:" + postId + ":virality_score";
        String value = redisTemplate.opsForValue().get(key);
        return value != null ? Long.parseLong(value) : 0L;
    }
}