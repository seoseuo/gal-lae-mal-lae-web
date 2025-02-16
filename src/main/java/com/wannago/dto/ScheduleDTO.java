package com.wannago.dto;

import lombok.Data;

@Data
public class ScheduleDTO {
    private Integer scIdx;
    private int scDate;
    private String scStartTime;
    private String scEndTime;
    private int trIdx;
    private int tsIdx;
}
