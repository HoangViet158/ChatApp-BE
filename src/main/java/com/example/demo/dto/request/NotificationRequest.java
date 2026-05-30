package com.example.demo.dto.request;

import com.example.demo.enums.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    private String title;

    private String content;

    @NotNull(message = "Notification type is required")
    private NotificationType type;
}