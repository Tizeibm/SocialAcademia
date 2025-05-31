package com.example.SocialAcademia.dto;

import lombok.Data;

@Data
public class EnterpriseProfileRequest {
    private String companyName;
    private String sector;
    private String website;
    private String description;
    private String profilePictureUrl;
    private String email;
}
