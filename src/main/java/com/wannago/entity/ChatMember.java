package com.wannago.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chat_member")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@IdClass(ChatMemberId.class)
public class ChatMember {
    @Id
    private Integer crIdx;
    
    @Id
    private Integer usIdx;
}
