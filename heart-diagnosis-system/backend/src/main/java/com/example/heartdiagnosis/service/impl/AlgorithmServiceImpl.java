package com.example.heartdiagnosis.service.impl;

import com.example.heartdiagnosis.entity.Algorithm;
import com.example.heartdiagnosis.mapper.AlgorithmMapper;
import com.example.heartdiagnosis.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlgorithmServiceImpl implements AlgorithmService {

    @Autowired
    private AlgorithmMapper algorithmMapper;
    
    @Override
    public List<Algorithm> getAllAlgorithms() {
        return algorithmMapper.findAll();
    }
    
    @Override
    public List<Algorithm> getAllEnabledAlgorithms() {
        return algorithmMapper.findAllEnabled();
    }
    
    @Override
    public boolean addAlgorithm(Algorithm algorithm) {
        if (algorithm.getStatus() == null) {
            algorithm.setStatus(1); // 默认启用
        }
        return algorithmMapper.insert(algorithm) > 0;
    }
    
    @Override
    public boolean updateAlgorithmStatus(Integer id, Integer status) {
        return algorithmMapper.updateStatus(id, status) > 0;
    }
} 