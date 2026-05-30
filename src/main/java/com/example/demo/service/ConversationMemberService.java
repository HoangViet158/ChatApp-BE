package com.example.demo.service;

import org.springframework.stereotype.Service;
import com.example.demo.mapper.ConversationMapper;
import com.example.demo.mapper.ConversationMemberMapper;
import com.example.demo.dto.request.ConversationMemberRequest;
import com.example.demo.dto.response.ConversationResponse;
import com.example.demo.entity.Conversation;
import com.example.demo.entity.ConversationMember;
import com.example.demo.entity.User;
import com.example.demo.enums.ConversationType;
import com.example.demo.enums.ErrorCode;
import com.example.demo.exception.AppException;
import com.example.demo.repository.ConversationMemberRepository;
import com.example.demo.repository.ConversationRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.dto.response.ConversationMemberResponse;

import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversationMemberService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final ConversationMemberRepository conversationMemberRepository;
    private final ConversationMemberMapper conversationMemberMapper;

@Transactional
public void addMemberToConversation(
        Long conversationId,
        Long creatorId,
        List<Long> userIds
) {

    User creator = userRepository.findById(creatorId)
            .orElseThrow(() ->
                    new AppException(ErrorCode.USER_NOT_EXISTED));

    Conversation conversation;

    if (conversationId != null) {

        conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() ->
                        new AppException(
                                ErrorCode.CONVERSATION_NOT_EXISTED
                        ));

    } else {

        conversation = Conversation.builder()
                .createdBy(creator)
                .type(ConversationType.GROUP)
                .build();

        conversation = conversationRepository.save(conversation);
    }

    final Conversation finalConversation = conversation;
    Set<Long> allUserIds = new HashSet<>(userIds);
    allUserIds.add(creatorId);

    List<User> users = userRepository.findByIdIn(allUserIds);

    List<ConversationMember> members = users.stream()
            .map(user -> (ConversationMember) ConversationMember.builder()
                    .conversation(finalConversation)
                    .user(user)
                    .build())
            .toList();

    conversationMemberRepository.saveAll(members);
}
   public ConversationMemberResponse getConversationMemberById(Long id) {

        ConversationMember conversation = conversationMemberRepository.findById(id)
                .orElseThrow(() ->
                        new AppException(
                                ErrorCode.CONVERSATION_MEMBER_NOT_EXISTED
                        ));
        return conversationMemberMapper.toResponse(conversation);
    }

    public List<ConversationMemberResponse> getAllConversationMembers() {

        return conversationMemberRepository.findAll().stream()
        .map(conversationMemberMapper::toResponse)
        .toList();
    }

    public List<ConversationMemberResponse> getMembersByConversationId(
            Long conversationId
    ) {

        List<ConversationMember> conversationmember = conversationMemberRepository
                .findByConversationId(conversationId);
        return conversationmember.stream()
        .map(conversationMemberMapper::toResponse)
        .toList();
    }

    public void updateConversationMember(
            Long id,
            ConversationMemberRequest request
    ) {

        ConversationMember conversationMember =
                conversationMemberRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.CONVERSATION_MEMBER_NOT_EXISTED
                                ));

        if (request.getConversationId() != null) {

            Conversation conversation =
                    conversationRepository.findById(
                                    request.getConversationId()
                            )
                            .orElseThrow(() ->
                                    new AppException(
                                            ErrorCode.CONVERSATION_NOT_EXISTED
                                    ));

            conversationMember.setConversation(conversation);
        }

        if (request.getUserId() != null) {

            User user = userRepository.findById(
                            request.getUserId()
                    )
                    .orElseThrow(() ->
                            new AppException(
                                    ErrorCode.USER_NOT_EXISTED
                            ));

            conversationMember.setUser(user);
        }

        conversationMemberRepository.save(conversationMember);
    }


    public void updateLastSeen(Long id, Long messageId){
        ConversationMember conversationMember = conversationMemberRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.CONVERSATION_MEMBER_NOT_EXISTED
                                ));
        conversationMember.setLastReadMessageId(messageId);
        conversationMemberRepository.save(conversationMember);
    }
    public void deleteConversationMember(Long id) {

        ConversationMember conversationMember =
                conversationMemberRepository.findById(id)
                        .orElseThrow(() ->
                                new AppException(
                                        ErrorCode.CONVERSATION_MEMBER_NOT_EXISTED
                                ));
        conversationMember.setDeleted(true);
        conversationMemberRepository.save(conversationMember);
    }
}
