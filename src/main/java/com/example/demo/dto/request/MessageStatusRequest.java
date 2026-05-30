package com.example.demo.dto.request;

import com.example.demo.enums.MessageDeliveryStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageStatusRequest {

    @NotNull(message = "Message id is required")
    private Long messageId;

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Status is required")
    private MessageDeliveryStatus status;
}