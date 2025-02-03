package com.wannago.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private Integer scIdx;

    private LocalDate scDate;
    private LocalTime scStartTime;
    private LocalTime scEndTime;
    private Integer trIdx;
    private Integer contentId;
}
