package com.example.SocialAcademia.service;

import com.example.SocialAcademia.dto.PostRequest;
import com.example.SocialAcademia.dto.PostResponse;
import com.example.SocialAcademia.model.Post;
import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.repository.FollowRepository;
import com.example.SocialAcademia.repository.PostLikeRepository;
import com.example.SocialAcademia.repository.PostRepository;
import com.example.SocialAcademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final FollowService followService;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public List<PostResponse> getFeedPost(int page, int size){
        User user = followService.getCurrentUser();
        List<Long> followeeIds = followService.getFollowingIds();

        if (followeeIds.isEmpty())
            return List.of();

        List<User> followees = userRepository.findAllById(followeeIds);
        List<Post> posts = postRepository.findByUserInOrderByCreatedAtDesc(followees, PageRequest.of(page, size));
        return posts.stream().map(this::map).collect(Collectors.toList());
    }
     public PostResponse create(PostRequest request){
        Post post = Post.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .user(getCurrentUser())
                .build();
        postRepository.save(post);
        return map(post);
    }

    public List<PostResponse> getAll(){
        return postRepository.findAll().stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public PostResponse getById(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        return map(post);
    }

    public PostResponse update(Long id, PostRequest request){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getUser().getEmail().equals(getCurrentUser().getEmail())){
            throw new RuntimeException("Unauthorized");
        }

        post.setContent(request.getContent());
        postRepository.save(post);
        return map(post);
    }

    public void delete(Long id){
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getEmail().equals(getCurrentUser().getEmail())){
            throw new RuntimeException("Unauthorized");
        }
        postRepository.delete(post);
    }

    private PostResponse map(Post post){
        int likeCount = postLikeRepository.countByPost(post);
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .authorEmail(post.getUser().getEmail())
                .likeCount(likeCount)
                .build();
    }
}
