package com.example.SocialAcademia.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentProfileResponse {
    private String firstName;
    private String lastName;
    private String school;
    private String degree;
    private String year;
    private List<String> skills;
    private String cvUrl;
    private String email;
}
