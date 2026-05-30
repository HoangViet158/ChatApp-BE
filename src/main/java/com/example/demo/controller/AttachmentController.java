package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.request.MessageRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.AttachmentResponse;
import com.example.demo.service.AttachmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/upload")
    public ApiResponse<AttachmentResponse> uploadAttachment(
            @ModelAttribute ("request") MessageRequest request,
            @RequestParam ("file") MultipartFile file
    ) {

        AttachmentResponse response =
                attachmentService.uploadAttachment(
                        request,
                        file
                );

        return ApiResponse.<AttachmentResponse>builder()
                .code(200)
                .message("Upload attachment successfully")
                .result(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AttachmentResponse> getAttachmentById(
            @PathVariable Long id
    ) {

        AttachmentResponse response =
                attachmentService.getAttachmentById(id);

        return ApiResponse.<AttachmentResponse>builder()
                .code(200)
                .message("Get attachment successfully")
                .result(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<AttachmentResponse>>
    getAllAttachments() {

        List<AttachmentResponse> responses =
                attachmentService.getAllAttachments();

        return ApiResponse.<List<AttachmentResponse>>builder()
                .code(200)
                .message("Get all attachments successfully")
                .result(responses)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAttachment(
            @PathVariable Long id
    ) {

        attachmentService.deleteAttachment(id);

        return ApiResponse.<Void>builder()
                .code(200)
                .message("Delete attachment successfully")
                .result(null)
                .build();
    }
}