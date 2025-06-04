package com.example.SocialAcademia.repository;

import com.example.SocialAcademia.model.EnterpriseProfile;
import com.example.SocialAcademia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnterpriseProfileRepository extends JpaRepository<EnterpriseProfile, Long> {
    Optional<EnterpriseProfile> findByUser(User user);
     List<EnterpriseProfile> findByCompanyNameContainingIgnoreCase(String companyName);
}
