package com.example.SocialAcademia.dto;

import com.example.SocialAcademia.model.Enum.OpportunityType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OpportunityRequest {
    private String title;
    private String description;
    private OpportunityType type;
    private LocalDate date;
}
