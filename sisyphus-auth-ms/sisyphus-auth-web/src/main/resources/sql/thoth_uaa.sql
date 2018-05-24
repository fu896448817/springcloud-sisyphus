/*
Navicat MySQL Data Transfer

Source Server         : 10.127.138.2
Source Server Version : 50623
Source Host           : 10.127.138.2:3306
Source Database       : thoth_uaa

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2017-11-27 11:25:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_thoth_uaa_authority
-- ----------------------------
DROP TABLE IF EXISTS `tbl_thoth_uaa_authority`;
CREATE TABLE `tbl_thoth_uaa_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_thoth_uaa_authority
-- ----------------------------
INSERT INTO `tbl_thoth_uaa_authority` VALUES ('2', 'wyf', '0000-00-00 00:00:00', null, '254355\000\005sr\000\015java.time.Ser225]204272\033\"H26', '查看demo', 'query-demo');

-- ----------------------------
-- Table structure for tbl_thoth_uaa_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_thoth_uaa_role`;
CREATE TABLE `tbl_thoth_uaa_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_thoth_uaa_role
-- ----------------------------
INSERT INTO `tbl_thoth_uaa_role` VALUES ('3', 'wyf', '0000-00-00 00:00:00', null, '254355\000\005sr\000\015java.time.Ser225]204272\033\"H26', '管理员', 'ROLE_ADMIN');
INSERT INTO `tbl_thoth_uaa_role` VALUES ('4', 'wyf', '0000-00-00 00:00:00', null, '254355\000\005sr\000\015java.time.Ser225]204272\033\"H26', '普通用户', 'ROLE_USER');

-- ----------------------------
-- Table structure for tbl_thoth_uaa_role_authorities
-- ----------------------------
DROP TABLE IF EXISTS `tbl_thoth_uaa_role_authorities`;
CREATE TABLE `tbl_thoth_uaa_role_authorities` (
  `uaa_role_id` bigint(20) NOT NULL,
  `authorities_id` bigint(20) NOT NULL,
  PRIMARY KEY (`uaa_role_id`,`authorities_id`),
  KEY `FKr2tyo2yrn7pdviq1sskqv02ne` (`authorities_id`),
  CONSTRAINT `FKeygil35ef7c3qtr4jtntnjqqd` FOREIGN KEY (`uaa_role_id`) REFERENCES `tbl_thoth_uaa_role` (`id`),
  CONSTRAINT `FKr2tyo2yrn7pdviq1sskqv02ne` FOREIGN KEY (`authorities_id`) REFERENCES `tbl_thoth_uaa_authority` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_thoth_uaa_role_authorities
-- ----------------------------
INSERT INTO `tbl_thoth_uaa_role_authorities` VALUES ('3', '2');

-- ----------------------------
-- Table structure for tbl_thoth_uaa_tenant
-- ----------------------------
DROP TABLE IF EXISTS `tbl_thoth_uaa_tenant`;
CREATE TABLE `tbl_thoth_uaa_tenant` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `contacts` varchar(255) DEFAULT NULL,
  `domain` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `weixin` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_thoth_uaa_tenant
-- ----------------------------
INSERT INTO `tbl_thoth_uaa_tenant` VALUES ('1001', '', null, null, null, null, null, null, 'cyou', null, null, null, 'hummel_acm');

-- ----------------------------
-- Table structure for tbl_thoth_uaa_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_thoth_uaa_user`;
CREATE TABLE `tbl_thoth_uaa_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_by` varchar(50) NOT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `password` varchar(60) NOT NULL,
  `tenant_id` bigint(20) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bne3owvotdn9lcqhamnray949` (`username`),
  UNIQUE KEY `UK_8ua038pu8g8h7jlqfvrgphivq` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_thoth_uaa_user
-- ----------------------------
INSERT INTO `tbl_thoth_uaa_user` VALUES ('5', 'wyf', '0000-00-00 00:00:00', null, '254355\000\005sr\000\015java.time.Ser225]204272\033\"H26', null, null, null, null, '$2a$10$XOVs4Z1YtPKqKwQVywG9j.xLAqXYRQLGZMGMrZDNbtl6pUC0Weteq', '0', '1001');

-- ----------------------------
-- Table structure for tbl_thoth_uaa_user_roles
-- ----------------------------
DROP TABLE IF EXISTS `tbl_thoth_uaa_user_roles`;
CREATE TABLE `tbl_thoth_uaa_user_roles` (
  `uaa_user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  PRIMARY KEY (`uaa_user_id`,`roles_id`),
  KEY `FKtral7230k8i8yeuy14n6p535a` (`roles_id`),
  CONSTRAINT `FKauuybhim3drowqbqppyygubly` FOREIGN KEY (`uaa_user_id`) REFERENCES `tbl_thoth_uaa_user` (`id`),
  CONSTRAINT `FKtral7230k8i8yeuy14n6p535a` FOREIGN KEY (`roles_id`) REFERENCES `tbl_thoth_uaa_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_thoth_uaa_user_roles
-- ----------------------------
INSERT INTO `tbl_thoth_uaa_user_roles` VALUES ('5', '3');
