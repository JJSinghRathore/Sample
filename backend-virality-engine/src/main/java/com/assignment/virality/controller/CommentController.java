package com.assignment.virality.controller;

import com.assignment.virality.dto.CommentRequest;
import com.assignment.virality.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final PostService postService;

    // ✅ Add Comment (Human / Bot)
    @PostMapping("/{postId}")
    public String addComment(
            @PathVariable Long postId,
            @RequestBody CommentRequest request
    ) {
        return postService.addComment(
                postId,
                request.getAuthorId(),
                request.getContent(),
                request.getDepthLevel(),
                request.getAuthorType()
        );
    }
}