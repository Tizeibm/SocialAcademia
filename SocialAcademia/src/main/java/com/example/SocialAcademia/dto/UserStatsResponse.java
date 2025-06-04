package com.example.SocialAcademia.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserStatsResponse {
    private Long userId;
    private Long postCount;
    private Long likeCount;
    private Long commentCount;
}
