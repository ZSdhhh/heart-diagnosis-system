package com.example.heartdiagnosis.entity;

/**
 * 算法统计数据模型
 */
public class AlgorithmCount {
    private String algorithm;  // 算法名称
    private Integer count;     // 算法使用次数
    
    public String getAlgorithm() {
        return algorithm;
    }
    
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
} 