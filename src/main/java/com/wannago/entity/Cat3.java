package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat3")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cat3 {
    @Id
    private String cat3;

    @Column(nullable = false, length = 20)
    private String cat3Title;

    @ManyToOne
    @JoinColumn(name = "cat2", nullable = false)
    private Cat2 cat2;
}
