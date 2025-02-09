package com.wannago.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import com.wannago.entity.Travel;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelogueDTO {
    private Integer tlIdx;
    private String tlTitle;
    private String tlContent;
    private Integer tlState;
    private Integer trIdx;
    private Travel travel;
}
