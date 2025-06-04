package com.example.SocialAcademia.repository;

import com.example.SocialAcademia.model.Comment;
import com.example.SocialAcademia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    @Query("select count(c) from Comment c where c.user.id = :userId")
    Long countCommentsByUserId(@Param("userId") Long userId);
}
