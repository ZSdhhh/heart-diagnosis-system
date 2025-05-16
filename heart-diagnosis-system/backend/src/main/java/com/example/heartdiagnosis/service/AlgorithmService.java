package com.example.heartdiagnosis.service;

import com.example.heartdiagnosis.entity.Algorithm;

import java.util.List;

public interface AlgorithmService {
    
    List<Algorithm> getAllAlgorithms();
    
    List<Algorithm> getAllEnabledAlgorithms();
    
    boolean addAlgorithm(Algorithm algorithm);
    
    boolean updateAlgorithmStatus(Integer id, Integer status);
} 