package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usIdx;

    @Column(nullable = false, length = 50)
    private String usEmail;

    @Column(nullable = false, length = 100)
    private String usPw;

    @Column(nullable = false)
    private Date usJoinDate;

    private Date usLeaveDate;

    @Column(nullable = false)
    private int usState;

    @OneToMany(mappedBy = "user")
    private Set<Member> members;
}
