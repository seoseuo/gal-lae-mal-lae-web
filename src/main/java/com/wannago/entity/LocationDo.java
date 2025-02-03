package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location_do")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ldIdx;

    @Column(nullable = false, length = 20)
    private String ldName;
}
