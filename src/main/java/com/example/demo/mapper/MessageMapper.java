package com.example.demo.mapper;
import com.example.demo.dto.request.MessageRequest;
import com.example.demo.dto.response.MessageResponse;
import com.example.demo.entity.Conversation;
import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    // =========================
    // Request -> Entity
    // =========================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)

    @Mapping(target = "content", source = "request.content")
    @Mapping(target = "messageType", source = "request.messageType")

    @Mapping(target = "conversation", source = "conversation")
    @Mapping(target = "sender", source = "sender")
    @Mapping(target = "replyToMessage", source = "replyToMessage")
    Message toEntity(
            MessageRequest request,
            Conversation conversation,
            User sender,
            Message replyToMessage
    );

    // =========================
    // Entity -> Response
    // =========================
    @Mapping(target = "conversationId", source = "conversation.id")
    @Mapping(target = "conversationName", source = "conversation.name")
    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "senderName", source = "sender.username")
    @Mapping(target = "replyToMessageId", source = "replyToMessage.id")
    @Mapping(
        target = "attachments",
        source = "attachments"
    )
    MessageResponse toResponse(Message message);
}