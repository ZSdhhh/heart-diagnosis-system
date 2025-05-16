package com.example.heartdiagnosis.algorithm.interfaces;

import com.example.heartdiagnosis.entity.Patient;

public interface DiagnosisAlgorithm {
    // 执行诊断并返回结果
    Float diagnose(Patient patientData);
    
    // 获取算法名称
    String getAlgorithmName();
} 