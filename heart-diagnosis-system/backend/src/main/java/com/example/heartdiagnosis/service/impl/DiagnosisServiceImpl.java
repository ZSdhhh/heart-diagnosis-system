package com.example.heartdiagnosis.service.impl;

import com.example.heartdiagnosis.algorithm.interfaces.DiagnosisAlgorithm;
import com.example.heartdiagnosis.algorithm.mock.MockDiagnosisAlgorithm;
import com.example.heartdiagnosis.entity.AlgorithmCount;
import com.example.heartdiagnosis.entity.Diagnosis;
import com.example.heartdiagnosis.entity.Patient;
import com.example.heartdiagnosis.mapper.DiagnosisMapper;
import com.example.heartdiagnosis.mapper.PatientMapper;
import com.example.heartdiagnosis.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    @Autowired
    private DiagnosisMapper diagnosisMapper;
    
    @Autowired
    private PatientMapper patientMapper;
    
    @Autowired
    private MockDiagnosisAlgorithm mockAlgorithm;
    
    @Override
    public Float diagnose(Integer patientId, String algorithmName) {
        Patient patient = patientMapper.findById(patientId);
        if (patient == null) {
            return null;
        }
        
        DiagnosisAlgorithm algorithm = getAlgorithm(algorithmName);
        if (algorithm == null) {
            algorithm = mockAlgorithm;
        }
        
        Float result = algorithm.diagnose(patient);
        
        // 直接使用前端传入的算法名称，不再使用算法对象提供的名称
        // 因为前端显示的名称就是用户看到和选择的名称
        
        // 保存诊断结果
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setPatientId(patientId);
        diagnosis.setAlgorithm(algorithmName);
        diagnosis.setResult(result);
        diagnosisMapper.insert(diagnosis);
        
        return result;
    }
    
    @Override
    public List<Diagnosis> getDiagnosisByPatientId(Integer patientId) {
        return diagnosisMapper.findByPatientId(patientId);
    }
    
    @Override
    public Map<String, Object> getDiagnosisByPatientIdWithPaging(Integer patientId, int pageNum, int pageSize) {
        Map<String, Object> result = new HashMap<>();
        
        // 计算偏移量
        int offset = (pageNum - 1) * pageSize;
        
        // 获取指定页的记录
        List<Diagnosis> records = diagnosisMapper.findByPatientIdWithPaging(patientId, offset, pageSize);
        
        // 获取总记录数
        int total = diagnosisMapper.countByPatientId(patientId);
        
        // 计算总页数
        int totalPages = (total + pageSize - 1) / pageSize;
        
        result.put("records", records);
        result.put("total", total);
        result.put("pageNum", pageNum);
        result.put("pageSize", pageSize);
        result.put("totalPages", totalPages);
        
        return result;
    }
    
    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> result = new HashMap<>();
        
        // 获取诊断总数
        int diagnosisCount = diagnosisMapper.count();
        result.put("diagnosisCount", diagnosisCount);
        
        // 获取算法分布数据
        List<AlgorithmCount> algorithmDistribution = diagnosisMapper.countByAlgorithm();
        result.put("algorithmDistribution", algorithmDistribution);
        
        // 获取所有诊断记录用于生成风险等级分布
        List<Diagnosis> diagnosisList = getAllDiagnosis();
        result.put("diagnosisList", diagnosisList);
        
        return result;
    }
    
    // 获取所有诊断记录
    private List<Diagnosis> getAllDiagnosis() {
        try {
            return diagnosisMapper.findAll();
        } catch (Exception e) {
            // 如果发生错误，返回空列表而不是模拟数据
            return new ArrayList<>();
        }
    }
    
    // 获取算法实现
    private DiagnosisAlgorithm getAlgorithm(String algorithmName) {
        if (algorithmName == null || algorithmName.isEmpty()) {
            return mockAlgorithm;
        }
        
        // 根据名称匹配算法
        // 实际项目中应该从Spring容器或数据库中查找算法实现
        // 这里为了简单起见，直接判断名称是否相同
        if (mockAlgorithm.getAlgorithmName().equals(algorithmName)) {
            return mockAlgorithm;
        }
        
        // 如果没有找到匹配的算法，使用默认算法
        return mockAlgorithm;
    }
} 