package com.wannago.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wannago.dto.ChatRoomDTO;
import com.wannago.dto.ChatRoomInfo;
import com.wannago.dto.CreatChatDTO;
import com.wannago.dto.MessageDTO;
import com.wannago.dto.ResponseChatRoomDTO;
import com.wannago.dto.ResponseDTO;
import com.wannago.dto.UserDTO;
import com.wannago.entity.ChatMember;
import com.wannago.entity.ChatRoom;
import com.wannago.entity.Message;
import com.wannago.entity.User;
import com.wannago.repository.MessageRepository;
import com.wannago.repository.UserRepository;
import com.wannago.repository.ChatMemberRepository;
import com.wannago.repository.ChatRoomRepository;
import com.wannago.mapper.MessageMapper;
import com.wannago.mapper.UserMapper;
import com.wannago.mapper.ChatRoomMapper;
@Service
public class ChatService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ChatRoomMapper chatRoomMapper;
    @Autowired
    private ChatRoomRepository chatRoomRepository;
    @Autowired
    private ChatMemberRepository chatMemberRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    //채팅 메시지 저장
    public MessageDTO createMessage(MessageDTO message){
        Message messageEntity = messageMapper.toEntity(message);
        Message savedMessage = messageRepository.save(messageEntity);
        MessageDTO savedMessageDTO = messageMapper.toDTO(savedMessage);
        redisService.saveChatMessage("chat_room"+message.getCrIdx(), savedMessageDTO, 7);
        return savedMessageDTO;
    }

    //채팅 메시지 조회
    public List<MessageDTO> getChatMessage(Integer crIdx){
        if(redisService.hasKey("chat_room"+crIdx)){
            return redisService.getChatMessage("chat_room"+crIdx);
        }
        else{
            List<Message> messages = messageRepository.findByCrIdx(crIdx);
            List<MessageDTO> messageDTOs = messageMapper.toDTOList(messages);
            redisService.setChatMessageList("chat_room"+crIdx, messageDTOs, 7);
            return messageDTOs;
        }
    }

    //채팅 방 개설
    public ChatRoomDTO createChatRoom(CreatChatDTO creatChatDTO){
        Optional<ChatMember> chatMember = chatMemberRepository.findByUsIdxAndUsIdx2(creatChatDTO.getUsIdx(), creatChatDTO.getUsIdx2());
        if(chatMember.isPresent()){
            ChatRoom chatRoom = chatRoomRepository.findByCrIdx(chatMember.get().getCrIdx());
            ChatRoomDTO chatRoomDTO = chatRoomMapper.toDto(chatRoom);
            return chatRoomDTO;
        }
        ChatRoom chatRoom = ChatRoom.builder()
        .createdAt(LocalDateTime.now())
        .build();
        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        ChatRoomDTO savedChatRoomDTO = chatRoomMapper.toDto(savedChatRoom);
        ChatMember chatMember1 = ChatMember.builder()
        .crIdx(savedChatRoom.getCrIdx())
        .usIdx(creatChatDTO.getUsIdx())
        .build();
        ChatMember chatMember2 = ChatMember.builder()
        .crIdx(savedChatRoom.getCrIdx())
        .usIdx(creatChatDTO.getUsIdx2())
        .build();
        chatMemberRepository.save(chatMember1);
        chatMemberRepository.save(chatMember2);
        return savedChatRoomDTO;
    }

    //1:1 채팅방 존재확인
    public ResponseDTO checkChatRoom(Integer usIdx, Integer usIdx2){
        Optional<ChatMember> chatMember = chatMemberRepository.findByUsIdxAndUsIdx2(usIdx, usIdx2);
        if(chatMember.isEmpty()){
            return new ResponseDTO(false, "채팅 방 존재 안함");
        }
        return new ResponseDTO(true, "채팅 방 존재 합니다");
    }

    // 채팅방 정보가져오기
    public ChatRoomInfo getChatRoom(Integer otherUsIdx, Integer myUsIdx){
        Optional<ChatMember> chatMember = chatMemberRepository.findByUsIdxAndUsIdx2(myUsIdx, otherUsIdx);
        if(chatMember.isEmpty()){
            return new ChatRoomInfo(null, null, null);
        }
        Optional<User> myUser = userRepository.findByUsIdx(myUsIdx);
        Optional<User> otherUser = userRepository.findByUsIdx(otherUsIdx);
        if(myUser.isEmpty() || otherUser.isEmpty()){
            return new ChatRoomInfo(null, null, null);
        }
        UserDTO myUserDTO = userMapper.toDTO(myUser.get());
        UserDTO otherUserDTO = userMapper.toDTO(otherUser.get());
        ChatRoomInfo chatRoomInfo = new ChatRoomInfo(chatMember.get().getCrIdx(), myUserDTO, otherUserDTO);
        return chatRoomInfo;
    }

    //채팅방 조회
    public List<ResponseChatRoomDTO> getChatRoomList(Integer usIdx){
        return chatRoomRepository.findByUsIdx(usIdx);
    }
} 