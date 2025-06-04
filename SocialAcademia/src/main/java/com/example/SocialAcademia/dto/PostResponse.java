package com.example.SocialAcademia.dto;

import com.example.SocialAcademia.model.Post;
import com.example.SocialAcademia.model.User;
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
    private User user;
}
