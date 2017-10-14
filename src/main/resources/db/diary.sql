/*
Navicat MySQL Data Transfer

Source Server         : 45.62.116.168(板瓦工)
Source Server Version : 50552
Source Host           : 45.62.116.168:3306
Source Database       : diary

Target Server Type    : MYSQL
Target Server Version : 50552
File Encoding         : 65001

Date: 2017-07-23 01:21:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for diary
-- ----------------------------
DROP TABLE IF EXISTS `diary`;
CREATE TABLE `diary` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `title` varchar(200) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `create_on` datetime DEFAULT NULL COMMENT '创建时间',
  `create_by` int(11) DEFAULT NULL COMMENT '创建人',
  `modify_on` datetime DEFAULT NULL COMMENT '修改时间',
  `weather` varchar(200) DEFAULT NULL COMMENT '天气',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of diary
-- ----------------------------
INSERT INTO `diary` VALUES ('1', '表情', '（ \'▿ \' ）', '2017-07-17 23:44:08', '1', '2017-07-17 23:44:08', '杭州:晴,低温 28℃,高温 38℃');

-- ----------------------------
-- Table structure for privilege
-- ----------------------------
DROP TABLE IF EXISTS `privilege`;
CREATE TABLE `privilege` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `user_id` int(11) DEFAULT NULL COMMENT '用户的id',
  `refer_id` int(11) DEFAULT NULL COMMENT '授权的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privilege
-- ----------------------------
INSERT INTO `privilege` VALUES ('1', '1', '2');


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) DEFAULT NULL,
  `login_password` varchar(100) DEFAULT NULL,
  `user_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'yzy', '65829e542dd151f443cc997270c61e78c042a82d687cc13844bf2c1813714600', '小杨');
INSERT INTO `user` VALUES ('2', 'test', '65829e542dd151f443cc997270c61e78c042a82d687cc13844bf2c1813714600', '小泰');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名',
  `message` varchar(255) DEFAULT NULL COMMENT '日志信息',
  `date_time` varchar(255) DEFAULT NULL COMMENT '时间',
  `ip` varchar(255) DEFAULT NULL COMMENT 'ip地址',
  `method_name` varchar(255) DEFAULT NULL COMMENT '执行方法名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;