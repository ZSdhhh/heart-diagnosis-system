package com.example.heartdiagnosis.algorithm.mock;

import com.example.heartdiagnosis.algorithm.interfaces.DiagnosisAlgorithm;
import com.example.heartdiagnosis.entity.Patient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MockDiagnosisAlgorithm implements DiagnosisAlgorithm {

    private final Random random = new Random();
    
    @Value("${algorithm.mock.name:模拟算法}")
    private String algorithmName;

    @Override
    public Float diagnose(Patient patientData) {
        // 模拟诊断逻辑，返回0-1之间的风险值
        return random.nextFloat();
    }

    @Override
    public String getAlgorithmName() {
        return algorithmName;
    }
} 