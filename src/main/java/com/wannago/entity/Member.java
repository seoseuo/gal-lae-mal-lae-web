package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(MemberId.class)
public class Member {
    @Id
    @Column(name = "gr_idx", nullable = false)
    private int grIdx;
    @Id
    @Column(name = "us_idx")
    private int usIdx;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole meRole;

    public enum MemberRole {
        MEMBER, ADMIN
    }
}
