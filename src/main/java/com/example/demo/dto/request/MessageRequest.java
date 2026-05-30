package com.example.demo.dto.request;

import com.example.demo.enums.MessageType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest {

    @NotNull(message = "Conversation id is required")
    private Long conversationId;

    @NotNull(message = "Sender id is required")
    private Long senderId;

    private String content;

    @Builder.Default
    private MessageType messageType = MessageType.TEXT;

    private Long replyToMessageId;
}