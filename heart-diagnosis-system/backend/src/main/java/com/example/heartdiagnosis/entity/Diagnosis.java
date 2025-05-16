package com.example.heartdiagnosis.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Diagnosis {
    private Integer id;
    private Integer patientId;
    private String algorithm;
    private Float result;
    private LocalDateTime createTime;
} 