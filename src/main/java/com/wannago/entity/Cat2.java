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
    private String cat2;

    @Column(nullable = false, length = 20)
    private String cat2Title;

    @ManyToOne
    @JoinColumn(name = "cat1", nullable = false)
    private Cat1 cat1;
}
