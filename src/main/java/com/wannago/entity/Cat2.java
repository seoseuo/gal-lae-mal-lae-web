package com.wannago.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat2")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cat2 {
    @Id
    @Column(name = "cat2",nullable = false)
    private String cat2;

    @Column(nullable = false, length = 20)
    private String cat2Title;

    @Column(name = "cat1",nullable = false)
    private String cat1;
}
