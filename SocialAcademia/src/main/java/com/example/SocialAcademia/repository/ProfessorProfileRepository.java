package com.example.SocialAcademia.repository;

import com.example.SocialAcademia.model.ProfessorProfile;
import com.example.SocialAcademia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorProfileRepository extends JpaRepository<ProfessorProfile, Long> {
    Optional<ProfessorProfile> findByUser(User user);
}
