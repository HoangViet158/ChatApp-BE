package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.NotificationRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.NotificationResponse;
import com.example.demo.service.NotificationService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ApiResponse<NotificationResponse> createNotification(
            @RequestBody NotificationRequest request
    ) {

        NotificationResponse response =
                notificationService.createNotification(
                        request
                );

        return ApiResponse.<NotificationResponse>builder()
                .code(200)
                .message("Create notification successfully")
                .result(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<NotificationResponse> getNotificationById(
            @PathVariable("id") Long id
    ) {

        NotificationResponse response =
                notificationService.getNotificationById(id);

        return ApiResponse.<NotificationResponse>builder()
                .code(200)
                .message("Get notification successfully")
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<NotificationResponse>>
    getAllNotifications() {

        List<NotificationResponse> responses =
                notificationService.getAllNotifications();

        return ApiResponse.<List<NotificationResponse>>builder()
                .code(200)
                .message("Get all notifications successfully")
                .result(responses)
                .build();
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<NotificationResponse>>
    getNotificationsByUserId(
            @PathVariable("userId") Long userId
    ) {

        List<NotificationResponse> responses =
                notificationService.getNotificationsByUserId(
                        userId
                );

        return ApiResponse.<List<NotificationResponse>>builder()
                .code(200)
                .message("Get notifications successfully")
                .result(responses)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<NotificationResponse> updateNotification(
            @PathVariable("id") Long id,
            @RequestBody NotificationRequest request
    ) {

        NotificationResponse response =
                notificationService.updateNotification(
                        id,
                        request
                );

        return ApiResponse.<NotificationResponse>builder()
                .code(200)
                .message("Update notification successfully")
                .result(response)
                .build();
    }

    @PutMapping("/{id}/read")
    public ApiResponse<NotificationResponse> markAsRead(
            @PathVariable("id") Long id
    ) {

        NotificationResponse response =
                notificationService.markAsRead(id);

        return ApiResponse.<NotificationResponse>builder()
                .code(200)
                .message("Notification marked as read")
                .result(response)
                .build();
    }

    @PutMapping("/user/{userId}/read-all")
    public ApiResponse<Void> markAllAsRead(
            @PathVariable("userId") Long userId
    ) {

        notificationService.markAllAsRead(userId);

        return ApiResponse.<Void>builder()
                .code(200)
                .message("All notifications marked as read")
                .result(null)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteNotification(
            @PathVariable("id") Long id
    ) {

        notificationService.deleteNotification(id);

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Delete notification successfully")
                .result(null)
                .build();
    }
}