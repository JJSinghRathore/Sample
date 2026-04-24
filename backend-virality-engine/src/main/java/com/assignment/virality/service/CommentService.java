package com.assignment.virality.service;

import com.assignment.virality.entity.Comment;
import com.assignment.virality.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final StringRedisTemplate redisTemplate;
    private final ViralityService viralityService;
    private final NotificationService notificationService;

    public String addComment(Long postId,
                             Long authorId,
                             String content,
                             int depthLevel,
                             String authorType) {

        // 🔒 Vertical Cap (Depth Check)
        if (depthLevel > 20) {
            return "❌ Depth limit exceeded (max 20)";
        }

        // 🤖 BOT LOGIC
        if ("BOT".equalsIgnoreCase(authorType)) {

            // 🔒 Horizontal Cap (max 100 bot comments per post)
            String botCountKey = "post:" + postId + ":bot_count";
            Long botCount = redisTemplate.opsForValue().increment(botCountKey);

            if (botCount != null && botCount > 100) {
                return "❌ Bot limit reached (100 max)";
            }

            // ⏳ Cooldown (bot → user interaction)
            String cooldownKey = "cooldown:bot_" + authorId + ":post_" + postId;

            Boolean exists = redisTemplate.hasKey(cooldownKey);
            if (Boolean.TRUE.equals(exists)) {
                return "❌ Cooldown active (10 min)";
            }

            redisTemplate.opsForValue().set(cooldownKey, "1", Duration.ofMinutes(10));

            // 📊 Virality update (bot reply = +1)
            viralityService.updateScore(postId, "BOT");

            // 🔔 Notification
            notificationService.handleNotification(postId,
                    "Bot " + authorId + " replied to your post");

        } else {
            // 👤 HUMAN COMMENT

            // 📊 Virality update (+50)
            viralityService.updateScore(postId, "COMMENT");
        }

        // 💾 Save Comment in DB (only after passing guardrails)
        Comment comment = Comment.builder()
                .postId(postId)
                .authorId(authorId)
                .content(content)
                .depthLevel(depthLevel)
                .build();

        commentRepository.save(comment);

        return "✅ Comment added successfully";
    }
}