package com.wannago.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "alerts")
public class Alerts {
    @Id
    private Integer alIdx;

    private String alContent;
    private LocalDateTime alDate;
    private Byte alRead;
    private Integer alState;
    private Integer usIdx;
}
