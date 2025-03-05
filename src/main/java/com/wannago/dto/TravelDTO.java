package com.wannago.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;



@Data
@NoArgsConstructor
@AllArgsConstructor

public class TravelDTO {
    private int trIdx;
    private int grIdx;
    private Date trStartTime;
    private Date trEndTime;
    private int ldIdx;
    private int lsIdx;
    private Date trCreatedAt;
    // 여행 삭제 날짜 추가
    private Date trDeletedAt;
    // 여행 상태 추가
    private int trState;
    // 여행 기간 추가
    private int trPeriod;    
}
