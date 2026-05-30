package com.example.demo.mapper;
import com.example.demo.dto.request.NotificationRequest;
import com.example.demo.dto.response.NotificationResponse;
import com.example.demo.entity.Notification;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    // =========================
    // Request -> Entity
    // =========================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "isRead", ignore = true)
    Notification toEntity(
            NotificationRequest request,
            User user
    );

    // =========================
    // Entity -> Response
    // =========================
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    NotificationResponse toResponse(
            Notification notification
    );
}