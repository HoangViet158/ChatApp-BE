package com.example.demo.dto.response;


import com.example.demo.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long id;

    // =========================
    // User
    // =========================
    private Long userId;

    private String username;

    // =========================
    // Notification
    // =========================
    private String title;

    private String content;

    private NotificationType type;

    // =========================
    // Status
    // =========================
    private Boolean isRead;

    // =========================
    // Time
    // =========================
    private LocalDateTime createdAt;
}