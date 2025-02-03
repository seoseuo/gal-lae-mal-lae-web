package com.wannago.dto;

import lombok.Data;

@Data
public class MessageDTO {
    private int msgIdx;
    private int msgSender;
    private int msgReceiver;
    private String msgContent;
    private String msgCreatedAt;
    private String msgRead;
    private int msgState;
}
