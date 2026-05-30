package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.MessageStatusRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.MessageStatusResponse;
import com.example.demo.service.MessageStatusService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/message-status")
@RequiredArgsConstructor
public class MessageStatusController {

    private final MessageStatusService messageStatusService;

    @PostMapping
    public ApiResponse<MessageStatusResponse> createMessageStatus(
            @RequestBody MessageStatusRequest request
    ) {

        MessageStatusResponse response =
                messageStatusService.createMessageStatus(
                        request
                );

        return ApiResponse.<MessageStatusResponse>builder()
                .code(200)
                .message("Create message status successfully")
                .result(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<MessageStatusResponse> getMessageStatusById(
            @PathVariable("id") Long id
    ) {

        MessageStatusResponse response =
                messageStatusService.getMessageStatusById(id);

        return ApiResponse.<MessageStatusResponse>builder()
                .code(200)
                .message("Get message status successfully")
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<MessageStatusResponse>>
    getAllMessageStatuses() {

        List<MessageStatusResponse> responses =
                messageStatusService.getAllMessageStatuses();

        return ApiResponse.<List<MessageStatusResponse>>builder()
                .code(200)
                .message("Get all message statuses successfully")
                .result(responses)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<MessageStatusResponse> updateMessageStatus(
            @PathVariable("id") Long id,
            @RequestBody MessageStatusRequest request
    ) {

        MessageStatusResponse response =
                messageStatusService.updateMessageStatus(
                        id,
                        request
                );

        return ApiResponse.<MessageStatusResponse>builder()
                .code(200)
                .message("Update message status successfully")
                .result(response)
                .build();
    }

    @PutMapping("/{id}/seen")
    public ApiResponse<MessageStatusResponse> markAsSeen(
            @PathVariable("id") Long id
    ) {

        MessageStatusResponse response =
                messageStatusService.markAsSeen(id);

        return ApiResponse.<MessageStatusResponse>builder()
                .code(200)
                .message("Message marked as seen")
                .result(response)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteMessageStatus(
            @PathVariable("id") Long id
    ) {

        messageStatusService.deleteMessageStatus(id);

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Delete message status successfully")
                .result(null)
                .build();
    }
}