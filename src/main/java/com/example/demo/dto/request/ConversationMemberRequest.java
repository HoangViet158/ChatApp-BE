package com.example.demo.dto.request;

import com.example.demo.enums.MemberRole;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationMemberRequest {

    @NotNull(message = "Conversation id is required")
    private Long conversationId;

    @NotNull(message = "User id is required")
    private Long userId;

    @Builder.Default
    private MemberRole role = MemberRole.MEMBER;

    private Long lastReadMessageId;
}
