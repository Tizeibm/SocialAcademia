package com.example.SocialAcademia.dto;

import com.example.SocialAcademia.model.Enum.OpportunityType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class OpportunityResponse {
    private Long id;
    private String title;
    private String description;
    private OpportunityType type;
    private LocalDate date;
    private String createdByName;
    private LocalDateTime createdAt;
}
