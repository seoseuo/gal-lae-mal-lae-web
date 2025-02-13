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
    @Column(name = "c3_code",nullable = false)
    private String c3Code;


    @Column(name = "c3_name",nullable = false, length = 20)
    private String c3Name;



    @Column(name = "c2_code",nullable = false)
    private String c2Code;

    @ManyToOne
    @JoinColumn(name = "c2_code",insertable = false, updatable = false)
    private Cat2 cat2;
}