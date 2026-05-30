package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(
    name = "refresh_tokens",
    indexes = {
        @Index(
            name = "idx_refresh_tokens_user",
            columnList = "user_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

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
        foreignKey = @ForeignKey(name = "fk_refresh_tokens_user")
    )
    private User user;

    // =========================
    // Refresh token
    // =========================
    @Column(nullable = false, columnDefinition = "TEXT")
    private String token;

    // =========================
    // Expired time
    // =========================
    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

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