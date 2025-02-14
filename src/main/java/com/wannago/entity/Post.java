package com.wannago.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "po_idx",nullable = false)
    private Integer poIdx;

    @Column(name = "po_title",nullable = false)
    private String poTitle;
    @Column(name = "po_content",nullable = false)
    private String poContent;
    @Column(name = "us_idx",nullable = false)
    private Integer usIdx;
    @Column(name = "po_created_at",nullable = false)
    private Date poCreatedAt;
    @Column(name = "po_updated_at",nullable = false)
    private Date poUpdatedAt;
    @Column(name = "po_deleted_at",nullable = false)
    private Date poDeletedAt;
    @Column(name = "po_state",nullable = false)
    private Integer poState;
    
    
}