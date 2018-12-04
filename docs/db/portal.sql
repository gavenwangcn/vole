SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dept_name` varchar(100) NOT NULL COMMENT '部门名称',
  `dept_desc` varchar(300) DEFAULT NULL COMMENT '描述',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (1, '架构部', 'JGDept', '2017-12-21 11:06:45', '2018-11-20 11:20:48');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(50) NOT NULL COMMENT '用户',
  `title` varchar(300) DEFAULT NULL COMMENT '日志',
  `url` varchar(300) DEFAULT NULL COMMENT '地址',
  `params` text COMMENT '参数',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` int(20) NOT NULL COMMENT '父级菜单ID',
  `url` varchar(255) DEFAULT NULL COMMENT '连接地址',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `deep` int(11) DEFAULT NULL COMMENT '深度',
  `code` varchar(50) DEFAULT NULL COMMENT '编码',
  `resource` varchar(50) DEFAULT NULL COMMENT '资源名称',
  `del_flag` int(1) DEFAULT '0' COMMENT '0--正常 1--删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '菜单管理', 0, NULL, 'fa-folder', 1, 1, '01', NULL, 0, '2018-11-11 20:38:42', NULL);
INSERT INTO `sys_menu` VALUES (2, '菜单列表', 1, '/admin/menu/list/1', 'fa-file', 1, 2, '0101', NULL, 0, '2018-11-11 20:39:20', NULL);
INSERT INTO `sys_menu` VALUES (4, '部门管理', 0, NULL, 'fa-folder', 1, 1, '02', NULL, 0, '2018-11-16 16:18:51', NULL);
INSERT INTO `sys_menu` VALUES (5, '部门列表', 4, '/admin/dept/list/1', 'fa-file', 1, 2, '0201', NULL, 0, '2018-11-16 16:20:01', NULL);
INSERT INTO `sys_menu` VALUES (6, '角色管理', 0, NULL, 'fa-folder', 1, 1, '03', NULL, 0, '2018-11-16 16:21:33', NULL);
INSERT INTO `sys_menu` VALUES (7, '角色列表', 6, '/admin/role/list/1', 'fa-file', 1, 2, '0301', NULL, 0, '2018-11-16 16:22:56', NULL);
INSERT INTO `sys_menu` VALUES (8, '系统信息管理', 0, NULL, 'fa-folder', 1, 1, '04', NULL, 0, '2018-11-16 16:24:24', NULL);
INSERT INTO `sys_menu` VALUES (9, '系统信息列表', 8, '/admin/setting/list/1', 'fa-file', 1, 2, '0401', NULL, 0, '2018-11-16 16:25:18', NULL);
INSERT INTO `sys_menu` VALUES (10, '用户管理', 0, NULL, 'fa-folder', 1, 1, '05', NULL, 0, '2018-11-16 16:31:37', NULL);
INSERT INTO `sys_menu` VALUES (11, '用户列表', 10, '/admin/user/list/1', 'fa-file', 1, 2, '0501', NULL, 0, '2018-11-16 16:32:51', NULL);
INSERT INTO `sys_menu` VALUES (12, '会员管理', 0, NULL, 'fa-folder', 1, 1, '06', NULL, 0, '2018-11-22 16:55:43', NULL);
INSERT INTO `sys_menu` VALUES (13, '会员列表', 12, '/mps/member/list/1', 'fa-file', 1, 2, '0601', NULL, 0, '2018-11-22 16:56:34', NULL);
INSERT INTO `sys_menu` VALUES (14, '会员角色管理', 0, NULL, 'fa-folder', 1, 1, '07', NULL, 0, '2018-11-22 16:57:11', NULL);
INSERT INTO `sys_menu` VALUES (16, '会员权限管理', 0, NULL, 'fa-folder', 1, 1, '08', NULL, 0, '2018-11-22 16:58:56', NULL);
INSERT INTO `sys_menu` VALUES (17, '会员权限列表', 16, '/mps/permission/list/1', 'fa-file', 1, 2, '0801', NULL, 0, '2018-11-22 16:59:41', NULL);
INSERT INTO `sys_menu` VALUES (18, '会员客户端管理', 0, NULL, 'fa-folder', 1, 1, '09', NULL, 0, '2018-11-22 17:00:30', NULL);
INSERT INTO `sys_menu` VALUES (19, '会员客户端列表', 18, '/mps/client/list/1', 'fa-file', 1, 2, '0901', NULL, 0, '2018-11-22 17:02:47', NULL);
INSERT INTO `sys_menu` VALUES (20, '会员角色列表', 14, '/mps/role/list/1', 'fa-file', 1, 2, '0701', NULL, 0, '2018-11-22 17:14:18', NULL);
INSERT INTO `sys_menu` VALUES (22, '菜单所有权限', 2, NULL, NULL, 1, 3, '010101', '/admin/menu/**', 0, '2018-11-23 17:33:49', '2018-11-23 18:01:49');
INSERT INTO `sys_menu` VALUES (23, '部门所有权限', 5, NULL, NULL, 1, 3, '020101', '/admin/dept/**', 0, '2018-11-23 17:39:53', '2018-11-23 18:01:56');
INSERT INTO `sys_menu` VALUES (24, '角色所有权限', 7, NULL, NULL, 1, 3, '030101', '/admin/role/**', 0, '2018-11-23 17:42:20', '2018-11-23 18:05:14');
INSERT INTO `sys_menu` VALUES (25, '系统管理所有权限', 9, NULL, NULL, 1, 3, '040101', '/admin/setting/**', 0, '2018-11-23 17:43:29', '2018-11-23 18:02:03');
INSERT INTO `sys_menu` VALUES (26, '用户管理所有权限', 11, NULL, NULL, 1, 3, '050101', '/admin/user/**', 0, '2018-11-23 17:44:15', '2018-11-23 18:02:17');
INSERT INTO `sys_menu` VALUES (27, '会员管理所有权限', 13, NULL, NULL, 1, 3, '060101', '/mps/member/**', 0, '2018-11-23 17:46:17', '2018-11-23 18:02:28');
INSERT INTO `sys_menu` VALUES (28, '会员角色管理所有权限', 20, NULL, NULL, 1, 3, '070101', '/mps/role/**', 0, '2018-11-23 17:47:21', '2018-11-23 18:02:34');
INSERT INTO `sys_menu` VALUES (29, '会员权限管理所有权限', 17, NULL, NULL, 1, 3, '080101', '/mps/permission/**', 0, '2018-11-23 17:48:19', '2018-11-23 18:02:40');
INSERT INTO `sys_menu` VALUES (30, '会员客户端管理所有权限', 19, NULL, NULL, 1, 3, '090101', '/mps/client/**', 0, '2018-11-23 17:49:08', '2018-11-23 18:02:48');
INSERT INTO `sys_menu` VALUES (31, '路由管理', 0, NULL, 'fa-folder', 1, 1, '10', NULL, 0, '2018-11-27 17:25:55', NULL);
INSERT INTO `sys_menu` VALUES (32, '路由列表', 31, '/mps/route/list/1', 'fa-file', 1, 2, '1001', NULL, 0, '2018-11-27 17:26:57', NULL);
INSERT INTO `sys_menu` VALUES (33, '路由管理所有权限', 32, NULL, NULL, 1, 3, '100101', '/mps/route/**', 0, '2018-11-27 17:27:52', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_name` varchar(64) NOT NULL,
  `role_code` varchar(64) NOT NULL,
  `role_desc` varchar(255) DEFAULT NULL,
  `del_flag` int(1) DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_idx1_role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (15, '菜单管理', 'MENU_ADMIN', '菜单管理所有权限', 0, '2018-11-11 20:41:53', '2018-11-16 16:27:25');
INSERT INTO `sys_role` VALUES (16, '部门管理', 'DEPT_ADMIN', '部门管理所有权限', 0, '2018-11-16 16:26:26', NULL);
INSERT INTO `sys_role` VALUES (17, '角色管理', 'ROLE_ADMIN', '角色管理所有权限', 0, '2018-11-16 16:27:13', NULL);
INSERT INTO `sys_role` VALUES (18, '用户管理', 'USER_ADMIN', '用户管理所有权限', 0, '2018-11-16 16:28:00', NULL);
INSERT INTO `sys_role` VALUES (19, '系统管理', 'SYS_ADMIN', '系统信息管理所有权限', 0, '2018-11-16 16:28:41', NULL);
INSERT INTO `sys_role` VALUES (20, '会员系统管理', 'MPS_ADMIN', '会员系统管理所有权限', 0, '2018-11-22 17:11:58', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` int(20) NOT NULL COMMENT '角色主键',
  `menu_id` int(20) NOT NULL COMMENT '菜单主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='角色菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (37, 19, 8);
INSERT INTO `sys_role_menu` VALUES (38, 19, 9);
INSERT INTO `sys_role_menu` VALUES (39, 19, 25);
INSERT INTO `sys_role_menu` VALUES (40, 18, 10);
INSERT INTO `sys_role_menu` VALUES (41, 18, 11);
INSERT INTO `sys_role_menu` VALUES (42, 18, 26);
INSERT INTO `sys_role_menu` VALUES (43, 17, 6);
INSERT INTO `sys_role_menu` VALUES (44, 17, 7);
INSERT INTO `sys_role_menu` VALUES (45, 17, 24);
INSERT INTO `sys_role_menu` VALUES (46, 16, 4);
INSERT INTO `sys_role_menu` VALUES (47, 16, 5);
INSERT INTO `sys_role_menu` VALUES (48, 16, 23);
INSERT INTO `sys_role_menu` VALUES (49, 15, 1);
INSERT INTO `sys_role_menu` VALUES (50, 15, 2);
INSERT INTO `sys_role_menu` VALUES (51, 15, 22);
INSERT INTO `sys_role_menu` VALUES (52, 20, 12);
INSERT INTO `sys_role_menu` VALUES (53, 20, 13);
INSERT INTO `sys_role_menu` VALUES (54, 20, 27);
INSERT INTO `sys_role_menu` VALUES (55, 20, 14);
INSERT INTO `sys_role_menu` VALUES (56, 20, 20);
INSERT INTO `sys_role_menu` VALUES (57, 20, 28);
INSERT INTO `sys_role_menu` VALUES (58, 20, 16);
INSERT INTO `sys_role_menu` VALUES (59, 20, 17);
INSERT INTO `sys_role_menu` VALUES (60, 20, 29);
INSERT INTO `sys_role_menu` VALUES (61, 20, 18);
INSERT INTO `sys_role_menu` VALUES (62, 20, 19);
INSERT INTO `sys_role_menu` VALUES (63, 20, 30);
INSERT INTO `sys_role_menu` VALUES (64, 20, 31);
INSERT INTO `sys_role_menu` VALUES (65, 20, 32);
INSERT INTO `sys_role_menu` VALUES (66, 20, 33);
COMMIT;

-- ----------------------------
-- Table structure for sys_setting
-- ----------------------------
DROP TABLE IF EXISTS `sys_setting`;
CREATE TABLE `sys_setting` (
  `sys_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sys_name` varchar(200) NOT NULL COMMENT '名称',
  `sys_sub_name` varchar(50) NOT NULL COMMENT '简称',
  `sys_global_key` varchar(50) NOT NULL COMMENT '全局系统主键',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sys_id`),
  UNIQUE KEY `sys_idx1_global` (`sys_global_key`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统设置表';

-- ----------------------------
-- Records of sys_setting
-- ----------------------------
BEGIN;
INSERT INTO `sys_setting` VALUES (1, '统一用户管理平台', 'PT', 'vole-portal', 1, '2017-12-21 11:06:45', '2018-11-16 16:34:58');
INSERT INTO `sys_setting` VALUES (2, '抢购系统', 'QG', 'vole-qianggou', 0, '2018-11-16 12:45:04', NULL);
INSERT INTO `sys_setting` VALUES (3, '验证码中心', 'IDC', 'vole-identity', 0, '2018-11-16 12:47:03', NULL);
INSERT INTO `sys_setting` VALUES (4, '会员管理平台', 'MPS', 'vole-mps', 0, '2018-11-20 16:35:35', '2018-11-27 17:22:48');
COMMIT;

-- ----------------------------
-- Table structure for sys_setting_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_setting_menu`;
CREATE TABLE `sys_setting_menu` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sys_id` int(20) NOT NULL COMMENT '系统主键',
  `menu_id` int(20) NOT NULL COMMENT '菜单主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='系统菜单关联表';

-- ----------------------------
-- Records of sys_setting_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_setting_menu` VALUES (1, 1, 1);
INSERT INTO `sys_setting_menu` VALUES (2, 1, 2);
INSERT INTO `sys_setting_menu` VALUES (3, 1, 4);
INSERT INTO `sys_setting_menu` VALUES (4, 1, 5);
INSERT INTO `sys_setting_menu` VALUES (5, 1, 6);
INSERT INTO `sys_setting_menu` VALUES (6, 1, 7);
INSERT INTO `sys_setting_menu` VALUES (7, 1, 8);
INSERT INTO `sys_setting_menu` VALUES (8, 1, 9);
INSERT INTO `sys_setting_menu` VALUES (9, 1, 10);
INSERT INTO `sys_setting_menu` VALUES (10, 1, 11);
INSERT INTO `sys_setting_menu` VALUES (15, 4, 12);
INSERT INTO `sys_setting_menu` VALUES (16, 4, 14);
INSERT INTO `sys_setting_menu` VALUES (17, 4, 16);
INSERT INTO `sys_setting_menu` VALUES (18, 4, 18);
INSERT INTO `sys_setting_menu` VALUES (19, 4, 31);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `loginname` varchar(64) NOT NULL COMMENT '登陆名',
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL COMMENT '简介',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `dept_id` int(20) DEFAULT NULL COMMENT '部门主键',
  `del_flag` int(1) DEFAULT '0' COMMENT '0-正常，1-删除',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_idx1_loginname` (`loginname`),
  UNIQUE KEY `user_idx3_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (5, 'xialaokou', '夏老抠', '{bcrypt}$2a$10$DPoi6hKJ/OiKuLnCzL1zWuHeGXaThdmlHLjC02ByEN7oILGE165rS', '18512582269', NULL, 1, 0, '2018-11-12 11:11:17', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_Id` int(20) NOT NULL COMMENT '用户主键',
  `role_Id` int(20) NOT NULL COMMENT '角色主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (7, 5, 15);
INSERT INTO `sys_user_role` VALUES (8, 5, 16);
INSERT INTO `sys_user_role` VALUES (9, 5, 17);
INSERT INTO `sys_user_role` VALUES (10, 5, 18);
INSERT INTO `sys_user_role` VALUES (11, 5, 19);
INSERT INTO `sys_user_role` VALUES (12, 5, 20);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
