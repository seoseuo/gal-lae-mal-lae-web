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

    @Column(name = "tr_idx", insertable = false, updatable = false)
    private Integer trIdx;

    @ManyToOne
    @JoinColumn(name = "tr_idx")
    private Travel travel;


}
