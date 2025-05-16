package com.example.heartdiagnosis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.heartdiagnosis.mapper")
public class HeartDiagnosisApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeartDiagnosisApplication.class, args);
    }

} 