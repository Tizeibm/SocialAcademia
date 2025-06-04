package com.example.SocialAcademia.service;

import com.example.SocialAcademia.model.Post;
import com.example.SocialAcademia.model.User;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PostSpecification {
    public static Specification<Post> contentContains(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isEmpty()) return cb.conjunction();
            return cb.like(cb.lower(root.get("content")), "%" + keyword.toLowerCase() + "%");
        };
    }

    public static Specification<Post> createdAfter(LocalDateTime date) {
        return (root, query, cb) -> {
            if (date == null) return cb.conjunction();
            return cb.greaterThanOrEqualTo(root.get("createdAt"), date);
        };
    }

    public static Specification<Post> createdBefore(LocalDateTime date) {
        return (root, query, cb) -> {
            if (date == null) return cb.conjunction();
            return cb.lessThanOrEqualTo(root.get("createdAt"), date);
        };
    }

    public static Specification<Post> authorIs(Long userId) {
        return (root, query, cb) -> {
            if (userId == null) return cb.conjunction();
            Join<Post, User> user = root.join("user", JoinType.INNER);
            return cb.equal(user.get("id"), userId);
        };
    }
}
