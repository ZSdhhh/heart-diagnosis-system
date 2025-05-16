package com.example.heartdiagnosis.mapper;

import com.example.heartdiagnosis.entity.Patient;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PatientMapper {
    
    @Results(id = "patientMap", value = {
        @Result(property = "id", column = "id"),
        @Result(property = "name", column = "name"),
        @Result(property = "age", column = "age"),
        @Result(property = "gender", column = "gender"),
        @Result(property = "bloodPressure", column = "blood_pressure"),
        @Result(property = "cholesterol", column = "cholesterol"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time")
    })
    @Select("SELECT * FROM patient")
    List<Patient> findAll();
    
    @ResultMap("patientMap")
    @Select("SELECT * FROM patient LIMIT #{offset}, #{limit}")
    List<Patient> findByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    @Select("SELECT COUNT(*) FROM patient")
    int count();
    
    @ResultMap("patientMap")
    @Select("SELECT * FROM patient WHERE id = #{id}")
    Patient findById(Integer id);
    
    @Insert("INSERT INTO patient(name, age, gender, blood_pressure, cholesterol, create_time, update_time) " +
            "VALUES (#{name}, #{age}, #{gender}, #{bloodPressure}, #{cholesterol}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Patient patient);
    
    @Update("UPDATE patient SET name = #{name}, age = #{age}, gender = #{gender}, " +
            "blood_pressure = #{bloodPressure}, cholesterol = #{cholesterol}, update_time = NOW() " +
            "WHERE id = #{id}")
    void update(Patient patient);
    
    @Delete("DELETE FROM patient WHERE id = #{id}")
    void deleteById(Integer id);
} 