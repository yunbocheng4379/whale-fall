/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80016
 Source Host           : localhost:3306
 Source Schema         : whale

 Target Server Type    : MySQL
 Target Server Version : 80016
 File Encoding         : 65001

 Date: 26/02/2025 21:59:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for operate_log
-- ----------------------------
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `source` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '日志来源',
  `operate_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作名称',
  `operate_info` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作信息',
  `operator` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作人',
  `operate_status` tinyint(1) NULL DEFAULT NULL COMMENT '操作状态',
  `fail_cause` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失败原因',
  `batch_id` int(11) NULL DEFAULT NULL COMMENT '批处理ID',
  `operate_time` datetime(0) NOT NULL COMMENT '操作时间',
  `batch_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否批量操作',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2837 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
  `flag` tinyint(1) NULL DEFAULT NULL COMMENT '用户状态(0: 删除，1: 正常)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, '程云博', '$2a$10$P/S2NXwl4QstLR5zHECfMebeEx44h/mtHw/2dxxGg2nHTUGGxfkmO', 1);

SET FOREIGN_KEY_CHECKS = 1;
