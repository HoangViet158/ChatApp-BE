package com.example.demo.mapper;

import com.example.demo.dto.request.AttachmentRequest;
import com.example.demo.dto.response.AttachmentResponse;
import com.example.demo.entity.Attachment;
import com.example.demo.entity.Message;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    // =========================
    // Request -> Entity
    // =========================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "message", source = "message")
    Attachment toEntity(
            AttachmentRequest request,
            Message message
    );

    // =========================
    // Entity -> Response
    // =========================
    @Mapping(target = "messageId", source = "message.id")
    AttachmentResponse toResponse(Attachment attachment);
}