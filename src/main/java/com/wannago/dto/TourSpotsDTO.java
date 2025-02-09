package com.wannago.dto;

import com.wannago.entity.Cat1;
import com.wannago.entity.Cat2;
import com.wannago.entity.Cat3;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
    
import com.wannago.entity.LocationDo;
import com.wannago.entity.LocationSi;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TourSpotsDTO {
    private Integer contentid;
    private String title;
    private String firstimage;
    private String firstimage2;
    private Float mapx;
    private Float mapy;
    private Byte mlevel;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String tel;
    private String contenttypeid;
    private String booktour;
    private String cpyrhtDivCd;
    private String createdtime;
    private String modifiedtime;
    private Integer ldIdx;
    private Integer lsIdx;
    private Cat1 cat1;
    private Cat2 cat2;
    private Cat3 cat3;
    private LocationSi locationSi;
    private LocationDo locationDo;
}

