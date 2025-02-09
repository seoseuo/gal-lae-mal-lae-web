package com.wannago.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.wannago.enums.MsgRead;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Integer msgIdx;
    private Integer msgSender;
    private Integer msgReceiver;
    private String msgContent;
    private LocalDateTime msgCreatedAt;
    private MsgRead msgRead;
    private Byte msgState;
}
