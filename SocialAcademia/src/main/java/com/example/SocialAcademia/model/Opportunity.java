package com.example.SocialAcademia.model;

import com.example.SocialAcademia.model.Enum.OpportunityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.border.TitledBorder;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opportunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private OpportunityType type;

    private LocalDate date;
    private String fileUrl;

    @ManyToOne
    private User createdBy;

    private LocalDateTime CreatedAt;
}
