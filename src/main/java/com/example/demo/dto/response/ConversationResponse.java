package com.example.demo.dto.response;

import com.example.demo.enums.ConversationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationResponse {

    private Long id;

    private String name;

    private ConversationType type;

    private String avatarUrl;

    private Long createdBy;

    private String createdByUsername;

    private LocalDateTime createdAt;
}