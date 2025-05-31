package com.example.SocialAcademia.dto;

import lombok.Data;

@Data
public class ProfessorProfileRequest {
    private String firstName;
    private String lastName;
    private String university;
    private String department;
    private String office;
    private String phone;
}
