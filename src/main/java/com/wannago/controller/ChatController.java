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
import com.wannago.dto.ChatRoomInfo;
import com.wannago.dto.CreatChatDTO;
import com.wannago.dto.MessageDTO;
import com.wannago.dto.ResponseDTO;
import com.wannago.dto.ResponseChatRoomDTO;
import com.wannago.service.ChatService;
import com.wannago.util.jwt.JwtProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private JwtProvider jwtProvider;
    

    @Autowired
    private ChatService chatService;
    
    //채팅 메시지 조회
    @GetMapping("/message/{crIdx}")
    public ResponseEntity<List<MessageDTO>> getChatMessage(@PathVariable Integer crIdx){
        return ResponseEntity.ok(chatService.getChatMessage(crIdx));
    }
    //채팅방 존재확인
    @GetMapping("/room/check")
    public ResponseEntity<ResponseDTO> checkChatRoom(@RequestParam Integer usIdx, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("accessToken")){
                    token = cookie.getValue();
                }
            }
        }
        Integer usIdx2 = jwtProvider.getUsIdx(token);
        return ResponseEntity.ok(chatService.checkChatRoom(usIdx, usIdx2));
    }

    // 채팅방 정보가져오기
    @GetMapping("/room")
    public ResponseEntity<ChatRoomInfo> getChatRoom(@RequestParam Integer usIdx, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("accessToken")){
                    token = cookie.getValue();
                }
            }
        }
        Integer usIdx2 = jwtProvider.getUsIdx(token);
        return ResponseEntity.ok(chatService.getChatRoom(usIdx, usIdx2));
    }
    //채팅방 생성
    @PostMapping("/room")
    public ResponseEntity<ChatRoomDTO> createChatRoom(@RequestParam Integer usIdx, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("accessToken")){
                    token = cookie.getValue();
                }
            }
        }
        Integer usIdx2 = jwtProvider.getUsIdx(token);
        ResponseDTO responseDTO = chatService.checkChatRoom(usIdx, usIdx2);
        if(!responseDTO.isSuccess()){
            CreatChatDTO creatChatDTO = new CreatChatDTO(usIdx, usIdx2);
            return ResponseEntity.ok(chatService.createChatRoom(creatChatDTO));
        }else{
            return ResponseEntity.status(500).body(null);
        }
    }

    //채팅상대 목록조회
    @GetMapping("/room/list")
    public ResponseEntity<List<ResponseChatRoomDTO>> getChatRoomList(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("accessToken")){
                    token = cookie.getValue();
                }
            }
        }
        Integer usIdx = jwtProvider.getUsIdx(token);
        return ResponseEntity.ok(chatService.getChatRoomList(usIdx));
    }
    
    
}
