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

    @ManyToOne
    @JoinColumn(name = "gr_idx", nullable = false)
    private TravelGroup travelGroup;

    @Column(nullable = false)
    private Date trStartTime;

    @Column(nullable = false)
    private Date trEndTime;

    @ManyToOne
    @JoinColumn(name = "ls_idx", nullable = false)
    private LocationSi locationSi;

    @Column(nullable = false)
    private Date trCreatedAt;
}
