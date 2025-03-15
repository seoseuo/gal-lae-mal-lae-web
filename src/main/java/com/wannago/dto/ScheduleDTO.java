package com.wannago.dto;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ScheduleDTO {
    private Integer scIdx;
    private int scDate;
    private LocalTime scStartTime;
    private LocalTime scEndTime;
    private int trIdx;
    private int tsIdx;
    private String tsName;
    private String tsFirstImage;
    private String tsAddr1;    
    private String tsTel;
}
