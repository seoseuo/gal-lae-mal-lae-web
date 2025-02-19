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
@Table(name = "travelogue")
public class Travelogue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tl_idx", nullable = false)
    private Integer tlIdx;
    @Column(name = "tl_title", nullable = false)
    private String tlTitle;
    @Column(name = "tl_content", nullable = false)
    private String tlContent;
    @Column(name = "tl_state", nullable = false)
    private Integer tlState;

    @Column(name = "tr_idx", updatable = false)
    private Integer trIdx;

    // 공개 여부 칼럼
    @Column(name = "tl_public", nullable = false)
    private Integer tlPublic;

    // 이미지 칼럼
    @Column(name = "tl_image", nullable = false)
    private String tlImage;

    // 작성자 idx
    @Column(name = "us_idx", nullable = false)
    private Integer usIdx;



}
