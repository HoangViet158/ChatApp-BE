package com.example.demo.dto.response;

import com.example.demo.enums.MemberRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationMemberResponse {

    private Long id;

    private Long conversationId;

    private String conversationName;

    private Long userId;

    private String username;

    private MemberRole role;

    private Long lastReadMessageId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}