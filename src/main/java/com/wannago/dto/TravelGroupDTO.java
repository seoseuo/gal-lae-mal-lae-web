package com.wannago.dto;

import lombok.Data;
import java.util.Date;
import java.util.Set;
import java.util.List;

@Data
public class TravelGroupDTO {
    private int grIdx;
    private String grName;
    private int grState;
    private Date grCreatedAt;
    private Date grDeletedAt;
    // 이미지 추가
    private String grProfile;

    // 인원 수
    private int grCount;

    // 여행지 수
    private List<String> grLdList;
}
