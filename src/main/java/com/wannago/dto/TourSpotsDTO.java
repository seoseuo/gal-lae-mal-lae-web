package com.wannago.dto;

import com.wannago.entity.Cat1;
import com.wannago.entity.Cat2;
import com.wannago.entity.Cat3;
import lombok.Data;

@Data
public class TourSpotsDTO {
    private int contentid;
    private String title;
    private String firstimage;
    private String firstimage2;
    private Float mapx;
    private Float mapy;
    private int mlevel;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    private String contenttypeid;
    private String booktour;
    private String cpyrhtDivCd;
    private String createdtime;
    private String modifiedtime;
    private int ldIdx;
    private int lsIdx;
    private Cat1 cat1;
    private Cat2 cat2;
    private Cat3 cat3;
}

