package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/follow/{followeeId}")
    public String follow(@PathVariable Long followeeId){
        return followService.follow(followeeId);
    }

    @DeleteMapping("/unfollow/{followeeId}")
    public String unfollow(@PathVariable Long followeeId){
        return followService.unfollow(followeeId);
    }

    @GetMapping("/following")
    public List<Long> getFollowing(){
        return followService.getFollowingIds();
    }

    @GetMapping("/followers/{userId}")
    public List<Long> getFollowers(@PathVariable Long userId){
        return followService.getFollowersIds(userId);
    }
}
