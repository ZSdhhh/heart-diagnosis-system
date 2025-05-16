package com.example.heartdiagnosis.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Patient {
    private Integer id;
    private String name;
    private Integer age;
    private Integer gender;
    private String bloodPressure;
    private Float cholesterol;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 