package com.wannago.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseChatRoomDTO {
    private Integer usIdx;
    private String usName;
    private String usProfile;
    private Integer usState;
    private String msgContent;
    private LocalDateTime msgCreatedAt;
}

