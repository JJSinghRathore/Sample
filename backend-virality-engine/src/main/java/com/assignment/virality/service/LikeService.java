package com.assignment.virality.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final ViralityService viralityService;

    // ✅ Like a post (Human Like = +20)
    public void likePost(Long postId) {

        // Update virality score
        viralityService.updateScore(postId, "LIKE");
    }
}