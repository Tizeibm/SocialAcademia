package com.example.SocialAcademia.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String authorEmail;
    private int likeCount;
}
