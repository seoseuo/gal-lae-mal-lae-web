package com.wannago.mapper;

import com.wannago.dto.MessageDTO;
import com.wannago.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageDTO toDTO(Message message);
    Message toEntity(MessageDTO dto);

    List<MessageDTO> toDTOList(List<Message> messageList);
    List<Message> toEntityList(List<MessageDTO> messageDTOList);
}
