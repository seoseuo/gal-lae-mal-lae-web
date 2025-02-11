package com.wannago.mapper;

import com.wannago.dto.MessageDTO;
import com.wannago.entity.Message;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-09T22:10:44+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.14 (Homebrew)"
)
@Component
public class MessageMapperImpl implements MessageMapper {

    @Override
    public MessageDTO toDTO(Message message) {
        if ( message == null ) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();

        if ( message.getMsgIdx() != null ) {
            messageDTO.setMsgIdx( message.getMsgIdx() );
        }
        if ( message.getMsgSender() != null ) {
            messageDTO.setMsgSender( message.getMsgSender() );
        }
        if ( message.getMsgReceiver() != null ) {
            messageDTO.setMsgReceiver( message.getMsgReceiver() );
        }
        messageDTO.setMsgContent( message.getMsgContent() );
        if ( message.getMsgCreatedAt() != null ) {
            messageDTO.setMsgCreatedAt( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( message.getMsgCreatedAt() ) );
        }
        if ( message.getMsgRead() != null ) {
            messageDTO.setMsgRead( message.getMsgRead().name() );
        }
        if ( message.getMsgState() != null ) {
            messageDTO.setMsgState( message.getMsgState() );
        }

        return messageDTO;
    }

    @Override
    public Message toEntity(MessageDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Message.MessageBuilder message = Message.builder();

        message.msgIdx( dto.getMsgIdx() );
        message.msgSender( dto.getMsgSender() );
        message.msgReceiver( dto.getMsgReceiver() );
        message.msgContent( dto.getMsgContent() );
        if ( dto.getMsgCreatedAt() != null ) {
            message.msgCreatedAt( LocalDateTime.parse( dto.getMsgCreatedAt() ) );
        }
        if ( dto.getMsgRead() != null ) {
            message.msgRead( Enum.valueOf( Message.MsgState.class, dto.getMsgRead() ) );
        }
        message.msgState( (byte) dto.getMsgState() );

        return message.build();
    }

    @Override
    public List<MessageDTO> toDTOList(List<Message> messageList) {
        if ( messageList == null ) {
            return null;
        }

        List<MessageDTO> list = new ArrayList<MessageDTO>( messageList.size() );
        for ( Message message : messageList ) {
            list.add( toDTO( message ) );
        }

        return list;
    }

    @Override
    public List<Message> toEntityList(List<MessageDTO> messageDTOList) {
        if ( messageDTOList == null ) {
            return null;
        }

        List<Message> list = new ArrayList<Message>( messageDTOList.size() );
        for ( MessageDTO messageDTO : messageDTOList ) {
            list.add( toEntity( messageDTO ) );
        }

        return list;
    }
}
