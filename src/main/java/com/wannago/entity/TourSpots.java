package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tour_spots")
public class TourSpots {
    @Id
    @Column(name = "content_id") // Added @Column annotation
    private Integer contentid;

    @Column(name = "title") // Added @Column annotation
    private String title;

    @Column(name = "first_image") // Added @Column annotation
    private String firstimage;

    @Column(name = "first_image2") // Added @Column annotation
    private String firstimage2;

    @Column(name = "mapx") // Added @Column annotation
    private Float mapx;

    @Column(name = "mapy") // Added @Column annotation
    private Float mapy;

    @Column(name = "mlevel") // Added @Column annotation
    private Byte mlevel;

    @Column(name = "addr1") // Added @Column annotation
    private String addr1;

    @Column(name = "addr2") // Added @Column annotation
    private String addr2;

    @Column(name = "zipcode") // Added @Column annotation
    private String zipcode;

    @Column(name = "tel") // Added @Column annotation
    private String tel;

    @Column(name = "content_type_id") // Added @Column annotation
    private String contenttypeid;

    @Column(name = "book_tour") // Added @Column annotation
    private String booktour;

    @Column(name = "cpyrht_div_cd") // Added @Column annotation
    private String cpyrhtDivCd;

    @Column(name = "created_time") // Added @Column annotation
    private String createdtime;

    @Column(name = "modified_time") // Added @Column annotation
    private String modifiedtime;

    @Column(name = "ld_idx") // Added @Column annotation
    private Integer ldIdx;

    @Column(name = "ls_idx") // Added @Column annotation
    private Integer lsIdx;

    @ManyToOne
    @JoinColumn(name = "cat1")
    private Cat1 cat1;

    @ManyToOne
    @JoinColumn(name = "cat2")
    private Cat2 cat2;

    @ManyToOne
    @JoinColumn(name = "cat3")
    private Cat3 cat3;
}