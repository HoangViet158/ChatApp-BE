package com.example.demo.dto.response;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    // =========================
    // Basic info
    // =========================
    private String username;

    private String email;

    private String fullName;

    private String avatarUrl;

    private String bio;

    // =========================
    // Status
    // =========================
    private Boolean isOnline;

    // =========================
    // Roles
    // =========================
    private Set<RoleResponse> roles;

    // =========================
    // Time
    // =========================
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}