package com.wannago.dto;

import lombok.Data;
import java.util.Date;
@Data
public class TravelGroupDTO {
    private int grIdx;
    private String grName;
    private int grState;
    private Date grCreatedAt;
    private Date grDeletedAt;
    // 이미지 추가
    private String grProfile;
}
