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
@Table(name = "alerts")
public class Alerts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "al_idx", nullable = false)
    private Integer alIdx;
    @Column(name = "al_content", nullable = false,length = 200)
    private String alContent;
    @Column(name = "al_date", nullable = false)
    private LocalDateTime alDate;
    @Column(name = "al_read", nullable = false)
    private ReadState alRead;
    @Column(name = "al_state", nullable = false)
    private Integer alState;
    @Column(name = "us_idx", nullable = false)
    private Integer usIdx;
}
