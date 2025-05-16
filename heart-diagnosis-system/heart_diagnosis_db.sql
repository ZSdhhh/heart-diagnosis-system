/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80022 (8.0.22)
 Source Host           : localhost:3306
 Source Schema         : heart_diagnosis_db

 Target Server Type    : MySQL
 Target Server Version : 80022 (8.0.22)
 File Encoding         : 65001

 Date: 05/05/2025 21:22:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for algorithm
-- ----------------------------
DROP TABLE IF EXISTS `algorithm`;
CREATE TABLE `algorithm` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '算法名称',
  `description` varchar(255) DEFAULT NULL COMMENT '算法描述',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态(0禁用,1启用)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='算法表';

-- ----------------------------
-- Records of algorithm
-- ----------------------------
BEGIN;
INSERT INTO `algorithm` (`id`, `name`, `description`, `status`, `create_time`) VALUES (1, '算法1', '模拟算法实现1', 1, '2025-04-02 10:34:25');
INSERT INTO `algorithm` (`id`, `name`, `description`, `status`, `create_time`) VALUES (2, '算法2', '模拟算法实现2', 1, '2025-04-02 10:34:25');
INSERT INTO `algorithm` (`id`, `name`, `description`, `status`, `create_time`) VALUES (3, '算法3', '基础模拟诊断算法', 1, '2025-04-02 10:34:25');
COMMIT;

-- ----------------------------
-- Table structure for diagnosis
-- ----------------------------
DROP TABLE IF EXISTS `diagnosis`;
CREATE TABLE `diagnosis` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_id` int NOT NULL COMMENT '患者ID',
  `algorithm` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '算法类型',
  `result` float DEFAULT NULL COMMENT '诊断结果(风险概率)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `fk_diagnosis_patient_idx` (`patient_id`),
  CONSTRAINT `fk_diagnosis_patient` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='诊断表';

-- ----------------------------
-- Records of diagnosis
-- ----------------------------
BEGIN;
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (1, 1, '模拟算法1', 0.25, '2024-03-01 09:00:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (2, 1, '模拟算法2', 0.32, '2024-03-01 09:05:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (3, 2, '模拟算法1', 0.68, '2024-03-02 09:45:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (4, 2, '模拟算法2', 0.72, '2024-03-02 09:50:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (5, 3, '模拟算法1', 0.15, '2024-03-03 10:30:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (6, 3, '模拟算法2', 0.18, '2024-03-03 10:35:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (7, 4, '模拟算法1', 0.55, '2024-03-04 12:00:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (8, 4, '模拟算法2', 0.58, '2024-03-04 12:05:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (9, 5, '模拟算法1', 0.85, '2024-03-05 14:30:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (10, 5, '模拟算法2', 0.88, '2024-03-05 14:35:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (11, 6, '模拟算法1', 0.22, '2024-03-06 16:15:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (12, 6, '模拟算法2', 0.25, '2024-03-06 16:20:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (13, 7, '模拟算法1', 0.65, '2024-03-07 10:00:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (14, 7, '模拟算法2', 0.61, '2024-03-07 10:05:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (15, 8, '模拟算法1', 0.45, '2024-03-08 10:45:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (16, 8, '模拟算法2', 0.48, '2024-03-08 10:50:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (17, 9, '模拟算法1', 0.75, '2024-03-09 11:30:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (18, 9, '模拟算法2', 0.79, '2024-03-09 11:35:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (19, 10, '模拟算法1', 0.12, '2024-03-10 14:00:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (20, 10, '模拟算法2', 0.15, '2024-03-10 14:05:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (21, 1, '模拟算法1', 0.2, '2024-04-01 09:00:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (22, 2, '模拟算法1', 0.72, '2024-04-02 09:45:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (23, 5, '模拟算法1', 0.8, '2024-04-05 14:30:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (24, 5, '模拟算法2', 0.82, '2024-04-05 14:35:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (25, 7, '模拟算法1', 0.58, '2024-04-07 10:00:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (26, 9, '模拟算法1', 0.7, '2024-04-09 11:30:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (27, 9, '模拟算法2', 0.72, '2024-04-09 11:35:00');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (28, 1, '模拟诊断算法', 0.113828, '2025-04-02 09:47:21');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (29, 1, '模拟诊断算法', 0.124369, '2025-04-02 09:50:22');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (30, 1, '模拟诊断算法', 0.63038, '2025-04-02 10:22:48');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (40, 4, '算法2', 0.744459, '2025-04-02 11:31:19');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (41, 1, '算法2', 0.502904, '2025-04-02 11:36:55');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (42, 5, '算法1', 0.235536, '2025-04-02 11:45:30');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (43, 1, '算法1', 0.114411, '2025-04-10 23:18:14');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (44, 1, '算法1', 0.494027, '2025-04-10 23:19:47');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (45, 1, '算法3', 0.498598, '2025-04-10 23:19:56');
INSERT INTO `diagnosis` (`id`, `patient_id`, `algorithm`, `result`, `create_time`) VALUES (46, 7, '算法1', 0.552594, '2025-04-10 23:20:05');
COMMIT;

-- ----------------------------
-- Table structure for patient
-- ----------------------------
DROP TABLE IF EXISTS `patient`;
CREATE TABLE `patient` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `age` int DEFAULT NULL COMMENT '年龄',
  `gender` tinyint DEFAULT NULL COMMENT '性别(0女,1男)',
  `blood_pressure` varchar(20) DEFAULT NULL COMMENT '血压',
  `cholesterol` float DEFAULT NULL COMMENT '胆固醇',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='患者表';

-- ----------------------------
-- Records of patient
-- ----------------------------
BEGIN;
INSERT INTO `patient` (`id`, `name`, `age`, `gender`, `blood_pressure`, `cholesterol`, `create_time`, `update_time`) VALUES (1, '张三', 45, 1, '130/85', 5.2, '2024-03-01 08:30:00', '2025-04-02 09:47:13');
INSERT INTO `patient` (`id`, `name`, `age`, `gender`, `blood_pressure`, `cholesterol`, `create_time`, `update_time`) VALUES (2, '李四', 62, 1, '145/95', 6.7, '2024-03-02 09:15:00', '2025-04-02 09:18:37');
INSERT INTO `patient` (`id`, `name`, `age`, `gender`, `blood_pressure`, `cholesterol`, `create_time`, `update_time`) VALUES (3, '王五', 38, 1, '125/80', 4.9, '2024-03-03 10:00:00', '2025-04-02 09:18:37');
INSERT INTO `patient` (`id`, `name`, `age`, `gender`, `blood_pressure`, `cholesterol`, `create_time`, `update_time`) VALUES (4, '赵六', 55, 1, '140/90', 6.1, '2024-03-04 11:30:00', '2025-04-02 09:18:37');
INSERT INTO `patient` (`id`, `name`, `age`, `gender`, `blood_pressure`, `cholesterol`, `create_time`, `update_time`) VALUES (5, '钱七', 70, 1, '160/100', 7.2, '2024-03-05 14:00:00', '2025-04-02 09:18:37');
INSERT INTO `patient` (`id`, `name`, `age`, `gender`, `blood_pressure`, `cholesterol`, `create_time`, `update_time`) VALUES (6, '孙八', 42, 0, '120/80', 5, '2024-03-06 15:45:00', '2025-04-02 09:18:37');
INSERT INTO `patient` (`id`, `name`, `age`, `gender`, `blood_pressure`, `cholesterol`, `create_time`, `update_time`) VALUES (7, '周九', 58, 0, '150/95', 6.5, '2024-03-07 09:30:00', '2025-04-02 09:18:37');
INSERT INTO `patient` (`id`, `name`, `age`, `gender`, `blood_pressure`, `cholesterol`, `create_time`, `update_time`) VALUES (8, '吴十', 49, 0, '135/88', 5.8, '2024-03-08 10:15:00', '2025-04-02 09:18:37');
INSERT INTO `patient` (`id`, `name`, `age`, `gender`, `blood_pressure`, `cholesterol`, `create_time`, `update_time`) VALUES (9, '郑十一', 65, 0, '155/98', 6.9, '2024-03-09 11:00:00', '2025-04-02 09:18:37');
INSERT INTO `patient` (`id`, `name`, `age`, `gender`, `blood_pressure`, `cholesterol`, `create_time`, `update_time`) VALUES (10, '王十二', 36, 0, '118/75', 4.5, '2024-03-10 13:30:00', '2025-04-02 09:18:37');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `role` varchar(10) NOT NULL DEFAULT 'user' COMMENT '角色(admin/user)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `username`, `password`, `role`, `create_time`) VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', '2025-04-02 09:18:10');
INSERT INTO `user` (`id`, `username`, `password`, `role`, `create_time`) VALUES (2, 'doctor1', 'e10adc3949ba59abbe56e057f20f883e', 'user', '2024-01-05 10:30:00');
INSERT INTO `user` (`id`, `username`, `password`, `role`, `create_time`) VALUES (3, 'doctor2', 'e10adc3949ba59abbe56e057f20f883e', 'user', '2024-01-10 14:15:00');
INSERT INTO `user` (`id`, `username`, `password`, `role`, `create_time`) VALUES (4, 'nurse1', 'e10adc3949ba59abbe56e057f20f883e', 'user', '2024-02-01 09:00:00');
INSERT INTO `user` (`id`, `username`, `password`, `role`, `create_time`) VALUES (5, 'reception', 'e10adc3949ba59abbe56e057f20f883e', 'user', '2024-02-15 11:20:00');
INSERT INTO `user` (`id`, `username`, `password`, `role`, `create_time`) VALUES (7, 'test', 'e10adc3949ba59abbe56e057f20f883e', 'user', '2025-04-02 10:01:34');
INSERT INTO `user` (`id`, `username`, `password`, `role`, `create_time`) VALUES (8, 'test1', '96e79218965eb72c92a549dd5a330112', 'user', '2025-04-10 23:17:54');
INSERT INTO `user` (`id`, `username`, `password`, `role`, `create_time`) VALUES (9, '0410ceshi', '81004b3276eb6d874a0c340e674753d5', 'user', '2025-04-10 23:18:48');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
