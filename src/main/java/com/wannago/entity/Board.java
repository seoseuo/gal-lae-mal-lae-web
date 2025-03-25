package com.wannago.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @Column(name = "bo_date", nullable = false)
    private LocalDateTime boDate;
    
    @Column(name = "bo_state", nullable = false)
    private Integer boState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "us_idx")
    private User user;

    public Integer getUsIdx() {
        return user != null ? user.getUsIdx() : null;
    }

    public Board setTempUser(Integer usIdx) {
        this.user = User.builder()
                       .usIdx(usIdx)
                       .build();
        return this;
    }
}
