package com.example.SocialAcademia.service;

import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserProfile(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUserProfile(Long userId, String bio) {
        User user = getUserProfile(userId);
        user.setBio(bio);
        return userRepository.save(user);
    }

    public User updateUserPhoto(Long userId, String photoUrl) {
        User user = getUserProfile(userId);
        user.setPhotoUrl(photoUrl);
        return userRepository.save(user);
    }
}
