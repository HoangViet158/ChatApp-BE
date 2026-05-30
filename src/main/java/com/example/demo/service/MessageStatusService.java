package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.request.MessageStatusRequest;
import com.example.demo.dto.response.MessageStatusResponse;
import com.example.demo.entity.Message;
import com.example.demo.entity.MessageStatus;
import com.example.demo.entity.User;
import com.example.demo.enums.ErrorCode;
import com.example.demo.exception.AppException;
import com.example.demo.mapper.MessageStatusMapper;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.MessageStatusRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MessageStatusService {

    private final MessageStatusRepository messageStatusRepository;

    private final MessageRepository messageRepository;

    private final MessageStatusMapper messageStatusMapper;

    private final UserRepository userRepository;

    public MessageStatusResponse createMessageStatus(
            MessageStatusRequest request
    ) {

        Message message = messageRepository.findById(
                        request.getMessageId()
                )
                .orElseThrow(() ->
                        new AppException(
                                ErrorCode.MESSAGE_NOT_FOUND
                        ));

        User user = userRepository.findById(
                        request.getUserId()
                )
                .orElseThrow(() ->
                        new AppException(
                                ErrorCode.USER_NOT_EXISTED
                        ));

        MessageStatus messageStatus =
                messageStatusMapper.toEntity(
                        request,
                        message,
                        user
                );

        messageStatusRepository.save(messageStatus);

        return messageStatusMapper.toResponse(messageStatus);
    }

    @Transactional(readOnly = true)
    public MessageStatusResponse getMessageStatusById(Long id) {

        MessageStatus messageStatus =
                messageStatusRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.MESSAGE_STATUS_NOT_FOUND
                                ));

        return messageStatusMapper.toResponse(messageStatus);
    }

    @Transactional(readOnly = true)
    public List<MessageStatusResponse> getAllMessageStatuses() {

        return messageStatusRepository.findAll()
                .stream()
                .map(messageStatusMapper::toResponse)
                .toList();
    }

    public MessageStatusResponse updateMessageStatus(
            Long id,
            MessageStatusRequest request
    ) {

        MessageStatus messageStatus =
                messageStatusRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.MESSAGE_STATUS_NOT_FOUND
                                ));

        if (request.getMessageId() != null) {

            Message message = messageRepository.findById(
                            request.getMessageId()
                    )
                    .orElseThrow(() ->
                            new AppException(
                                    ErrorCode.MESSAGE_NOT_FOUND
                            ));

            messageStatus.setMessage(message);
        }

        if (request.getUserId() != null) {

            User user = userRepository.findById(
                            request.getUserId()
                    )
                    .orElseThrow(() ->
                            new AppException(
                                    ErrorCode.USER_NOT_EXISTED
                            ));

            messageStatus.setUser(user);
        }

        messageStatus.setStatus(request.getStatus());
        messageStatusRepository.save(messageStatus);

        return messageStatusMapper.toResponse(messageStatus);
    }

    public void deleteMessageStatus(Long id) {

        MessageStatus messageStatus =
                messageStatusRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.MESSAGE_STATUS_NOT_FOUND
                                ));
        messageStatusRepository.delete(messageStatus);
    }

    public MessageStatusResponse markAsSeen(Long id) {

        MessageStatus messageStatus =
                messageStatusRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.MESSAGE_STATUS_NOT_FOUND
                                ));

        messageStatus.setSeenAt(LocalDateTime.now());

        messageStatusRepository.save(messageStatus);

        return messageStatusMapper.toResponse(messageStatus);
    }
}