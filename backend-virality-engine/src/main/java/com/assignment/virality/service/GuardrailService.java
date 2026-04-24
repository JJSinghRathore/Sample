package com.assignment.virality.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class GuardrailService {

    private final StringRedisTemplate redisTemplate;

    // ✅ Horizontal Cap (max 100 bot replies per post)
    public boolean checkAndIncrementBotCount(Long postId) {

        String key = "post:" + postId + ":bot_count";

        Long count = redisTemplate.opsForValue().increment(key);

        if (count != null && count > 100) {
            return false;
        }
        return true;
    }

    // ✅ Vertical Cap (depth <= 20)
    public boolean checkDepth(int depthLevel) {
        return depthLevel <= 20;
    }

    // ✅ Cooldown (bot cannot interact with same human within 10 min)
    public boolean checkAndSetCooldown(Long botId, Long humanId) {

        String key = "cooldown:bot_" + botId + ":human_" + humanId;

        Boolean exists = redisTemplate.hasKey(key);

        if (Boolean.TRUE.equals(exists)) {
            return false;
        }

        redisTemplate.opsForValue().set(key, "1", Duration.ofMinutes(10));
        return true;
    }
}