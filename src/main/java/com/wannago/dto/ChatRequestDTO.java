package com.wannago.dto;

import lombok.Data;

@Data
public class ChatRequestDTO {
    private Integer sender;
    private Integer receiver;
    private String message;
    private Integer crIdx;
}
