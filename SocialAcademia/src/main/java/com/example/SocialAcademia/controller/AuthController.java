package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.AuthRequest;
import com.example.SocialAcademia.dto.AuthResponse;
import com.example.SocialAcademia.dto.RegisterRequest;
import com.example.SocialAcademia.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){this.authService = authService;}

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){
        return authService.authenticate(request);
    }
}
