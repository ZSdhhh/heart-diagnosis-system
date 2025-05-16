package com.example.heartdiagnosis.mapper;

import com.example.heartdiagnosis.entity.Algorithm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AlgorithmMapper {
    
    @Select("SELECT * FROM algorithm WHERE status = 1")
    List<Algorithm> findAllEnabled();
    
    @Select("SELECT * FROM algorithm")
    List<Algorithm> findAll();
    
    @Insert("INSERT INTO algorithm(name, description, status) VALUES(#{name}, #{description}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Algorithm algorithm);
    
    @Update("UPDATE algorithm SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);
} 