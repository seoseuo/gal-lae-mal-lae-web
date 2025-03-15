package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer scIdx;

    @Column(name = "sc_date")
    private int scDate;

    @Column(name = "sc_start_time")
    private LocalTime scStartTime;

    @Column(name = "sc_end_time")
    private LocalTime scEndTime;

    @Column(name = "tr_idx", nullable = false)
    private Integer trIdx;

    @Column(name = "ts_idx", nullable = true)
    private Integer tsIdx;    

    @Version
    @Column(name = "version", nullable = true)
    private Long version;  // 낙관적 잠금을 위한 버전 필드
}
