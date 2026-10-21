/*
 Navicat Premium Data Transfer

 Source Server         : mysql-root
 Source Server Type    : MySQL
 Source Server Version : 80019
 Source Host           : localhost:3306
 Source Schema         : workspace

 Target Server Type    : MySQL
 Target Server Version : 80019
 File Encoding         : 65001

 Date: 22/02/2023 14:55:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for game_user
-- ----------------------------
DROP TABLE IF EXISTS `game_user`;
CREATE TABLE `game_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `combat_capacity` double(255, 0) NULL DEFAULT NULL,
  `level` int(0) NULL DEFAULT 1,
  `empirical_value` double(255, 0) NULL DEFAULT 0,
  `blood` double(255, 0) NULL DEFAULT 100,
  `physical_attack` double(255, 0) NULL DEFAULT 10,
  `magic_attak` double(255, 0) NULL DEFAULT 0,
  `physical_defend` double(255, 0) NULL DEFAULT 0,
  `magic_defend` double(255, 0) NULL DEFAULT 0,
  `tenacity` double(255, 0) NULL DEFAULT 0,
  `auto_recovery` double(255, 0) NULL DEFAULT 0,
  `feeding` int(0) NULL DEFAULT NULL,
  `kill_count` double(255, 0) NULL DEFAULT NULL,
  `checkpoint` int(0) NULL DEFAULT NULL,
  `equipments_head_uuid` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipments_body_uuid` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipments_foot_uuid` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipments_weapon_uuid` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `equipments_broach_uuid` varchar(0) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of game_user
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_account` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nick_name` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(63) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `vip` int(4) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  `reg_date` datetime(0) NOT NULL,
  `v_date` datetime(0) NOT NULL,
  `version` int(0) UNSIGNED NOT NULL DEFAULT 0,
  `uuid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user (removed: sample data may contain personal information)
-- ----------------------------

-- ----------------------------
-- Table structure for user_log
-- ----------------------------
DROP TABLE IF EXISTS `user_log`;
CREATE TABLE `user_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `method` tinyint(1) NOT NULL,
  `login_date` datetime(0) NOT NULL,
  `logout_date` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
