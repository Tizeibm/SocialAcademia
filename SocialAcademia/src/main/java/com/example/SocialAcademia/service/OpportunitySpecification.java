package com.example.SocialAcademia.service;

import com.example.SocialAcademia.model.Enum.OpportunityType;
import com.example.SocialAcademia.model.Opportunity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OpportunitySpecification {
    public static Specification<Opportunity> withFilter(
            OpportunityType type,
            LocalDate startDate,
            LocalDate endDate,
            String authorName
    ){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (type !=  null){
                predicates.add(criteriaBuilder.equal(root.get("type"), type));
            }
            if (startDate !=  null){
                predicates.add(criteriaBuilder.equal(root.get("startDate"), startDate));
            }
            if (endDate !=  null){
                predicates.add(criteriaBuilder.equal(root.get("endDate"), endDate));
            }
            if (authorName !=  null && !authorName.isEmpty()){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("createdBy")
                        .get("fullName")), "%" + authorName.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
