package com.wannago.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "oauth")
public class Oauth {
    @Id
    private Integer oaIdx;

    private String type;

    private Integer usIdx;

    private Long oaId;
}
