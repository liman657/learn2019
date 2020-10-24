/*
Navicat MariaDB Data Transfer

Source Server         : .
Source Server Version : 100122
Source Host           : localhost:3306
Source Database       : muxin-dev

Target Server Type    : MariaDB
Target Server Version : 100122
File Encoding         : 65001

Date: 2018-07-07 15:39:42
*/
CREATE DATABASE db_manxin_netty;
USE db_manxin_netty;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chat_msg
-- ----------------------------
DROP TABLE IF EXISTS `t_chat_msg`;
CREATE TABLE `t_chat_msg` (
  `id` VARCHAR(64) NOT NULL,
  `send_user_id` VARCHAR(64) NOT NULL,
  `accept_user_id` VARCHAR(64) NOT NULL,
  `msg` VARCHAR(255) NOT NULL,
  `sign_flag` INT(1) NOT NULL COMMENT '消息是否签收状态\r\n1：签收\r\n0：未签收\r\n',
  `create_time` DATETIME NOT NULL COMMENT '发送请求的事件',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of chat_msg
-- ----------------------------

-- ----------------------------
-- Table structure for friends_request
-- ----------------------------
DROP TABLE IF EXISTS `t_friends_request`;
CREATE TABLE `t_friends_request` (
  `id` VARCHAR(64) NOT NULL,
  `send_user_id` VARCHAR(64) NOT NULL,
  `accept_user_id` VARCHAR(64) NOT NULL,
  `request_date_time` DATETIME NOT NULL COMMENT '发送请求的事件',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of friends_request
-- ----------------------------

-- ----------------------------
-- Table structure for my_friends
-- ----------------------------
DROP TABLE IF EXISTS `t_my_friends`;
CREATE TABLE `t_my_friends` (
  `id` VARCHAR(64) NOT NULL,
  `my_user_id` VARCHAR(64) NOT NULL COMMENT '用户id',
  `my_friend_user_id` VARCHAR(64) NOT NULL COMMENT '用户的好友id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `my_user_id` (`my_user_id`,`my_friend_user_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of my_friends
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `t_users`;
CREATE TABLE `t_users` (
  `id` VARCHAR(64) NOT NULL,
  `username` VARCHAR(20) NOT NULL COMMENT '用户名，账号，慕信号',
  `password` VARCHAR(64) NOT NULL COMMENT '密码',
  `face_image` VARCHAR(255) NOT NULL COMMENT '我的头像，如果没有默认给一张',
  `face_image_big` VARCHAR(255) NOT NULL,
  `nickname` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `qrcode` VARCHAR(255) NOT NULL COMMENT '新用户注册后默认后台生成二维码，并且上传到fastdfs',
  `cid` VARCHAR(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of users
-- ----------------------------