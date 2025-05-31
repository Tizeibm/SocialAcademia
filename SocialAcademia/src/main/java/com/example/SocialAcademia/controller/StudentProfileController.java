package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.StudentProfileRequest;
import com.example.SocialAcademia.dto.StudentProfileResponse;
import com.example.SocialAcademia.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles/students")
@RequiredArgsConstructor
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    @PostMapping
    public StudentProfileResponse createProfile(@RequestBody StudentProfileRequest request){
        return studentProfileService.create(request);
    }

    @GetMapping
    public StudentProfileResponse getProfile(){
        return studentProfileService.get();
    }

    @PutMapping
    public StudentProfileResponse updateProfile(@RequestBody StudentProfileRequest request){
        return studentProfileService.update(request);
    }
}
