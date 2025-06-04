package com.example.SocialAcademia.repository;

import com.example.SocialAcademia.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByStudentIdAndOpportunityId(Long studentId, Long opportunityId);
    List<Application> findByOpportunityId(Long opportunityId);
}
