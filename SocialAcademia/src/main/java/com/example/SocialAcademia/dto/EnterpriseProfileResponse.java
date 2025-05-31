package com.example.SocialAcademia.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnterpriseProfileResponse {
    private String companyName;
    private String sector;
    private String website;
    private String description;
    private String profilePictureUrl;
    private String email;
}
