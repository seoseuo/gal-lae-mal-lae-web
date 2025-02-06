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
    @Column(name = "cat3",nullable = false)
    private String cat3;

    @Column(name = "cat3_title",nullable = false, length = 20)
    private String cat3Title;

    @Column(name = "cat2",nullable = false)
    private String cat2;
}
