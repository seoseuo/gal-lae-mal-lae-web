package com.wannago.dto;

import lombok.Data;

@Data
public class TravelogueDTO {
    private int tlIdx;
    private String tlTitle;
    private String tlContent;
    private int tlState;
    private int trIdx;
}
