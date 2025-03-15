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

    public TourSpotsDTO(String tsName, String tsFirstImage, String tsAddr1, String tsTel) {
        this.tsName = tsName;
        this.tsFirstImage = tsFirstImage;
        this.tsAddr1 = tsAddr1;
        this.tsTel = tsTel;
    }

    // Getters and setters
    public Integer getTsIdx() {
        return tsIdx;
    }

    public void setTsIdx(Integer tsIdx) {
        this.tsIdx = tsIdx;
    }

    public String getTsName() {
        return tsName;
    }

    public void setTsName(String tsName) {
        this.tsName = tsName;
    }

    public String getTsFirstImage() {
        return tsFirstImage;
    }

    public void setTsFirstImage(String tsFirstImage) {
        this.tsFirstImage = tsFirstImage;
    }

    public String getTsFirstImage2() {
        return tsFirstImage2;
    }

    public void setTsFirstImage2(String tsFirstImage2) {
        this.tsFirstImage2 = tsFirstImage2;
    }

    public Float getTsMapx() {
        return tsMapx;
    }

    public void setTsMapx(Float tsMapx) {
        this.tsMapx = tsMapx;
    }

    public Float getTsMapy() {
        return tsMapy;
    }

    public void setTsMapy(Float tsMapy) {
        this.tsMapy = tsMapy;
    }

    public Byte getTsMlevel() {
        return tsMlevel;
    }

    public void setTsMlevel(Byte tsMlevel) {
        this.tsMlevel = tsMlevel;
    }

    public String getTsAddr1() {
        return tsAddr1;
    }

    public void setTsAddr1(String tsAddr1) {
        this.tsAddr1 = tsAddr1;
    }

    public String getTsAddr2() {
        return tsAddr2;
    }

    public void setTsAddr2(String tsAddr2) {
        this.tsAddr2 = tsAddr2;
    }

    public String getTsZipcode() {
        return tsZipcode;
    }

    public void setTsZipcode(String tsZipcode) {
        this.tsZipcode = tsZipcode;
    }

    public String getTsTel() {
        return tsTel;
    }

    public void setTsTel(String tsTel) {
        this.tsTel = tsTel;
    }

    public String getTsContentTypeId() {
        return tsContentTypeId;
    }

    public void setTsContentTypeId(String tsContentTypeId) {
        this.tsContentTypeId = tsContentTypeId;
    }

    public String getTsBookTour() {
        return tsBookTour;
    }

    public void setTsBookTour(String tsBookTour) {
        this.tsBookTour = tsBookTour;
    }

    public String getTsCpyrhtDivCd() {
        return tsCpyrhtDivCd;
    }

    public void setTsCpyrhtDivCd(String tsCpyrhtDivCd) {
        this.tsCpyrhtDivCd = tsCpyrhtDivCd;
    }

    public String getTsCreatedTime() {
        return tsCreatedTime;
    }

    public void setTsCreatedTime(String tsCreatedTime) {
        this.tsCreatedTime = tsCreatedTime;
    }

    public String getTsModifiedTime() {
        return tsModifiedTime;
    }

    public void setTsModifiedTime(String tsModifiedTime) {
        this.tsModifiedTime = tsModifiedTime;
    }

    public Integer getLdIdx() {
        return ldIdx;
    }

    public void setLdIdx(Integer ldIdx) {
        this.ldIdx = ldIdx;
    }

    public Integer getLsIdx() {
        return lsIdx;
    }

    public void setLsIdx(Integer lsIdx) {
        this.lsIdx = lsIdx;
    }

    public String getC1Code() {
        return c1Code;
    }

    public void setC1Code(String c1Code) {
        this.c1Code = c1Code;
    }

    public String getC2Code() {
        return c2Code;
    }

    public void setC2Code(String c2Code) {
        this.c2Code = c2Code;
    }

    public String getC3Code() {
        return c3Code;
    }

    public void setC3Code(String c3Code) {
        this.c3Code = c3Code;
    }

    public LocationSi getLocationSi() {
        return locationSi;
    }

    public void setLocationSi(LocationSi locationSi) {
        this.locationSi = locationSi;
    }

    public LocationDo getLocationDo() {
        return locationDo;
    }

    public void setLocationDo(LocationDo locationDo) {
        this.locationDo = locationDo;
    }

    public Cat1 getCat1() {
        return cat1;
    }

    public void setCat1(Cat1 cat1) {
        this.cat1 = cat1;
    }

    public Cat2 getCat2() {
        return cat2;
    }

    public void setCat2(Cat2 cat2) {
        this.cat2 = cat2;
    }

    public Cat3 getCat3() {
        return cat3;
    }

    public void setCat3(Cat3 cat3) {
        this.cat3 = cat3;
    }
}

