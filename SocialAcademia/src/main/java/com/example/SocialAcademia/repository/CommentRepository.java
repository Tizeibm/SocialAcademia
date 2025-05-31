package com.example.SocialAcademia.repository;

import com.example.SocialAcademia.model.Comment;
import com.example.SocialAcademia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
