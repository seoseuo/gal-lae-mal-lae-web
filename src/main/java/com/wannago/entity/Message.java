package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "message")
public class Message {
    @Id
    private Integer msgIdx;

    private Integer msgSender;
    private Integer msgReceiver;
    private String msgContent;
    private LocalDateTime msgCreatedAt;

    @Enumerated(EnumType.STRING)
    private MsgState msgRead;

    private Byte msgState;

    public enum MsgState {
        READ, UNREAD
    }
}