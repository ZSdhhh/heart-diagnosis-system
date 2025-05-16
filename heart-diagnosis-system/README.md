# 心脏病辅助诊断系统

本系统是一个基于SpringBoot和Vue3的心脏病辅助诊断系统，提供患者管理、心脏病诊断和数据统计功能。

## 技术栈

### 后端
- SpringBoot 2.7.0
- MyBatis
- MySQL 8.0
- Java 11

### 前端
- Vue 3
- Element Plus
- ECharts
- Vue Router
- Vuex

## 功能介绍

1. **用户管理**：注册、登录功能
2. **患者管理**：添加、编辑、删除患者信息
3. **心脏病诊断**：基于患者数据进行心脏病风险评估
4. **数据统计**：患者统计、诊断结果统计和可视化

## 系统架构

- 采用前后端分离架构
- 后端提供RESTful API接口
- 前端通过HTTP请求与后端交互
- 预留算法接口便于后续扩展

## 开发环境搭建

### 数据库

1. 创建MySQL数据库
```sql
CREATE DATABASE heart_diagnosis_db;
```

2. 导入数据库脚本
```bash
mysql -u root -p heart_diagnosis_db < heart_diagnosis_system.sql
```

### 后端

1. 进入后端目录
```bash
cd heart-diagnosis-system/backend
mvn clean package -DskipTests && java -jar target/heart-diagnosis-0.0.1-SNAPSHOT.jar
```

### 前端

1. 进入前端目录
```bash
cd heart-diagnosis-system/frontend
npm install
npm run serve
```

## 默认账号

系统内置了一个管理员账号：
- 用户名：admin
- 密码：123456