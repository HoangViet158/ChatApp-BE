package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.example.demo.dto.request.MessageRequest;
import com.example.demo.service.MessageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping ("/chat.send")
    public void sendMessage(MessageRequest message) {

        // messageService.createMessage(message);

        messagingTemplate.convertAndSend(
                "/topic/conversation/" + message.getConversationId(),
                message
        );
    }
}