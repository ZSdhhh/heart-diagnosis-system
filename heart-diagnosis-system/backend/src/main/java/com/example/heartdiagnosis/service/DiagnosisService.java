package com.example.heartdiagnosis.service;

import com.example.heartdiagnosis.entity.Diagnosis;
import com.example.heartdiagnosis.entity.Patient;

import java.util.List;
import java.util.Map;

public interface DiagnosisService {
    
    Float diagnose(Integer patientId, String algorithmName);
    
    List<Diagnosis> getDiagnosisByPatientId(Integer patientId);
    
    Map<String, Object> getDiagnosisByPatientIdWithPaging(Integer patientId, int pageNum, int pageSize);
    
    Map<String, Object> getStatistics();
} 