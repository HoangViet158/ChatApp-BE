package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import com.example.demo.enums.MessageDeliveryStatus;

@Data
@Builder
public class MessageStatusResponse {

    private Long id;

    private Long messageId;

    private Long userId;

    private String username;

    private MessageDeliveryStatus status;

    private LocalDateTime seenAt;
}