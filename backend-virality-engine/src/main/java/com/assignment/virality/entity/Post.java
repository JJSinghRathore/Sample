package com.assignment.virality.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Can be User or Bot
    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
