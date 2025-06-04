package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.ApplicationRequest;
import com.example.SocialAcademia.dto.ApplicationResponse;
import com.example.SocialAcademia.model.Enum.ApplicationStatus;
import com.example.SocialAcademia.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/application")
@RequiredArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @PostMapping("/apply/{studentId}/{opportunityId}")
    public ApplicationResponse apply(
            @PathVariable Long studentId,
            @PathVariable Long opportunityId,
            @RequestPart("data")ApplicationRequest request,
            @RequestPart(name = "file", required = false)MultipartFile file
            ){
        return applicationService.apply(studentId, opportunityId, request, file);
    }

    @GetMapping("/opportunity/{opportunityId}")
    public List<ApplicationResponse> getByOpportunity(@PathVariable Long opportunityId){
        return applicationService.getApplications(opportunityId);
    }

    @PutMapping("/{applicationId}/status")
    public ApplicationResponse update(
            @PathVariable Long applicationId,
            @RequestParam ApplicationStatus status
            ){
        return applicationService.updateApplicatnStatus(applicationId, status);
    }

    @GetMapping("/student/{id}")
    public List<ApplicationResponse> getByStudent(@PathVariable Long id){
        return applicationService.getAppsByStudent(id);
    }

    @DeleteMapping("/{applicationId}/student/{studentId}")
    public ResponseEntity<?> deleteApp(@PathVariable Long applicationId, @PathVariable Long studentId){
        applicationService.deleteApplication(applicationId, studentId);
        return ResponseEntity.ok("Application removed successfully");
    }

}
