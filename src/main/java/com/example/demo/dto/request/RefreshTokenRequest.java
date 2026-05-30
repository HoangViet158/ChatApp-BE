package com.example.demo.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenRequest {

    // @NotNull(message = "User id is required")
    // private Long userId;

    @NotBlank(message = "Token is required")
    private String token;

    // @NotNull(message = "Expired time is required")
    // private LocalDateTime expiredAt;
}