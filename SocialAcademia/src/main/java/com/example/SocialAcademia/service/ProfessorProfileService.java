package com.example.SocialAcademia.service;

import com.example.SocialAcademia.dto.ProfessorProfileRequest;
import com.example.SocialAcademia.dto.ProfessorProfileResponse;

import com.example.SocialAcademia.model.Enum.Role;
import com.example.SocialAcademia.model.ProfessorProfile;

import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.repository.ProfessorProfileRepository;
import com.example.SocialAcademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorProfileService {
    private final ProfessorProfileRepository professorProfileRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public ProfessorProfileResponse create(ProfessorProfileRequest request){
        User user = getCurrentUser();
        if (user.getRole() != Role.PROFESSOR)throw new RuntimeException("Not a professor");

        if(professorProfileRepository.findByUser(user).isPresent()){
            throw new RuntimeException("Profile already exists.");
        }

        ProfessorProfile profile = ProfessorProfile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .university(request.getUniversity())
                .department(request.getDepartment())
                .office(request.getOffice())
                .phone(request.getPhone())
                .user(user)
                .build();
        professorProfileRepository.save(profile);
        return map(profile);
    }

    public ProfessorProfileResponse get(){
        User user = getCurrentUser();
        ProfessorProfile profile = professorProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return map(profile);
    }

    public ProfessorProfileResponse update(ProfessorProfileRequest request){
        User user = getCurrentUser();
        ProfessorProfile profile = professorProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setUniversity(request.getUniversity());
        profile.setDepartment(request.getDepartment());
        profile.setOffice(request.getOffice());
        profile.setPhone(request.getPhone());

        professorProfileRepository.save(profile);
        return map(profile);
    }

    private ProfessorProfileResponse map(ProfessorProfile profile){
        return ProfessorProfileResponse.builder()
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .university(profile.getUniversity())
                .department(profile.getDepartment())
                .office(profile.getOffice())
                .phone(profile.getPhone())
                .email(profile.getUser().getEmail())
                .build();
    }
}
