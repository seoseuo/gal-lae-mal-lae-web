package com.wannago.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AlertsDTO {
    private Integer alIdx;
    private String alContent;
    private LocalDateTime alDate;
    private Byte alRead;
    private Integer alState;
    private Integer usIdx;
}
