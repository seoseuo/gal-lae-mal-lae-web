package com.wannago.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Integer boIdx;
    private String boTitle;
    private String boContent;
    private String boImg;
    private Integer ldIdx;
    private Integer boLike;
    private Integer lsIdx;
    private Integer usIdx;
    private LocalDateTime boDate;
    private Integer boState;
    private String usName;
    private String usProfile;
}
