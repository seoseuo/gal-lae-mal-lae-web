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
@NoArgsConstructor
public class ResponseChatRoomDTO {
    private Integer usIdx;
    private String usName;
    private String usProfile;
    private String msgContent;
    private LocalDateTime msgCreatedAt;

    public ResponseChatRoomDTO(Integer usIdx, String usName, String usProfile, String msgContent, LocalDateTime msgCreatedAt) {
        this.usIdx = usIdx;
        this.usName = usName;
        this.usProfile = usProfile;
        this.msgContent = msgContent;
        this.msgCreatedAt = msgCreatedAt;
    }
}
