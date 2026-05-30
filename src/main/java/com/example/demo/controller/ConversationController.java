package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import com.example.demo.dto.request.ConversationRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ConversationResponse;
import com.example.demo.service.ConversationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/conversations")
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;

    @PostMapping()
    public ApiResponse<ConversationResponse> createConversation(@Valid @RequestBody ConversationRequest request) {
        ConversationResponse conversationResponse = conversationService.createConversation(request);
        return ApiResponse.<ConversationResponse>builder()
                .code(200)
                .message("Conversation created successfully")
                .result(conversationResponse)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ConversationResponse> updateConversation(@PathVariable("id") Long id, @RequestBody ConversationRequest request) {
        ConversationResponse conversationResponse = conversationService.updateConversation(id, request.getName(), request.getAvatarUrl());
        return ApiResponse.<ConversationResponse>builder()
                .code(200)
                .message("Conversation updated successfully")
                .result(conversationResponse)
                .build();
            }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteConversation(@PathVariable("id") Long id, @RequestBody Long userId) {
        conversationService.deleteConversation(id, userId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Conversation deleted successfully")
                .build();
        }
    @GetMapping("/search")
    public ApiResponse<List<ConversationResponse>> searchConversations(@RequestParam("name") String name) {
        List<ConversationResponse> conversationResponses = conversationService.searchConversations(name);
        return ApiResponse.<List<ConversationResponse>>builder()
                .code(200)
                .message("Conversations retrieved successfully")
                .result(conversationResponses)
                .build();
    }
    @GetMapping("/{id}")
    public ApiResponse<ConversationResponse> getConversationById(@PathVariable("id") Long id) {
        ConversationResponse conversationResponse = conversationService.getConversationById(id);
        return ApiResponse.<ConversationResponse>builder()
                .code(200)
                .message("Conversation retrieved successfully")
                .result(conversationResponse)
                .build();
            }
    @GetMapping()
    public ApiResponse<List<ConversationResponse>> getAllConversations() {
        List<ConversationResponse> conversationResponses = conversationService.getAllConversations();
        return ApiResponse.<List<ConversationResponse>>builder()    
                .code(200)
                .message("Conversations retrieved successfully")
                .result(conversationResponses)
                .build();
            }
    
    @GetMapping("/user/{id}")
    public ApiResponse<List<ConversationResponse>> getConversationsByUser(@PathVariable("id") Long id){
        List<ConversationResponse> conversationResponses = conversationService.getByUserId(id);
        return ApiResponse.<List<ConversationResponse>>builder()    
                .code(200)
                .message("Conversations retrieved successfully")
                .result(conversationResponses)
                .build();
    }
}