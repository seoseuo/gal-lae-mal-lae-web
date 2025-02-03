package com.wannago.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberId implements java.io.Serializable {
    private Integer grIdx;
    private Integer usIdx;
}