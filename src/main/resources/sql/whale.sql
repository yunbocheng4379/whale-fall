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

 Date: 02/03/2025 18:35:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for app_menu
-- ----------------------------
DROP TABLE IF EXISTS `app_menu`;
CREATE TABLE `app_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '系统菜单ID',
  `menu_name` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名',
  `menu_rank` int(11) NULL DEFAULT NULL COMMENT '菜单排名',
  `menu_flag` tinyint(1) NULL DEFAULT 1 COMMENT '菜单状态(0: 删除,1: 正常)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `app_menu_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_menu
-- ----------------------------
INSERT INTO `app_menu` VALUES (1, '测试路由', 1, 1, '2025-03-02 18:17:57', '2025-03-02 18:18:00');

-- ----------------------------
-- Table structure for app_node
-- ----------------------------
DROP TABLE IF EXISTS `app_node`;
CREATE TABLE `app_node`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单节点ID',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '父菜单ID',
  `node_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '节点名称',
  `node_route` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '节点前端路由',
  `node_rank` int(11) NULL DEFAULT NULL COMMENT '节点排名',
  `node_flag` tinyint(1) NULL DEFAULT NULL COMMENT '节点状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `app_node_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_node
-- ----------------------------
INSERT INTO `app_node` VALUES (1, 1, '测试路由一', '/demo/demo1', 1, 1, '2025-03-02 18:18:46', '2025-03-02 18:18:47');
INSERT INTO `app_node` VALUES (2, 1, '测试路由二', '/demo/demo2', 2, 1, '2025-03-02 18:19:15', '2025-03-02 18:19:16');
INSERT INTO `app_node` VALUES (3, 1, '测试路由三', '/demo/demo3', 3, 1, '2025-03-02 18:19:32', '2025-03-02 18:19:33');

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
-- Table structure for role_app_auth
-- ----------------------------
DROP TABLE IF EXISTS `role_app_auth`;
CREATE TABLE `role_app_auth`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '用户角色ID',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `role_app_auth_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色与菜单权限对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_app_auth
-- ----------------------------
INSERT INTO `role_app_auth` VALUES (1, 1, 1);
INSERT INTO `role_app_auth` VALUES (2, 1, 2);
INSERT INTO `role_app_auth` VALUES (3, 1, 3);
INSERT INTO `role_app_auth` VALUES (4, 2, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_role` int(11) NULL DEFAULT NULL COMMENT '用户角色ID',
  `flag` tinyint(1) NULL DEFAULT NULL COMMENT '用户状态(0: 删除，1: 正常)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '用户创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '用户修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, '程云博', '$2a$10$P/S2NXwl4QstLR5zHECfMebeEx44h/mtHw/2dxxGg2nHTUGGxfkmO', 1, 1, '2025-03-02 17:57:24', '2025-03-02 17:57:26');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户角色主键ID',
  `role_name` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色类型名称',
  `role_flag` tinyint(1) NULL DEFAULT 1 COMMENT '用户角色状态(0: 删除,1: 正常)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '用户角色创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '用户角色修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_role_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, '管理员', 1, '2025-03-02 17:56:51', '2025-03-02 17:56:52');
INSERT INTO `user_role` VALUES (2, '普通用户', 1, '2025-03-02 17:57:10', '2025-03-02 17:57:13');

SET FOREIGN_KEY_CHECKS = 1;
