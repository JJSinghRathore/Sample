package com.assignment.virality.controller;

import com.assignment.virality.dto.CommentRequest;
import com.assignment.virality.entity.Post;
import com.assignment.virality.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // ✅ Create Post
    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postService.createPost(post);
    }

    // ✅ Add Comment (DTO based)
    @PostMapping("/{postId}/comments")
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

    // ✅ Like Post
    @PostMapping("/{postId}/like")
    public String likePost(@PathVariable Long postId) {
        postService.likePost(postId);
        return "Post liked successfully!";
    }
}