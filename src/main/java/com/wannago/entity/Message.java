package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import com.wannago.enums.ReadState;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_idx", nullable = false)
    private Integer msgIdx;

    @Column(name = "msg_sender", nullable = false)
    private Integer msgSender;
    @Column(name = "msg_Content", nullable = false)
    private String msgContent;
    @Column(name = "msg_created_at")
    private LocalDateTime msgCreatedAt;

    @Column(name = "cr_idx")
    private Integer crIdx;

    @Enumerated(EnumType.STRING)
    private ReadState msgRead;

    @Column(name = "msg_state")
    private Integer msgState;

}