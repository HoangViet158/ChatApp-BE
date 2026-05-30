package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentRequest {

    @NotNull(message = "Message id is required")
    private Long messageId;

    @NotBlank(message = "File url is required")
    private String fileUrl;

    private String fileType;

    private Long fileSize;

    private String publicId;
}