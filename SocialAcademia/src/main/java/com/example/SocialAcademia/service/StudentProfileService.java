package com.example.SocialAcademia.service;

import com.example.SocialAcademia.dto.StudentProfileRequest;
import com.example.SocialAcademia.dto.StudentProfileResponse;
import com.example.SocialAcademia.model.Enum.Role;
import com.example.SocialAcademia.model.StudentProfile;
import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.repository.StudentProfileRepository;
import com.example.SocialAcademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.NoSuchElementException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentProfileService {
    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
        .orElseThrow(() -> new NoSuchElementException("Aucun utilisateur trouvé pour l'email: " + email));
    }

    public StudentProfileResponse create(StudentProfileRequest request){
        User user = getCurrentUser();
        if (user.getRole() != Role.STUDENT){
            throw new RuntimeException("Not a student account");
        }
        if (studentProfileRepository.findByUser(user).isPresent()){
            throw new RuntimeException("Student profile already exist.");
        }

        StudentProfile profile = StudentProfile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .school(request.getSchool())
                .degree(request.getDegree())
                .year(request.getYear())
                .skills(request.getSkills())
                .cvUrl(request.getCvUrl())
                .user(user)
                .build();
        studentProfileRepository.save(profile);
        return mapToResponce(profile);
    }

    public StudentProfileResponse get(){
        User user = getCurrentUser();
        StudentProfile profile = studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapToResponce(profile);
    }

    public StudentProfileResponse update(StudentProfileRequest request){
        User user = getCurrentUser();
        StudentProfile profile = studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setSchool(request.getSchool());
        profile.setDegree(request.getDegree());
        profile.setYear(request.getYear());
        profile.setSkills(request.getSkills());
        profile.setCvUrl(request.getCvUrl());

        studentProfileRepository.save(profile);
        return mapToResponce(profile);
    }

    private StudentProfileResponse mapToResponce(StudentProfile profile){
        return StudentProfileResponse.builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .school(profile.getSchool())
                .degree(profile.getDegree())
                .year(profile.getYear())
                .skills(profile.getSkills())
                .cvUrl(profile.getCvUrl())
                .email(profile.getUser().getEmail())
                .build();
    }
}
