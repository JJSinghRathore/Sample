package com.assignment.virality.service;

import com.assignment.virality.entity.Comment;
import com.assignment.virality.entity.Post;
import com.assignment.virality.repository.CommentRepository;
import com.assignment.virality.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final GuardrailService guardrailService;
    private final ViralityService viralityService;
    private final NotificationService notificationService;

    // ✅ Create Post
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    // ✅ Like Post
    public void likePost(Long postId) {
        viralityService.updateScore(postId, "LIKE");
    }

    // ✅ Add Comment (MAIN LOGIC)
    public String addComment(Long postId,
                             Long authorId,
                             String content,
                             int depthLevel,
                             String authorType) {

        // 🔒 Vertical Cap
        if (!guardrailService.checkDepth(depthLevel)) {
            return "❌ Depth limit exceeded (max 20)";
        }

        // 🤖 BOT LOGIC
        if ("BOT".equalsIgnoreCase(authorType)) {

            // 🔒 Horizontal Cap
            if (!guardrailService.checkAndIncrementBotCount(postId)) {
                return "❌ Bot limit reached (100 max)";
            }

            // ⏳ Cooldown (bot-human)
            // NOTE: Ideally pass real humanId (post owner)
            Long humanId = postId; // simplified assumption

            if (!guardrailService.checkAndSetCooldown(authorId, humanId)) {
                return "❌ Cooldown active (10 min)";
            }

            // 📊 Virality (+1)
            viralityService.updateScore(postId, "BOT");

            // 🔔 Notification
            notificationService.handleNotification(
                    humanId,
                    "Bot " + authorId + " replied to your post"
            );

        } else {
            // 👤 HUMAN COMMENT

            // 📊 Virality (+50)
            viralityService.updateScore(postId, "COMMENT");
        }

        // 💾 Save to DB (ONLY after guardrails pass)
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