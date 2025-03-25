package com.wannago.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TravelogueLike")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelogueLike {
    @Id
    @Column(name = "tl_idx", nullable = false)
    private Integer tlIdx;


    @Column(name = "us_idx" ,nullable = false)
    private Integer usIdx;
}
