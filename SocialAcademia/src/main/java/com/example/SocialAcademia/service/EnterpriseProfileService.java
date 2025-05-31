package com.example.SocialAcademia.service;

import com.example.SocialAcademia.dto.EnterpriseProfileRequest;
import com.example.SocialAcademia.dto.EnterpriseProfileResponse;

import com.example.SocialAcademia.model.EnterpriseProfile;
import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.model.Enum.Role;

import com.example.SocialAcademia.repository.EnterpriseProfileRepository;
import com.example.SocialAcademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnterpriseProfileService {
    private final EnterpriseProfileRepository enterpriseProfileRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public EnterpriseProfileResponse create(EnterpriseProfileRequest request){
        User user = getCurrentUser();
        if (user.getRole() != Role.ENTERPRISE)throw new RuntimeException("Not a professor");

        if(enterpriseProfileRepository.findByUser(user).isPresent()){
            throw new RuntimeException("Profile already exists.");
        }

        EnterpriseProfile profile = EnterpriseProfile.builder()
                .companyName(request.getCompanyName())
                .description(request.getDescription())
                .sector(request.getSector())
                .website(request.getWebsite())
                .profilePictureUrl(request.getProfilePictureUrl())
                .user(user)
                .build();
        enterpriseProfileRepository.save(profile);
        return map(profile);
    }

    public EnterpriseProfileResponse get(){
        User user = getCurrentUser();
        EnterpriseProfile profile = enterpriseProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return map(profile);
    }

    public EnterpriseProfileResponse update(EnterpriseProfileRequest request){
        User user = getCurrentUser();
        EnterpriseProfile profile = enterpriseProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setCompanyName(request.getCompanyName());
        profile.setDescription(request.getDescription());
        profile.setSector(request.getSector());
        profile.setWebsite(request.getWebsite());
        profile.setProfilePictureUrl(request.getProfilePictureUrl());

        enterpriseProfileRepository.save(profile);
        return map(profile);
    }

    private EnterpriseProfileResponse map(EnterpriseProfile profile){
        return EnterpriseProfileResponse.builder()
                .companyName(profile.getCompanyName())
                .description(profile.getDescription())
                .sector(profile.getSector())
                .website(profile.getWebsite())
                .profilePictureUrl(profile.getProfilePictureUrl())
                .email(profile.getUser().getEmail())
                .build();
    }
}
