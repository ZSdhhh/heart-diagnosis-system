package com.example.heartdiagnosis.mapper;

import com.example.heartdiagnosis.entity.AlgorithmCount;
import com.example.heartdiagnosis.entity.Diagnosis;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiagnosisMapper {
    
    @Results(id = "diagnosisMap", value = {
        @Result(property = "id", column = "id"),
        @Result(property = "patientId", column = "patient_id"),
        @Result(property = "algorithm", column = "algorithm"),
        @Result(property = "result", column = "result"),
        @Result(property = "createTime", column = "create_time")
    })
    @Select("SELECT * FROM diagnosis WHERE patient_id = #{patientId} ORDER BY create_time DESC")
    List<Diagnosis> findByPatientId(Integer patientId);
    
    @ResultMap("diagnosisMap")
    @Select("SELECT * FROM diagnosis WHERE patient_id = #{patientId} ORDER BY create_time DESC LIMIT #{offset}, #{limit}")
    List<Diagnosis> findByPatientIdWithPaging(@Param("patientId") Integer patientId, 
                                              @Param("offset") int offset, 
                                              @Param("limit") int limit);
    
    @Select("SELECT COUNT(*) FROM diagnosis WHERE patient_id = #{patientId}")
    int countByPatientId(Integer patientId);
    
    @Insert("INSERT INTO diagnosis(patient_id, algorithm, result, create_time) VALUES(#{patientId}, #{algorithm}, #{result}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Diagnosis diagnosis);
    
    @Select("SELECT COUNT(*) FROM diagnosis")
    int count();
    
    @Select("SELECT algorithm, COUNT(*) as count FROM diagnosis GROUP BY algorithm")
    @Results({
        @Result(property = "algorithm", column = "algorithm"),
        @Result(property = "count", column = "count")
    })
    List<AlgorithmCount> countByAlgorithm();
    
    @ResultMap("diagnosisMap")
    @Select("SELECT * FROM diagnosis ORDER BY create_time DESC")
    List<Diagnosis> findAll();
} 