package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.ProfessorProfileRequest;
import com.example.SocialAcademia.dto.ProfessorProfileResponse;
import com.example.SocialAcademia.service.ProfessorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/profiles/professors")
@RequiredArgsConstructor
public class ProfessorProfileController {
    
    private final ProfessorProfileService professorProfileService;

   

    @GetMapping
    public ProfessorProfileResponse getProfile(){
        return professorProfileService.get();
    }

    @PostMapping
    public ProfessorProfileResponse createProfile(@RequestBody ProfessorProfileRequest request){
        return professorProfileService.create(request);
    }

    @PutMapping
    public ProfessorProfileResponse updateProfile(@RequestBody ProfessorProfileRequest request){
        return professorProfileService.update(request);
    }

}
