package com.example.SocialAcademia.repository;

import com.example.SocialAcademia.model.Post;
import com.example.SocialAcademia.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserInOrderByCreatedAtDesc(List<User> users, Pageable pageable);
}
