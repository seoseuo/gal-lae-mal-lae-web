package com.wannago.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.Date;

import com.wannago.entity.TravelGroup;
import com.wannago.entity.LocationSi;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TravelDTO {
    private int trIdx;
    private TravelGroup travelGroup;
    private Date trStartTime;
    private Date trEndTime;
    private int lsIdx;
    private LocationSi locationSi;
    private Date trCreatedAt;
}
