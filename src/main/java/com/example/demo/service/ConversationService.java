package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Conversation;
import com.example.demo.entity.User;
import com.example.demo.enums.ConversationType;
import com.example.demo.exception.AppException;
import com.example.demo.repository.ConversationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.dto.request.ConversationRequest;
import com.example.demo.dto.response.ConversationResponse;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;
import com.example.demo.mapper.ConversationMapper;
import com.example.demo.enums.ErrorCode;
@Service
@RequiredArgsConstructor
public class ConversationService {
    private final ConversationRepository conversationRepository;
    private final ConversationMapper conversationMapper;
    private final UserRepository userRepository;

    public List<ConversationResponse> getAllConversations() {
        List<Conversation> conversations = conversationRepository.findAll();
        return conversations.stream()
                .map(conversationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<ConversationResponse> searchConversations(String name) {
        List<Conversation> conversations = conversationRepository.findByNameContainingIgnoreCase(name);
        return conversations.stream()
                .map(conversationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ConversationResponse getConversationById(Long id) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));
        return conversationMapper.toResponse(conversation);
    }

    public ConversationResponse createConversation(ConversationRequest request) {
        if (!userRepository.existsById(request.getCreatedBy())) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        } else{
            User user = userRepository.findById(request.getCreatedBy())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
            Conversation conversation = conversationMapper.toEntity(request, user);
            Conversation savedConversation = conversationRepository.save(conversation);
            return conversationMapper.toResponse(savedConversation);
        }
    }

    public List<ConversationResponse> getByUserId (Long userId){
        List<Conversation> conversations = conversationRepository.getConversationsByUserId(userId);
        return conversations.stream()
                .map(conversationMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ConversationResponse updateConversation(Long id, String name, String avatarUrl) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));
        conversation.setName(name);
        conversation.setAvatarUrl(avatarUrl);
        Conversation updatedConversation = conversationRepository.save(conversation);
        return conversationMapper.toResponse(updatedConversation);
    }

    public void deleteConversation(Long id, Long userId) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONVERSATION_NOT_EXISTED));
        if (conversation.getType() == ConversationType.GROUP && !conversation.isDeleted() && conversation.getCreatedBy().getId().equals(userId)) {
            conversation.setDeleted(true);
            conversationRepository.save(conversation);
        }
        
    }
}
