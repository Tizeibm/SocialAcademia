package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.dto.UserStatsResponse;
import com.example.SocialAcademia.service.UserStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userstats")
@RequiredArgsConstructor
public class UserStatsController {
    private final UserStatsService userStatsService;

    @GetMapping("/{id}")
    public UserStatsResponse userStat(@PathVariable Long id){
        return userStatsService.getUserStats(id);
    }
}
