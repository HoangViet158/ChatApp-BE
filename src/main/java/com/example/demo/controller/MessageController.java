package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.MessageRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.MessageResponse;
import com.example.demo.service.MessageService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ApiResponse<MessageResponse> createMessage(
            @RequestBody MessageRequest request
    ) {

        MessageResponse message =
                messageService.createMessage(request);

        return ApiResponse.<MessageResponse>builder()
                .code(200)
                .message("Create message successfully")
                .result(message)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MessageResponse> updateMessage(
            @PathVariable("id") Long id,
            @RequestBody MessageRequest request
    ) {

        MessageResponse message =
                messageService.updateMessage(id, request);

        return ApiResponse.<MessageResponse>builder()
                .code(200)
                .message("Update message successfully")
                .result(message)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MessageResponse> getMessageById(
            @PathVariable("id") Long id
    ) {

        MessageResponse message =
                messageService.getMessageById(id);

        return ApiResponse.<MessageResponse>builder()
                .code(200)
                .message("Get message successfully")
                .result(message)
                .build();
    }

    @GetMapping
    public ApiResponse<List<MessageResponse>> getAllMessages() {

        List<MessageResponse> messages =
                messageService.getAllMessages();

        return ApiResponse.<List<MessageResponse>>builder()
                .code(200)
                .message("Get all messages successfully")
                .result(messages)
                .build();
    }

    @GetMapping("/conversation/{conversationId}")
    public ApiResponse<List<MessageResponse>>
    getMessagesByConversationId(
            @PathVariable("conversationId") Long conversationId
    ) {

        List<MessageResponse> messages =
                messageService.getMessagesByConversationId(
                        conversationId
                );

        return ApiResponse.<List<MessageResponse>>builder()
                .code(200)
                .message("Get messages successfully")
                .result(messages)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMessage(
            @PathVariable("id") Long id,
            @RequestBody Long userId
    ) {

        messageService.deleteMessage(id, userId);

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Delete message successfully")
                .result(null)
                .build();
    }
}