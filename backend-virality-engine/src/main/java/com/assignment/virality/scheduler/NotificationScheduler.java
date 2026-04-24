package com.assignment.virality.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class NotificationScheduler {

    private final StringRedisTemplate redisTemplate;

    // Runs every 5 minutes (testing purpose)
    @Scheduled(fixedRate = 300000)
    public void processNotifications() {

        // Get all users with pending notifications
        Set<String> keys = redisTemplate.keys("user:*:pending_notifs");

        if (keys == null || keys.isEmpty()) {
            return;
        }

        for (String key : keys) {

            // Count pending notifications
            Long count = redisTemplate.opsForList().size(key);

            if (count != null && count > 0) {

                // Extract userId from key (user:{id}:pending_notifs)
                String userId = key.split(":")[1];

                // Log summarized notification
                System.out.println(
                        "Summarized Push Notification: Bot X and " +
                                count + " others interacted with User " + userId + "'s posts."
                );

                // Clear the list after processing
                redisTemplate.delete(key);
            }
        }
    }
}