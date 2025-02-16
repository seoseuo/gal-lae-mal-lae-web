package com.wannago.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.wannago.dto.ChatRoomDTO;
import com.wannago.entity.ChatRoom;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper {

    ChatRoomMapper INSTANCE = Mappers.getMapper(ChatRoomMapper.class);
    ChatRoomDTO toDto(ChatRoom chatRoom);
    ChatRoom toEntity(ChatRoomDTO chatRoomDTO);
    List<ChatRoomDTO> toDtoList(List<ChatRoom> chatRooms);
    List<ChatRoom> toEntityList(List<ChatRoomDTO> chatRoomDTOs);
}
