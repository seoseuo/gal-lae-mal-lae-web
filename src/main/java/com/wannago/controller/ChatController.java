package com.wannago.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wannago.dto.ChatRoomDTO;
import com.wannago.dto.CreatChatDTO;
import com.wannago.dto.MessageDTO;
import com.wannago.dto.ResponseDTO;
import com.wannago.dto.ResponseChatRoomDTO;
import com.wannago.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;
    
    //채팅 메시지 조회
    @GetMapping("/message/{crIdx}")
    public ResponseEntity<List<MessageDTO>> getChatMessage(@PathVariable Integer crIdx){
        return ResponseEntity.ok(chatService.getChatMessage(crIdx));
    }
    //채팅방 존재확인
    @GetMapping("/room/check")
    public ResponseEntity<ResponseDTO> checkChatRoom(@RequestBody CreatChatDTO creatChatDTO){
        return ResponseEntity.ok(chatService.checkChatRoom(creatChatDTO.getUsIdx(), creatChatDTO.getUsIdx2()));
    }
    //채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<ChatRoomDTO> createChatRoom(@RequestBody CreatChatDTO creatChatDTO){
        return ResponseEntity.ok(chatService.createChatRoom(creatChatDTO));
    }

    //채팅상대 목록조회
    @GetMapping("/room/list/{usIdx}")
    public ResponseEntity<List<ResponseChatRoomDTO>> getChatRoomList(@PathVariable Integer usIdx){
        return ResponseEntity.ok(chatService.getChatRoomList(usIdx));
    }
    
    
}
