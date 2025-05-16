package com.example.heartdiagnosis.service;

import com.example.heartdiagnosis.entity.Patient;
import com.example.heartdiagnosis.entity.PageResult;

import java.util.List;

public interface PatientService {
    
    List<Patient> getAllPatients();
    
    PageResult<Patient> getPatientsByPage(int pageNum, int pageSize);
    
    Patient getPatientById(Integer id);
    
    boolean addPatient(Patient patient);
    
    boolean updatePatient(Patient patient);
    
    boolean deletePatient(Integer id);
} 