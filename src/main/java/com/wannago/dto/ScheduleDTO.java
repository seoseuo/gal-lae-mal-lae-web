package com.wannago.dto;

import lombok.Data;

@Data
public class ScheduleDTO {
    private int scIdx;
    private String scDate;
    private String scStartTime;
    private String scEndTime;
    private int trIdx;
    private int contentid;
}
