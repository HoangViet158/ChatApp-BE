package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.ConversationMemberRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.ConversationMemberResponse;
import com.example.demo.entity.ConversationMember;
import com.example.demo.service.ConversationMemberService;
import org.springframework.web.bind.annotation.RequestBody ;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/conversation-member")
@RequiredArgsConstructor
public class ConversationMemberController {
    private final ConversationMemberService conversationMemberService;
    @PostMapping
    public ApiResponse<Void> addMembers(
        @RequestParam("conversationId") Long conversationId,
        @RequestParam("creatorId") Long creatorId,
        @RequestBody List<Long> userIds) {
        conversationMemberService.addMemberToConversation(
                conversationId,
                creatorId,
                userIds
        );

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Add members successfully")
                .result(null)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ConversationMemberResponse> getConversationMemberById(
            @PathVariable("id") Long id
    ) {

        return ApiResponse.<ConversationMemberResponse>builder()
                .code(200)
                .message("Get conversation member successfully")
                .result(
                        conversationMemberService
                                .getConversationMemberById(id)
                )
                .build();
    }

    @GetMapping
    public ApiResponse<List<ConversationMemberResponse>>
    getAllConversationMembers() {

        return ApiResponse.<List<ConversationMemberResponse>>builder()
                .code(200)
                .message("Get all conversation members successfully")
                .result(
                        conversationMemberService
                                .getAllConversationMembers()
                )
                .build();
    }

    @GetMapping("/conversation/{conversationId}")
    public ApiResponse<List<ConversationMemberResponse>>
    getMembersByConversationId(
            @PathVariable("conversationId") Long conversationId
    ) {

        return ApiResponse.<List<ConversationMemberResponse>>builder()
                .code(200)
                .message("Get members by conversation successfully")
                .result(
                        conversationMemberService
                                .getMembersByConversationId(conversationId)
                )
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateConversationMember(
            @PathVariable("id") Long id,
            @RequestBody ConversationMemberRequest request
    ) {

        conversationMemberService.updateConversationMember(
                id,
                request
        );

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Update conversation member successfully")
                .result(null)
                .build();
    }

    @PutMapping("/delete/{id}")
    public ApiResponse<Void> deleteConversationMember(
            @PathVariable("id") Long id
    ) {

        conversationMemberService.deleteConversationMember(id);

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Delete conversation member successfully")
                .result(null)
                .build();
    }
    @PutMapping("last-seen/{id}")
    public ApiResponse<Void> putMethodName(@PathVariable("id") Long id, @RequestBody Long messageId) {
        conversationMemberService.updateLastSeen(id, messageId);
        return ApiResponse.<Void>builder()
        .code(200)
        .result(null)
        .message("update last seen success")
        .build();
    }
}
