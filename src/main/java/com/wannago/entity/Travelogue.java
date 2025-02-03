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
    private Integer tlIdx;

    private String tlTitle;
    private String tlContent;
    private Integer tlState;
    private Integer trIdx;
}
