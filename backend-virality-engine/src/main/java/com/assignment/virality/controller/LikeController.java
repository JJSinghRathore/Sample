package com.assignment.virality.controller;

import com.assignment.virality.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final PostService postService;

    // ✅ Like a Post
    @PostMapping("/{postId}")
    public String likePost(@PathVariable Long postId) {
        postService.likePost(postId);
        return "Post liked successfully!";
    }
}