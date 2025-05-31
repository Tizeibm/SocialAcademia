package com.example.SocialAcademia.dto;

import lombok.Data;

import java.util.List;

@Data
public class StudentProfileRequest {
    private String firstName;
    private String lastName;
    private String school;
    private String degree;
    private String year;
    private List<String> skills;
    private String cvUrl;
    private String email;
}
