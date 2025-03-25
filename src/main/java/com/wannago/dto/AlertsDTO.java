package com.wannago.dto;

import lombok.Data;
import java.time.LocalDateTime;

import com.wannago.enums.ReadState;

@Data
public class AlertsDTO {
    private Integer alIdx;
    private String alContent;
    private LocalDateTime alDate;
    private ReadState alRead;
    private Integer alState;
    private Integer usIdx;
}
