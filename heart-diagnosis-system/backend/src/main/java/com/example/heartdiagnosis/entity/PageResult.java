package com.example.heartdiagnosis.entity;

import java.util.List;

/**
 * 分页结果封装类
 */
public class PageResult<T> {
    private long total;       // 总记录数
    private List<T> records;  // 当前页数据列表
    private int pageNum;      // 当前页码
    private int pageSize;     // 每页记录数
    
    public PageResult() {
    }
    
    public PageResult(long total, List<T> records, int pageNum, int pageSize) {
        this.total = total;
        this.records = records;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }
    
    public long getTotal() {
        return total;
    }
    
    public void setTotal(long total) {
        this.total = total;
    }
    
    public List<T> getRecords() {
        return records;
    }
    
    public void setRecords(List<T> records) {
        this.records = records;
    }
    
    public int getPageNum() {
        return pageNum;
    }
    
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
} 