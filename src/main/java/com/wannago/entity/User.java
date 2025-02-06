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
    @Column(name = "us_idx", nullable = false)
    private int usIdx;

    @Column(name = "us_name", nullable = false, length = 50)
    private String us_name;

    @Column(name = "us_email",nullable = false, length = 50)
    private String usEmail;

    @Column(name = "us_pw",nullable = false, length = 100)
    private String usPw;

    @Column(name="us_join_date", nullable = false)
    private Date usJoinDate;
    @Column(name = "us_leave_date", nullable = false)
    private Date usLeaveDate;

    @Column(name = "us_state",nullable = false)
    private int usState;
}
