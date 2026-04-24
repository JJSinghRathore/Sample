package com.assignment.virality.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentRequest {

    private Long authorId;

    private String content;

    private int depthLevel;

    // USER or BOT
    private String authorType;
}