package com.assignment.virality.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {

    private Long authorId;

    private String content;
}