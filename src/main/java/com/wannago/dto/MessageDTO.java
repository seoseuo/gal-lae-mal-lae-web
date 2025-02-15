package com.wannago.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.wannago.enums.ReadState;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private Integer msgIdx;
    private Integer msgSender;
    private String msgContent;
    private Integer crIdx;
    private LocalDateTime msgCreatedAt;
    private ReadState msgRead;
    private Integer msgState;
}
