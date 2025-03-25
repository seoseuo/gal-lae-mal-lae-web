package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location_do")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDo {
    @Id
    @Column(name = "ld_idx", nullable = false)
    private int ldIdx;

    @Column(name = "ld_name",nullable = false, length = 20)
    private String ldName;
}
