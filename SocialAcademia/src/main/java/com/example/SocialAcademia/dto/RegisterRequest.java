package com.example.SocialAcademia.dto;

import com.example.SocialAcademia.model.Enum.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private Role role;
}
