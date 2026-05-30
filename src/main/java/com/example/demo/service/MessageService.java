package com.example.demo.service;

import com.example.demo.dto.request.MessageRequest;
import com.example.demo.dto.response.MessageResponse;
import com.example.demo.entity.Conversation;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.enums.ErrorCode;
import com.example.demo.mapper.MessageMapper;
import com.example.demo.repository.ConversationRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    @Transactional
    public MessageResponse createMessage(MessageRequest request) {
        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Message replyToMessage = null;
        if (request.getReplyToMessageId() != null) {
            replyToMessage = messageRepository.findById(request.getReplyToMessageId())
                    .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        }

        Message message = messageMapper.toEntity(request, conversation, sender, replyToMessage);
        Message saved = messageRepository.save(message);
        return messageMapper.toResponse(saved);
    }

    @Transactional
    public Message createMessageEntity(MessageRequest request) {
        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));

        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        Message replyToMessage = null;
        if (request.getReplyToMessageId() != null) {
            replyToMessage = messageRepository.findById(request.getReplyToMessageId())
                    .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        }

        Message message = messageMapper.toEntity(request, conversation, sender, replyToMessage);
        Message saved = messageRepository.save(message);
        return saved;
    }

    @Transactional(readOnly = true)
    public MessageResponse getMessageById(Long id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
        return messageMapper.toResponse(message);
    }

    @Transactional
    public MessageResponse updateMessage(Long id, MessageRequest request) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));

        // Quy tắc update: chỉ update nội dung/chỉnh sửa; không đổi conversation/sender
        message.setContent(request.getContent());
        message.setMessageType(request.getMessageType());
        message.setIsEdited(true);

        if (request.getReplyToMessageId() != null) {
            Message replyToMessage = messageRepository.findById(request.getReplyToMessageId())
                    .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
            message.setReplyToMessage(replyToMessage);
        } else {
            message.setReplyToMessage(null);
        }

        Message updated = messageRepository.save(message);
        return messageMapper.toResponse(updated);
    }

    @Transactional
    public void deleteMessage(Long id, Long userId) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));

        if (message.getSender() == null || message.getSender().getId() == null || !message.getSender().getId().equals(userId)) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        message.setIsDeleted(true);
        messageRepository.save(message);
    }

    public List<MessageResponse> getMessagesByConversationId(Long conversationId){
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));
        List<MessageResponse> messages = messageRepository.findByConversationOrderByCreatedAtAsc(conversation)
            .stream()
            .map(messageMapper::toResponse)
            .toList();
        return messages;    
    }

    public List<MessageResponse> getAllMessages(){
        return messageRepository.findAll()
            .stream()
            .map(messageMapper::toResponse)
            .toList();
    }

}

