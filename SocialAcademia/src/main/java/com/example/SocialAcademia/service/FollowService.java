package com.example.SocialAcademia.service;

import com.example.SocialAcademia.model.Enum.NotificationType;
import com.example.SocialAcademia.model.Follow;
import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.repository.FollowRepository;
import com.example.SocialAcademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public String follow(Long followeeId){
        User follower = getCurrentUser();
        User followee = userRepository.findById(followeeId).orElseThrow(() -> new RuntimeException("User to follow not found"));
        if (follower.equals(followee)){
            throw new RuntimeException("You can't follow yourself");
        }
        if (followRepository.findByFollowerAndFollowee(follower, followee).isPresent()){
            return "Already followed";
        }
        Follow follow = Follow.builder().follower(follower).followee(followee).build();
        followRepository.save(follow);

        notificationService.createNotification(
                followee, follower.getUsername() + "is following you", NotificationType.FOLLOW
        );
        return "Following";
    }

    public String unfollow(Long followeeId){
        User follower = getCurrentUser();
        User followee = userRepository.findById(followeeId).orElseThrow(() -> new RuntimeException("User to be unfollow not found"));
        followRepository.deleteByFollowerAndFollowee(follower, followee);
        return "Unfollowed";
    }

    public List<Long> getFollowingIds(){
        User user = getCurrentUser();
        return followRepository.findByFollower(user).stream()
                .map(f -> f.getFollowee().getId())
                .collect(Collectors.toList());
    }

    public List<Long> getFollowersIds(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return followRepository.findByFollowee(user).stream()
                .map(f -> f.getFollower().getId())
                .collect(Collectors.toList());
    }
}
