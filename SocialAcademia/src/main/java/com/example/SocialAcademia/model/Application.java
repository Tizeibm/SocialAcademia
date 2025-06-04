package com.example.SocialAcademia.model;

import com.example.SocialAcademia.model.Enum.ApplicationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private StudentProfile student;

    @ManyToOne
    private Opportunity opportunity;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private String message;
    private String fileUrl;
    private LocalDateTime appliedAt;
}
