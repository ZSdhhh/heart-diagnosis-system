package com.example.heartdiagnosis.controller;

import com.example.heartdiagnosis.entity.Diagnosis;
import com.example.heartdiagnosis.service.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;
    
    @PostMapping("/diagnosis")
    public Map<String, Object> diagnose(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        
        Integer patientId = (Integer) params.get("patientId");
        String algorithmName = (String) params.get("algorithm");
        
        if (patientId == null) {
            result.put("code", 400);
            result.put("message", "患者ID不能为空");
            return result;
        }
        
        // 记录收到的原始算法名称，用于调试
        System.out.println("收到算法名称: [" + algorithmName + "]");
        
        Float diagnosisResult = diagnosisService.diagnose(patientId, algorithmName);
        
        if (diagnosisResult != null) {
            result.put("code", 200);
            result.put("message", "诊断成功");
            result.put("data", diagnosisResult);
        } else {
            result.put("code", 400);
            result.put("message", "诊断失败，患者不存在");
        }
        
        return result;
    }
    
    @GetMapping("/diagnosis/patient/{patientId}")
    public Map<String, Object> getDiagnosisByPatientId(@PathVariable Integer patientId) {
        Map<String, Object> result = new HashMap<>();
        
        List<Diagnosis> diagnosisList = diagnosisService.getDiagnosisByPatientId(patientId);
        
        result.put("code", 200);
        result.put("message", "获取成功");
        result.put("data", diagnosisList);
        
        return result;
    }
    
    @GetMapping("/diagnosis/patient/{patientId}/page")
    public Map<String, Object> getDiagnosisByPatientIdWithPaging(
            @PathVariable Integer patientId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        
        Map<String, Object> result = new HashMap<>();
        
        Map<String, Object> pageData = diagnosisService.getDiagnosisByPatientIdWithPaging(patientId, pageNum, pageSize);
        
        result.put("code", 200);
        result.put("message", "获取成功");
        result.put("data", pageData);
        
        return result;
    }
    
    @GetMapping("/statistics")
    public Map<String, Object> getStatistics() {
        Map<String, Object> result = new HashMap<>();
        
        Map<String, Object> statistics = diagnosisService.getStatistics();
        
        result.put("code", 200);
        result.put("message", "获取成功");
        result.put("data", statistics);
        
        return result;
    }
} 