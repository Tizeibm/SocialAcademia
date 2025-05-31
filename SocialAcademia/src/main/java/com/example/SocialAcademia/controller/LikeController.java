package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{postId}/like")
    public String toggleLike(@PathVariable Long postId){
        return likeService.toggleLike(postId);
    }

    @GetMapping("/{postId}/likes/count")
    public int getLikeCount(@PathVariable Long postId){
        return likeService.getLikeCount(postId);
    }

    @GetMapping("/{postId}/likes/users")
    public List<String> getUsersWhoLiked(@PathVariable Long postId){
        return likeService.getUserWhoLiked(postId);
    }
}
