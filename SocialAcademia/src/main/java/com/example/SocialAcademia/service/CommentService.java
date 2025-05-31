package com.example.SocialAcademia.service;

import com.example.SocialAcademia.dto.CommentRequest;
import com.example.SocialAcademia.dto.CommentResponse;
import com.example.SocialAcademia.model.Comment;
import com.example.SocialAcademia.model.Enum.NotificationType;
import com.example.SocialAcademia.model.Post;
import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.repository.CommentRepository;
import com.example.SocialAcademia.repository.PostRepository;
import com.example.SocialAcademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    private User getCurrentUser(){
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public CommentResponse create(Long postId,CommentRequest request){
        User user = getCurrentUser();
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = Comment.builder()
                .Content(request.getContent())
                .CreatedAt(LocalDateTime.now())
                .post(post)
                .user(getCurrentUser())
                .build();
        commentRepository.save(comment);
        if (!post.getUser().equals(user)){
            notificationService.createNotification(
                    post.getUser(), user.getUsername()+ " commented your post", NotificationType.COMMENT
            );
        }
        return map(comment);
    }

    public List<CommentResponse> getByPost(Long postId){
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));
        return commentRepository.findByPost(post).stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public CommentResponse Update(Long id, CommentRequest request){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getEmail().equals(getCurrentUser().getEmail())){
            throw new RuntimeException("Unauthorized");
        }

        comment.setContent(request.getContent());
        commentRepository.save(comment);
        return map(comment);
    }

    public void delete(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));
        if (!comment.getUser().getEmail().equals(getCurrentUser().getEmail())){
            throw new RuntimeException("Unauthorized");
        }
        commentRepository.deleteById(id);
    }

    private CommentResponse map(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createAt(comment.getCreatedAt())
                .authorEmail(comment.getUser().getEmail())
                .build();
    }
}
