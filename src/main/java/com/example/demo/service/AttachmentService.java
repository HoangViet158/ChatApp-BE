package com.example.demo.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.demo.dto.request.MessageRequest;
import com.example.demo.dto.response.AttachmentResponse;
import com.example.demo.entity.Attachment;
import com.example.demo.entity.Message;
import com.example.demo.enums.ErrorCode;
import com.example.demo.exception.AppException;
import com.example.demo.mapper.AttachmentMapper;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.MessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final MessageRepository messageRepository;

    private final MessageService messageService;

    private final AttachmentMapper attachmentMapper;

    private final Cloudinary cloudinary;


    public AttachmentResponse uploadAttachment(
            MessageRequest request,
            MultipartFile file
    ) {

        try {

            Message message = messageService.createMessageEntity(request);

            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder",
                            "chat-app"
                    )
            );

            String fileUrl =
                    uploadResult.get("secure_url").toString();

            String publicId =
                    uploadResult.get("public_id").toString();

            Attachment attachment = Attachment.builder()
                    .message(message)
                    .fileUrl(fileUrl)
                    .publicId(publicId)
                    .fileType(file.getContentType())
                    .fileSize(file.getSize())
                    .build();

            attachmentRepository.save(attachment);

            return attachmentMapper.toResponse(attachment);

        } catch (IOException e) {

            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
        }
    }

    @Transactional(readOnly = true)
    public AttachmentResponse getAttachmentById(Long id) {

        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(
                                ErrorCode.ATTACHMENT_NOT_FOUND
                        ));

        return attachmentMapper.toResponse(attachment);
    }

    @Transactional(readOnly = true)
    public List<AttachmentResponse> getAllAttachments() {

        return attachmentRepository.findAll()
                .stream()
                .map(attachmentMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AttachmentResponse> getAttachmentsByMessageId(
            Long messageId
    ) {
        Message message = messageRepository.findById(messageId).orElseThrow(() -> new AppException(ErrorCode.MESSAGE_NOT_FOUND));
        return attachmentRepository.findByMessage(message)
                .stream()
                .map(attachmentMapper::toResponse)
                .toList();
    }

    public void deleteAttachment(Long id) {

        try {

            Attachment attachment =
                    attachmentRepository.findById(id)
                            .orElseThrow(() ->
                                    new AppException(
                                            ErrorCode.ATTACHMENT_NOT_FOUND
                                    ));

            if (attachment.getPublicId() != null) {

                cloudinary.uploader().destroy(
                        attachment.getPublicId(),
                        ObjectUtils.emptyMap()
                );
            }

            attachmentRepository.delete(attachment);

        } catch (IOException e) {
            throw new AppException(ErrorCode.DELETE_ATTACHMENT_FAILED);
        }
    }
}