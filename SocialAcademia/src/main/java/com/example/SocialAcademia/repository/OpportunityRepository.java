package com.example.SocialAcademia.repository;

import com.example.SocialAcademia.model.Enum.OpportunityType;
import com.example.SocialAcademia.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OpportunityRepository extends JpaRepository<Opportunity, Long>, JpaSpecificationExecutor<Opportunity> {
    List<Opportunity> findByTitleContainingIgnoreCase(String keyword);

    @Query("select o from Opportunity o where " +
            "(:type IS null or o.type = :type) and " +
            "(:startDate is null  or o.date <= :startDate) and" +
            "(:endDate is null or o.date <= :endDate) and " +
            "(:authorId is null or o.createdBy.id = :authorId)")
    List<Opportunity> filterOpportunity(
            @Param("type") OpportunityType type,
            @Param("StartDate") LocalDate startDate,
            @Param("endData") LocalDate endDate,
            @Param("authorId") Long authorId
            );
}
