package com.example.heartdiagnosis.service.impl;

import com.example.heartdiagnosis.entity.Patient;
import com.example.heartdiagnosis.entity.PageResult;
import com.example.heartdiagnosis.mapper.PatientMapper;
import com.example.heartdiagnosis.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;
    
    @Override
    public List<Patient> getAllPatients() {
        return patientMapper.findAll();
    }
    
    @Override
    public PageResult<Patient> getPatientsByPage(int pageNum, int pageSize) {
        // 计算偏移量
        int offset = (pageNum - 1) * pageSize;
        // 查询总数
        int total = patientMapper.count();
        // 分页查询数据
        List<Patient> patients = patientMapper.findByPage(offset, pageSize);
        // 封装结果
        return new PageResult<>(total, patients, pageNum, pageSize);
    }
    
    @Override
    public Patient getPatientById(Integer id) {
        return patientMapper.findById(id);
    }
    
    @Override
    public boolean addPatient(Patient patient) {
        patientMapper.insert(patient);
        return patient.getId() != null;
    }
    
    @Override
    public boolean updatePatient(Patient patient) {
        patientMapper.update(patient);
        return true;
    }
    
    @Override
    public boolean deletePatient(Integer id) {
        patientMapper.deleteById(id);
        return true;
    }
} 