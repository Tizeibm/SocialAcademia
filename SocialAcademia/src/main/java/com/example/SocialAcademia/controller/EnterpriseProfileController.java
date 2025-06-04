package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.EnterpriseProfileRequest;
import com.example.SocialAcademia.dto.EnterpriseProfileResponse;

import com.example.SocialAcademia.service.EnterpriseProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/enterprise")
@RequiredArgsConstructor
public class EnterpriseProfileController {
    private final EnterpriseProfileService enterpriseProfileService;
   
    @GetMapping
    public EnterpriseProfileResponse getProfile(){
        return enterpriseProfileService.get();
    }

    @PostMapping
    public EnterpriseProfileResponse createProfile(@RequestBody EnterpriseProfileRequest request){
        return enterpriseProfileService.create(request);
    }

    @PutMapping
    public EnterpriseProfileResponse updateProfile(@RequestBody EnterpriseProfileRequest request){
        return enterpriseProfileService.update(request);
    }
}
