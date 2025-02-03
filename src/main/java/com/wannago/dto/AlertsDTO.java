package com.wannago.dto;

import lombok.Data;

@Data
public class AlertsDTO {
    private int alIdx;
    private String alContent;
    private String alDate;
    private int alRead;
    private int alState;
    private int usIdx;
}
