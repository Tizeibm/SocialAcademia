package com.example.SocialAcademia.service;

import com.example.SocialAcademia.dto.UserStatsResponse;
import com.example.SocialAcademia.repository.CommentRepository;
import com.example.SocialAcademia.repository.PostLikeRepository;
import com.example.SocialAcademia.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatsService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public UserStatsResponse getUserStats(Long userId){
        Long postCount = postRepository.countPostByUserId(userId);
        Long likeCount = postLikeRepository.countPostLikesByUserId(userId);
        Long commentCount = commentRepository.countCommentsByUserId(userId);

        return UserStatsResponse.builder()
                .userId(userId)
                .postCount(postCount)
                .commentCount(commentCount)
                .likeCount(likeCount)
                .build();
    }
}
