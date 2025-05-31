package com.example.SocialAcademia.repository;

import com.example.SocialAcademia.model.StudentProfile;
import com.example.SocialAcademia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
    Optional<StudentProfile> findByUser(User user);
}
