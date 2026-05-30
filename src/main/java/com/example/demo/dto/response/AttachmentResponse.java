package com.example.demo.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentResponse {

    private Long id;

    private Long messageId;

    private String fileUrl;

    private String fileType;

    private Long fileSize;

    private String publicId;

    private LocalDateTime createdAt;
}