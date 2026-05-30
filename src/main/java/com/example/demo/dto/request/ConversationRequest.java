package com.example.demo.dto.request;

import com.example.demo.enums.ConversationType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationRequest {

    private String name;

    @NotNull(message = "Conversation type is required")
    private ConversationType type;

    private String avatarUrl;

    @NotNull(message = "Created by is required")
    private Long createdBy;
}