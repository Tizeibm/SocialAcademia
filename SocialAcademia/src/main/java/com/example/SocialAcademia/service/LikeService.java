package com.example.SocialAcademia.service;

import com.example.SocialAcademia.model.Enum.NotificationType;
import com.example.SocialAcademia.model.Post;
import com.example.SocialAcademia.model.PostLike;
import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.repository.PostLikeRepository;
import com.example.SocialAcademia.repository.PostRepository;
import com.example.SocialAcademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final NotificationService notificationService;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public String toggleLike(Long postId){
        User user = getCurrentUser();
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post Not found"));
        return postLikeRepository.findByUserAndPost(user, post).map(existing ->{
            postLikeRepository.delete(existing);
            return "Unliked";
        })
                .orElseGet(() -> {
                    PostLike like = PostLike.builder().user(user).post(post).build();
                    notificationService.createNotification(post.getUser(), getCurrentUser().getUsername()+"Liked your post", NotificationType.LIKE);
                    postLikeRepository.save(like);
                    return "Liked";
                });
    }

    public int getLikeCount(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        return postLikeRepository.countByPost(post);
    }

    public List<String> getUserWhoLiked(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        return postLikeRepository.findByPost(post).stream()
                .map(like -> like.getUser().getEmail())
                .collect(Collectors.toList());
    }
}
