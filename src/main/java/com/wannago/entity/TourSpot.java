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
public class TourSpot {
    @Id
    private Integer contentId;

    private String title;
    private String firstImage;
    private String firstImage2;
    private Float mapx;
    private Float mapy;
    private Byte mlevel;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    private String contentTypeId;
    private String bookTour;
    private String cpyrhtDivCd;
    private String createdTime;
    private String modifiedTime;
    private Integer ldIdx;
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
