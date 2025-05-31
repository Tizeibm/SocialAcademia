package com.example.SocialAcademia.service;

import com.example.SocialAcademia.model.Enum.NotificationType;
import com.example.SocialAcademia.model.Notification;
import com.example.SocialAcademia.model.User;
import com.example.SocialAcademia.repository.NotificationRepository;
import com.example.SocialAcademia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    private User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow();
    }

    public List<Notification> getMyNotif(){
        User user = getCurrentUser();
        return notificationRepository.findByRecipientOrderByCreatedAtDesc(user);
    }

    public void markAsRead(Long notificationId){
        Notification notif = notificationRepository.findById(notificationId).orElseThrow(() -> new RuntimeException("Notification not found"));
        notif.setRead(true);
        notificationRepository.save(notif);
    }

    public void deleteNotif(Long notifId){
        Notification notif = notificationRepository.findById(notifId).orElseThrow(() -> new RuntimeException("Notification not found"));
        notificationRepository.deleteById(notifId);
    }

    public void createNotification(User recipient, String message, NotificationType type){
        Notification notification = Notification.builder()
                .recipient(recipient)
                .message(message)
                .read(false)
                .createdAt(LocalDateTime.now())
                .type(type)
                .build();
        notificationRepository.save(notification);
    }
}
