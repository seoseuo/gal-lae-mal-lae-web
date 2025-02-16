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
@IdClass(LocationSiId.class)
public class LocationSi {
    @Id
    @Column(name = "ls_idx", nullable = false)
    private int lsIdx;

    @Id
    @Column(name = "ld_idx", insertable = false)
    private Integer ldIdx;

    @Column(name = "ls_name",nullable = false, length = 20)
    private String lsName;


    @ManyToOne
    @JoinColumn(name = "ld_idx", nullable = false,insertable = false, updatable = false)
    private LocationDo locationDo;
}
