package com.wannago.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cat1")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cat1 {
    @Id
    @Column(name = "cat1", nullable = false)
    private String cat1;

    @Column(name = "cat_title" ,nullable = false, length = 20)
    private String cat1Title;
}
