package com.wannago.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.wannago.dto.ChatDTO;
import com.wannago.dto.ChatRequestDTO;
import com.wannago.dto.MessageDTO;
import com.wannago.entity.Message;
import com.wannago.enums.ReadState;
import com.wannago.service.ChatService;
import com.wannago.service.RedisService;
import org.springframework.stereotype.Controller;

@Controller
public class StompChatController {
    @Autowired
    private SimpMessagingTemplate template;
    @Autowired
    private ChatService chatService;
    @Autowired
    private RedisService redisService;
    
    @MessageMapping("/chat")
    public void sendMessage(ChatRequestDTO chat) {
        MessageDTO message = MessageDTO.builder()
        .msgSender(chat.getSender())
        .msgContent(chat.getMessage())
        .msgCreatedAt(LocalDateTime.now())
        .crIdx(chat.getCrIdx())
        .msgRead(ReadState.UNREAD)
        .msgState(1)
        .build();
        chatService.createMessage(message);
        template.convertAndSend("/sub/chat/" + chat.getCrIdx(), message);
    }
}
