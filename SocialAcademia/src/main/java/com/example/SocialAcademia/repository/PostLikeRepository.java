package com.example.SocialAcademia.repository;

import com.example.SocialAcademia.model.Post;
import com.example.SocialAcademia.model.PostLike;
import com.example.SocialAcademia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    List<PostLike> findByPost(Post post);
    int countByPost(Post post);
}
