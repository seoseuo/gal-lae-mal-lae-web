package com.wannago.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

import com.wannago.dto.ChatMemberDTO;
import com.wannago.entity.ChatMember;

@Mapper(componentModel = "spring")
public interface ChatMemberMapper {

    ChatMemberMapper INSTANCE = Mappers.getMapper(ChatMemberMapper.class);
    ChatMemberDTO toDTO(ChatMember chatMember);
    ChatMember toEntity(ChatMemberDTO chatMemberDTO);

    List<ChatMemberDTO> toDTOList(List<ChatMember> chatMemberList);
    List<ChatMember> toEntityList(List<ChatMemberDTO> chatMemberDTOList);
}
