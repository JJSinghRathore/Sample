package com.assignment.virality.repository;

import com.assignment.virality.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);

    List<Comment> findByPostIdAndDepthLevel(Long postId, int depthLevel);
}