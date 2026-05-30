package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.enums.MessageType;

@Data
@Builder
public class MessageResponse {

    private Long id;

    private Long conversationId;

    private String conversationName;

    private Long senderId;

    private String senderName;

    private String content;

    private MessageType messageType;

    private Boolean isEdited;

    private Boolean isDeleted;

    private Long replyToMessageId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<AttachmentResponse> attachments;
}