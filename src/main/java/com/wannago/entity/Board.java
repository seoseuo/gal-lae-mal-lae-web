package com.wannago.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bo_idx")
    private Integer boIdx;

    @Column(name = "bo_title", length = 50, nullable = false)
    private String boTitle;

    @Column(name = "bo_content", length = 300, nullable = false)
    private String boContent;

    @Column(name = "bo_img", length = 50)
    private String boImg;

    @Column(name = "ld_idx", nullable = true)
    private Integer ldIdx;

    @Column(name = "bo_like", nullable = false)
    private Integer boLike;

    @Column(name = "ls_idx", nullable = true)
    private Integer lsIdx;

    @Column(name = "us_idx", nullable = false)
    private Integer usIdx;

    @Column(name = "bo_date", nullable = false)
    private LocalDateTime boDate;
    
    @Column(name = "bo_state", nullable = false)
    private Integer boState;
}
