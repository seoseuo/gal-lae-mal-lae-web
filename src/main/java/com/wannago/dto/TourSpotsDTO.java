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
    private Integer tsIdx;
    private String tsName;
    private String tsFirstImage;
    private String tsFirstImage2;
    private Float tsMapx;
    private Float tsMapy;
    private Byte tsMlevel;
    private String tsAddr1;
    private String tsAddr2;
    private String tsZipcode;
    private String tsTel;
    private String tsContentTypeId;
    private String tsBookTour;
    private String tsCpyrhtDivCd;
    private String tsCreatedTime;
    private String tsModifiedTime;
    private Integer ldIdx;
    private Integer lsIdx;
    private String c1Code;
    private String c2Code;
    private String c3Code;
    private LocationSi locationSi;
    private LocationDo locationDo;
    private Cat1 cat1;
    private Cat2 cat2;
    private Cat3 cat3;
}

