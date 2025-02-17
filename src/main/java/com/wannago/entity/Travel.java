package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "travel")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Travel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trIdx;    
    
    @JoinColumn(name = "gr_idx", nullable = false)
    private int grIdx;

    @Column(name = "tr_start_time", nullable = true)
    private Date trStartTime;

    @Column(name = "tr_end_time", nullable = true)
    private Date trEndTime;

    @Column(name = "ld_idx", nullable = false, updatable = false)    
    private int ldIdx;

    @Column(name = "ls_idx", nullable = false, updatable = false)    
    private int lsIdx;

    // 추가
    @Column(name = "tr_period", nullable = false)
    private int trPeriod;

    @Column(nullable = false)
    private Date trCreatedAt;

    @Column(nullable = false)
    private int trState;

    @Column(nullable = true)
    private Date trDeletedAt;
}
