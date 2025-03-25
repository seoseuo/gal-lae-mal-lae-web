package com.wannago.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.wannago.entity.LocationDo;

import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationSiDTO {
    private int lsIdx;
    private String lsName;
    private Integer ldIdx;
    private LocationDo locationDo;

    private String ldName;
}
