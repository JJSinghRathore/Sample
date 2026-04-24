package com.assignment.virality.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final StringRedisTemplate redisTemplate;

    public void handleNotification(Long userId, String message) {

        String cooldownKey = "notif:cooldown:" + userId;
        String listKey = "user:" + userId + ":pending_notifs";

        Boolean exists = redisTemplate.hasKey(cooldownKey);

        if (Boolean.TRUE.equals(exists)) {
            // Add to pending list
            redisTemplate.opsForList().rightPush(listKey, message);
        } else {
            // Send immediately (simulate)
            System.out.println("Push Notification Sent to User " + userId);

            // Set cooldown (15 min)
            redisTemplate.opsForValue().set(cooldownKey, "1", Duration.ofMinutes(15));
        }
    }
}