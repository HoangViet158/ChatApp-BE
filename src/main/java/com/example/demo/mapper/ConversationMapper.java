package com.example.demo.mapper;
import com.example.demo.dto.request.ConversationRequest;
import com.example.demo.dto.response.ConversationResponse;
import com.example.demo.entity.Conversation;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConversationMapper {

    // =========================
    // Request -> Entity
    // =========================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", source = "createdBy")
    @Mapping(target = "avatarUrl" , source = "request.avatarUrl")
    Conversation toEntity(
            ConversationRequest request,
            User createdBy
    );

    // =========================
    // Entity -> Response
    // =========================
    @Mapping(target = "createdBy", source = "createdBy.id")
    @Mapping(target = "createdByUsername", source = "createdBy.username")
    ConversationResponse toResponse(Conversation conversation);
}