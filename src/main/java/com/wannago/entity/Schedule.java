package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Column(name = "sc_date", nullable = false)
    private LocalDate scDate;
    @Column(name = "sc_start_time",nullable = false)
    private LocalTime scStartTime;
    @Column(name = "sc_end_time",nullable = false)
    private LocalTime scEndTime;
    @Column(name = "tr_idx", nullable = false)
    private Integer trIdx;
    @Column(name = "contentid", nullable = false)
    private Integer contentid;
}
