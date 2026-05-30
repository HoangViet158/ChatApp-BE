package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.demo.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(
    name = "notifications",
    indexes = {
        @Index(
            name = "idx_notifications_user",
            columnList = "user_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // User
    // =========================
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(
        name = "user_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_notifications_user")
    )
    private User user;

    // =========================
    // Notification title
    // =========================
    @Column(length = 255)
    private String title;

    // =========================
    // Notification content
    // =========================
    @Column(columnDefinition = "TEXT")
    private String content;

    // =========================
    // Notification type
    // =========================
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    // =========================
    // Read status
    // =========================
    @Builder.Default
    @Column(name = "is_read")
    private Boolean isRead = false;

    // =========================
    // Created time
    // =========================
    @Column(
        name = "created_at",
        updatable = false
    )
    private LocalDateTime createdAt;

    // =========================
    // Auto timestamp
    // =========================
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}