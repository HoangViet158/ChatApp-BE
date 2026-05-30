package com.example.demo.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenResponse {

    private Long id;

    // =========================
    // User
    // =========================
    private Long userId;

    private String username;

    // =========================
    // Token
    // =========================
    private String token;

    // =========================
    // Time
    // =========================
    private LocalDateTime expiredAt;

    private LocalDateTime createdAt;
}