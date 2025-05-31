package com.example.SocialAcademia.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfessorProfileResponse {
    private String firstName;
    private String lastName;
    private String university;
    private String department;
    private String office;
    private String phone;
    private String email;
}
