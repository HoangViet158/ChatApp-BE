package com.example.demo.mapper;

import com.example.demo.dto.request.ConversationMemberRequest;
import com.example.demo.dto.response.ConversationMemberResponse;
import com.example.demo.entity.Conversation;
import com.example.demo.entity.ConversationMember;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversationMemberMapper {

    // =========================
    // Request -> Entity
    // =========================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "conversation", source = "conversation")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ConversationMember toEntity(
            ConversationMemberRequest request,
            Conversation conversation,
            User user
    );

    // =========================
    // Entity -> Response
    // =========================
    @Mapping(target = "conversationId", source = "conversation.id")
    @Mapping(target = "conversationName", source = "conversation.name")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    ConversationMemberResponse toResponse(
            ConversationMember conversationMember
    );
}