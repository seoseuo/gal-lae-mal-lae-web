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
    @ManyToOne
    @JoinColumn(name = "gr_idx")
    private TravelGroup travelGroup;
    @Id
    @ManyToOne
    @JoinColumn(name = "us_idx")
    private User user;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberRole meRole;

    public enum MemberRole {
        MEMBER, ADMIN
    }
}
