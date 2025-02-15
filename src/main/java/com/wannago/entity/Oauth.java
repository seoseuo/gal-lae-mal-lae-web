package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "oauth")
public class Oauth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oa_idx", nullable = false)
    private Integer oaIdx;

    @Column(name = "oa_type",nullable = false) 
    private String oaType;

    @Column(name = "us_idx",nullable = false)
    private Integer usIdx;

    @Column(name = "oa_id",nullable = false)
    private Long oaId;
}
