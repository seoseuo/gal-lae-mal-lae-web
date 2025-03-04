package com.wannago.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor

public class TravelViewDTO {
    private int trIdx;
    private String ldName;
    private String lsName;
    private List<String> tlImgList;
}
