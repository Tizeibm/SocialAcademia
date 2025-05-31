package com.example.SocialAcademia.controller;

import com.example.SocialAcademia.model.Notification;
import com.example.SocialAcademia.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<Notification> getNotif(){
        return notificationService.getMyNotif();
    }

    @PostMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id){
        notificationService.markAsRead(id);
    }

    @DeleteMapping("/{id}")
    public void deleteNotif(@PathVariable Long id){
        notificationService.deleteNotif(id);
    }
}
