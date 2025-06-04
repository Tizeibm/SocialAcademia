package com.example.SocialAcademia.dto;

import com.example.SocialAcademia.model.Enum.ApplicationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationResponse {
    private Long id;
    private Long studentId;
    private String studentName;
    private String message;
    private String fileUrl;
    private LocalDateTime appliedAt;
    private ApplicationStatus status;
}
