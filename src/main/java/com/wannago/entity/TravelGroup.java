package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "travel_group")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int grIdx;

    @Column(nullable = false, length = 30)
    private String grName;

    @Column(nullable = false)
    private int grState;

    @Column(nullable = false)
    private Date grCreatedAt;

    @Column(nullable = false)
    private Date grDeletedAt;
}
