package com.example.heartdiagnosis.controller;

import com.example.heartdiagnosis.entity.Patient;
import com.example.heartdiagnosis.entity.PageResult;
import com.example.heartdiagnosis.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientController {

    @Autowired
    private PatientService patientService;
    
    @GetMapping
    public Map<String, Object> getAllPatients() {
        Map<String, Object> result = new HashMap<>();
        List<Patient> patients = patientService.getAllPatients();
        
        result.put("code", 200);
        result.put("message", "获取成功");
        result.put("data", patients);
        
        return result;
    }
    
    @GetMapping("/page")
    public Map<String, Object> getPatientsByPage(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        Map<String, Object> result = new HashMap<>();
        PageResult<Patient> pageResult = patientService.getPatientsByPage(pageNum, pageSize);
        
        result.put("code", 200);
        result.put("message", "获取成功");
        result.put("data", pageResult);
        
        return result;
    }
    
    @GetMapping("/{id}")
    public Map<String, Object> getPatientById(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        Patient patient = patientService.getPatientById(id);
        
        if (patient != null) {
            result.put("code", 200);
            result.put("message", "获取成功");
            result.put("data", patient);
        } else {
            result.put("code", 404);
            result.put("message", "患者不存在");
        }
        
        return result;
    }
    
    @PostMapping
    public Map<String, Object> addPatient(@RequestBody Patient patient) {
        Map<String, Object> result = new HashMap<>();
        boolean success = patientService.addPatient(patient);
        
        if (success) {
            result.put("code", 200);
            result.put("message", "添加成功");
            result.put("data", patient);
        } else {
            result.put("code", 400);
            result.put("message", "添加失败");
        }
        
        return result;
    }
    
    @PutMapping("/{id}")
    public Map<String, Object> updatePatient(@PathVariable Integer id, @RequestBody Patient patient) {
        Map<String, Object> result = new HashMap<>();
        patient.setId(id);
        boolean success = patientService.updatePatient(patient);
        
        if (success) {
            result.put("code", 200);
            result.put("message", "更新成功");
        } else {
            result.put("code", 400);
            result.put("message", "更新失败");
        }
        
        return result;
    }
    
    @DeleteMapping("/{id}")
    public Map<String, Object> deletePatient(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        boolean success = patientService.deletePatient(id);
        
        if (success) {
            result.put("code", 200);
            result.put("message", "删除成功");
        } else {
            result.put("code", 400);
            result.put("message", "删除失败");
        }
        
        return result;
    }
} 