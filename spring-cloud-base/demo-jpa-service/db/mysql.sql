/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-05-21 10:58:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class` (
  `pk_class_id` varchar(255) NOT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_class_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_class
-- ----------------------------
INSERT INTO `t_class` VALUES ('1', '1B');
INSERT INTO `t_class` VALUES ('2', '2B');
INSERT INTO `t_class` VALUES ('3', '3B');
INSERT INTO `t_class` VALUES ('4', '4B');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `pk_role_id` varchar(124) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_role_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_student
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `pk_student_id` varchar(255) NOT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `last_modify_time` bigint(20) DEFAULT NULL,
  `student_name` varchar(255) DEFAULT NULL,
  `fk_class_id` varchar(255) DEFAULT NULL,
  `fk_id_card` varchar(255) DEFAULT NULL,
  `age` int(16) DEFAULT NULL,
  `gender` tinyint(16) DEFAULT NULL,
  PRIMARY KEY (`pk_student_id`),
  UNIQUE KEY `UK_omgovq1h8ru2d7prim9vrh195` (`fk_id_card`),
  KEY `FKm7w7npcqobpwo726q6nrgkp0o` (`fk_class_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------

-- ----------------------------
-- Table structure for t_student_idcard
-- ----------------------------
DROP TABLE IF EXISTS `t_student_idcard`;
CREATE TABLE `t_student_idcard` (
  `pk_student_idcard_id` varchar(255) NOT NULL,
  `student_num` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_student_idcard_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student_idcard
-- ----------------------------

-- ----------------------------
-- Table structure for t_student_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_student_teacher`;
CREATE TABLE `t_student_teacher` (
  `pk_student_id` varchar(255) NOT NULL,
  `pk_teacher_id` varchar(255) NOT NULL,
  KEY `FKiwbqr0utxbl9jsqr0wg2py930` (`pk_teacher_id`),
  KEY `FKf7gxhr5grtfwgfkmbjld4lq7` (`pk_student_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student_teacher
-- ----------------------------

-- ----------------------------
-- Table structure for t_teacher
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher` (
  `pk_teacher_id` varchar(255) NOT NULL,
  `teacher_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pk_teacher_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_teacher
-- ----------------------------


-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `pk_id` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_name` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`pk_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


INSERT INTO `t_teacher` VALUES ('1', '张老师');
INSERT INTO `t_teacher` VALUES ('2', '李老师');
INSERT INTO `t_teacher` VALUES ('3', '王老师');
INSERT INTO `t_teacher` VALUES ('4', '赵老师');
-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '1234', 'admin');
