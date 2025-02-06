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
@Table(name = "follow")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fo_idx", nullable = false)
    private Integer foIdx;

    @Column(name = "follower", nullable = false)
    private Integer follower;
    @Column(name = "followee", nullable = false)
    private Integer followee;
}
