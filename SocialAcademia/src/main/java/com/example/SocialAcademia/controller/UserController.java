package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.UserUpdateProfile;
import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.repository.EnterpriseProfileRepository;
import com.example.SocialAcademia.repository.ProfessorProfileRepository;
import com.example.SocialAcademia.repository.StudentProfileRepository;
import com.example.SocialAcademia.service.FileStorageService;
import com.example.SocialAcademia.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final FileStorageService fileStorageService;
    private final StudentProfileRepository studentRepo;
    private final ProfessorProfileRepository professorRepo;
    private final EnterpriseProfileRepository enterpriseRepo;

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> searchUsers(@RequestParam("q") String q) {
        var students = studentRepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(q, q);
        var professors = professorRepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(q, q);
        var enterprises = enterpriseRepo.findByCompanyNameContainingIgnoreCase(q);

        Map<String, Object> result = new HashMap<>();
        result.put("students", students);
        result.put("professors", professors);
        result.put("enterprises", enterprises);
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/{id}")
    public User getUserProfile(@PathVariable Long id) {
        return userService.getUserProfile(id);
    }

    @PutMapping("/{id}")
    public User updateUserProfile(@PathVariable Long id, @RequestBody UserUpdateProfile request) {
        return userService.updateUserProfile(id, request.getBio());
    }

    @PostMapping("/{id}/photo")
    public User updateUserPhoto(@PathVariable Long id,
                                @RequestParam(value = "file", required = false) MultipartFile file
    ) throws IOException {
        String attachmentPath = null;
        if (file != null && !file.isEmpty()) {
            attachmentPath = fileStorageService.storeFile(file);
        }
        return userService.updateUserPhoto(id, attachmentPath);
    }
}
