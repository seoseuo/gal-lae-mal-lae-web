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
    @Column(name = "ts_idx",nullable = false) // Added @Column annotation
    private Integer tsIdx;


    @Column(name = "ts_name",nullable = false) // Added @Column annotation
    private String tsName;


    @Column(name = "ts_first_image") // Added @Column annotation
    private String tsFirstImage;


    @Column(name = "ts_first_image2") // Added @Column annotation
    private String tsFirstImage2;


    @Column(name = "ts_mapx") // Added @Column annotation
    private Float tsMapx;


    @Column(name = "ts_mapy") // Added @Column annotation
    private Float tsMapy;


    @Column(name = "ts_mlevel") // Added @Column annotation
    private Byte tsMlevel;


    @Column(name = "ts_addr1") // Added @Column annotation
    private String tsAddr1;


    @Column(name = "ts_addr2") // Added @Column annotation
    private String tsAddr2;


    @Column(name = "ts_zipcode") // Added @Column annotation
    private String tsZipcode;


    @Column(name = "ts_tel") // Added @Column annotation
    private String tsTel;


    @Column(name = "ts_content_type_id") // Added @Column annotation
    private String tsContentTypeId;


    @Column(name = "ts_book_tour") // Added @Column annotation
    private String tsBookTour;


    @Column(name = "ts_cpyrht_div_cd") // Added @Column annotation
    private String tsCpyrhtDivCd;


    @Column(name = "ts_created_time") // Added @Column annotation
    private String tsCreatedTime;


    @Column(name = "ts_modified_time") // Added @Column annoㄴtation
    private String tsModifiedTime;


    @Column(name = "ld_idx")
    private Integer ldIdx;


    @Column(name = "ls_idx")
    private Integer lsIdx;

    @Column(name = "c1_code")
    private String c1Code;

    @Column(name = "c2_code")
    private String c2Code;

    @Column(name = "c3_code")
    private String c3Code;
}