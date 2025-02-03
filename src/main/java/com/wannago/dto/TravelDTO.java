package com.wannago.dto;

import lombok.Data;

@Data
public class TravelDTO {
    private int trIdx;
    private int grIdx;
    private String trStartTime;
    private String trEndTime;
    private int lsIdx;
    private String trCreatedAt;
}
