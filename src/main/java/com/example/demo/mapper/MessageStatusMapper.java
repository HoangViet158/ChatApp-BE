package com.example.demo.mapper;
import com.example.demo.dto.request.MessageStatusRequest;
import com.example.demo.dto.response.MessageStatusResponse;
import com.example.demo.entity.Message;
import com.example.demo.entity.MessageStatus;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageStatusMapper {

    // =========================
    // Request -> Entity
    // =========================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "message", source = "message")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "seenAt", ignore = true)
    MessageStatus toEntity(
            MessageStatusRequest request,
            Message message,
            User user
    );

    // =========================
    // Entity -> Response
    // =========================
    @Mapping(target = "messageId", source = "message.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    MessageStatusResponse toResponse(
            MessageStatus messageStatus
    );
}