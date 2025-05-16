package com.example.heartdiagnosis.controller;

import com.example.heartdiagnosis.entity.Algorithm;
import com.example.heartdiagnosis.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/algorithms")
@CrossOrigin
public class AlgorithmController {

    @Autowired
    private AlgorithmService algorithmService;
    
    @GetMapping
    public Map<String, Object> getAllAlgorithms() {
        Map<String, Object> result = new HashMap<>();
        List<Algorithm> algorithms = algorithmService.getAllAlgorithms();
        
        result.put("code", 200);
        result.put("message", "获取成功");
        result.put("data", algorithms);
        
        return result;
    }
    
    @GetMapping("/enabled")
    public Map<String, Object> getAllEnabledAlgorithms() {
        Map<String, Object> result = new HashMap<>();
        List<Algorithm> algorithms = algorithmService.getAllEnabledAlgorithms();
        
        result.put("code", 200);
        result.put("message", "获取成功");
        result.put("data", algorithms);
        
        return result;
    }
    
    @PostMapping
    public Map<String, Object> addAlgorithm(@RequestBody Algorithm algorithm) {
        Map<String, Object> result = new HashMap<>();
        boolean success = algorithmService.addAlgorithm(algorithm);
        
        if (success) {
            result.put("code", 200);
            result.put("message", "添加成功");
        } else {
            result.put("code", 400);
            result.put("message", "添加失败");
        }
        
        return result;
    }
    
    @PutMapping("/{id}/status")
    public Map<String, Object> updateAlgorithmStatus(@PathVariable Integer id, @RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        Integer status = (Integer) params.get("status");
        
        if (status == null) {
            result.put("code", 400);
            result.put("message", "状态不能为空");
            return result;
        }
        
        boolean success = algorithmService.updateAlgorithmStatus(id, status);
        
        if (success) {
            result.put("code", 200);
            result.put("message", "更新成功");
        } else {
            result.put("code", 400);
            result.put("message", "更新失败");
        }
        
        return result;
    }
} 