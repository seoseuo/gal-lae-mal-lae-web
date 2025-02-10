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
    @Column(name = "c2_code",nullable = false)
    private String c2_code;


    @Column(name = "c2_name",nullable = false, length = 20)
    private String c2_name;



    @Column(name = "c1_code",nullable = false)
    private String c1_code;

    @ManyToOne
    @JoinColumn(name = "c1_code",insertable = false, updatable = false)
    private Cat1 cat1;
}
