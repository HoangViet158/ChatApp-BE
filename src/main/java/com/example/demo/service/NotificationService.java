package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.NotificationRequest;
import com.example.demo.dto.response.NotificationResponse;
import com.example.demo.entity.Notification;
import com.example.demo.entity.User;
import com.example.demo.enums.ErrorCode;
import com.example.demo.exception.AppException;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.repository.NotificationRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    private final NotificationMapper notificationMapper;

    public NotificationResponse createNotification(
            NotificationRequest request
    ) {

        User user = userRepository.findById(
                        request.getUserId()
                )
                .orElseThrow(() ->
                        new AppException(
                                ErrorCode.USER_NOT_EXISTED
                        ));

        Notification notification =
                notificationMapper.toEntity(
                        request,
                        user
                );

        notificationRepository.save(notification);

        return notificationMapper.toResponse(notification);
    }

    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(
            Long id
    ) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.NOTIFICATION_NOT_FOUND
                                ));

        return notificationMapper.toResponse(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {

        return notificationRepository.findAll()
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByUserId(
            Long userId
    ) {

        return notificationRepository.findByUserIdOrderByCreatedAtDesc(
                        userId
                )
                .stream()
                .map(notificationMapper::toResponse)
                .toList();
    }

    public NotificationResponse updateNotification(
            Long id,
            NotificationRequest request
    ) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.NOTIFICATION_NOT_FOUND
                                ));

        if (request.getUserId() != null) {

            User user = userRepository.findById(
                            request.getUserId()
                    )
                    .orElseThrow(() ->
                            new AppException(
                                    ErrorCode.USER_NOT_EXISTED
                            ));

            notification.setUser(user);
        }

        if (request.getTitle() != null) {
            notification.setTitle(request.getTitle());
        }

        if (request.getContent() != null) {
            notification.setContent(request.getContent());
        }

        if (request.getType() != null) {
            notification.setType(request.getType());
        }

        notificationRepository.save(notification);

        return notificationMapper.toResponse(notification);
    }

    public NotificationResponse markAsRead(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.NOTIFICATION_NOT_FOUND
                                ));

        notification.setIsRead(true);

        notificationRepository.save(notification);

        return notificationMapper.toResponse(notification);
    }

    public void markAllAsRead(Long userId) {

        List<Notification> notifications =
                notificationRepository.findByUserIdAndIsReadFalse(
                        userId
                );

        notifications.forEach(notification ->
                notification.setIsRead(true)
        );

        notificationRepository.saveAll(notifications);
    }

    public void deleteNotification(Long id) {

        Notification notification =
                notificationRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.NOTIFICATION_NOT_FOUND
                                ));

        notificationRepository.delete(notification);
    }
}