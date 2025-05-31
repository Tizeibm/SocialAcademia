package com.example.SocialAcademia.dto;
import lombok.*;

@Data
@AllArgsConstructor
public class AuthRequest {
    private String email;
    private String password;
}
