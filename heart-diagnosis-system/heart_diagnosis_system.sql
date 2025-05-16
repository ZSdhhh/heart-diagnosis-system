-- 创建数据库
CREATE DATABASE IF NOT EXISTS heart_diagnosis_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE heart_diagnosis_db;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `role` VARCHAR(10) NOT NULL DEFAULT 'user' COMMENT '角色(admin/user)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 患者表
CREATE TABLE IF NOT EXISTS `patient` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '姓名',
  `age` INT NULL COMMENT '年龄',
  `gender` TINYINT NULL COMMENT '性别(0女,1男)',
  `blood_pressure` VARCHAR(20) NULL COMMENT '血压',
  `cholesterol` FLOAT NULL COMMENT '胆固醇',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='患者表';

-- 算法表
CREATE TABLE IF NOT EXISTS `algorithm` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL COMMENT '算法名称',
  `description` VARCHAR(255) NULL COMMENT '算法描述',
  `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态(0禁用,1启用)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='算法表';

-- 诊断表
CREATE TABLE IF NOT EXISTS `diagnosis` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `patient_id` INT NOT NULL COMMENT '患者ID',
  `algorithm` VARCHAR(50) NULL COMMENT '算法类型',
  `result` FLOAT NULL COMMENT '诊断结果(风险概率)',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  INDEX `fk_diagnosis_patient_idx` (`patient_id` ASC),
  CONSTRAINT `fk_diagnosis_patient`
    FOREIGN KEY (`patient_id`)
    REFERENCES `patient` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='诊断表';


-- 插入管理员账户
INSERT INTO `user` (`username`, `password`, `role`) VALUES
('admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin'); -- 密码: 123456 (MD5加密) 