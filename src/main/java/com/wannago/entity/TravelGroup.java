package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "travel_group")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gr_idx", nullable = false)
    private int grIdx;

    @Column(name = "gr_name",nullable = false, length = 30)
    private String grName;

    @Column(name = "gr_state",nullable = false)
    private int grState;

    @Column(name = "gr_created_at",nullable = false)
    private Date grCreatedAt;

    @Column(name = "gr_deleated_at",nullable = true)
    private Date grDeletedAt;

    @Column(name = "gr_profile", length = 200)
    @Builder.Default
    private String grProfile = "profile.png";
}
