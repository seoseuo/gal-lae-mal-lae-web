package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location_si")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationSi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ls_idx", nullable = false)
    private int lsIdx;

    @Column(name = "ls_name",nullable = false, length = 20)
    private String lsName;

    @Column(name = "ld_idx", insertable = false, updatable = false) // ✅ Hibernate가 자동으로 관리 X
    private Integer ldIdx;

    @ManyToOne
    @JoinColumn(name = "ld_idx", nullable = false)
    private LocationDo locationDo;
}
