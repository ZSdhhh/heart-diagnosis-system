package com.example.heartdiagnosis.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Algorithm {
    private Integer id;
    private String name;
    private String description;
    private Integer status;
    private LocalDateTime createTime;
} 