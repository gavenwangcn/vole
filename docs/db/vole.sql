SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for JOB_EXECUTION_LOG
-- ----------------------------
DROP TABLE IF EXISTS `JOB_EXECUTION_LOG`;
CREATE TABLE `JOB_EXECUTION_LOG` (
  `id` varchar(40) NOT NULL,
  `job_name` varchar(100) NOT NULL,
  `task_id` varchar(255) NOT NULL,
  `hostname` varchar(255) NOT NULL,
  `ip` varchar(50) NOT NULL,
  `sharding_item` int(11) NOT NULL,
  `execution_source` varchar(20) NOT NULL,
  `failure_cause` varchar(4000) DEFAULT NULL,
  `is_success` int(11) NOT NULL,
  `start_time` timestamp NULL DEFAULT NULL,
  `complete_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for JOB_STATUS_TRACE_LOG
-- ----------------------------
DROP TABLE IF EXISTS `JOB_STATUS_TRACE_LOG`;
CREATE TABLE `JOB_STATUS_TRACE_LOG` (
  `id` varchar(40) NOT NULL,
  `job_name` varchar(100) NOT NULL,
  `original_task_id` varchar(255) NOT NULL,
  `task_id` varchar(255) NOT NULL,
  `slave_id` varchar(50) NOT NULL,
  `source` varchar(50) NOT NULL,
  `execution_type` varchar(20) NOT NULL,
  `sharding_item` varchar(100) NOT NULL,
  `state` varchar(20) NOT NULL,
  `message` varchar(4000) DEFAULT NULL,
  `creation_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `TASK_ID_STATE_INDEX` (`task_id`,`state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for member_0
-- ----------------------------
DROP TABLE IF EXISTS `member_0`;
CREATE TABLE `member_0` (
  `member_id` bigint(64) NOT NULL COMMENT '主键ID',
  `membername` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '简介',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '0-正常，1-删除',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `member_idx1_membername` (`membername`),
  UNIQUE KEY `member_idx2_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of member_0
-- ----------------------------
BEGIN;
INSERT INTO `member_0` VALUES (1067629916525072386, 'vole', '{bcrypt}$2a$10$YCnmdlVnNsXdxR8t7glNeO5lPlDpbEijuFHrkcT13KcbqXsEVUqAC', '18512582267', NULL, '2018-11-28 12:02:52', NULL, '0');
COMMIT;

-- ----------------------------
-- Table structure for member_1
-- ----------------------------
DROP TABLE IF EXISTS `member_1`;
CREATE TABLE `member_1` (
  `member_id` bigint(64) NOT NULL COMMENT '主键ID',
  `membername` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '简介',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '头像',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '0-正常，1-删除',
  PRIMARY KEY (`member_id`),
  UNIQUE KEY `member_idx1_membername` (`membername`),
  UNIQUE KEY `member_idx2_membername` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Table structure for member_role_0
-- ----------------------------
DROP TABLE IF EXISTS `member_role_0`;
CREATE TABLE `member_role_0` (
  `id` bigint(64) NOT NULL COMMENT 'Id',
  `member_id` bigint(64) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_role_idx1` (`member_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='会员角色表';

-- ----------------------------
-- Table structure for member_role_1
-- ----------------------------
DROP TABLE IF EXISTS `member_role_1`;
CREATE TABLE `member_role_1` (
  `id` bigint(64) NOT NULL COMMENT 'Id',
  `member_id` bigint(64) NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_role_idx1` (`member_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='会员角色表';

-- ----------------------------
-- Records of member_role_1
-- ----------------------------
BEGIN;
INSERT INTO `member_role_1` VALUES (1067723141915058177, 1067629916525072386, 1);
COMMIT;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(40) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `oauth_client_idx1` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES (1, 'vole', NULL, '{bcrypt}$2a$10$zqgaMTf2VSHXjIV28CrID.fgN1bGd73VTwLqvmTqHNak79nrRNz7W', 'server', 'client,password', NULL, NULL, NULL, NULL, NULL, 'true');
COMMIT;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  `permission` varchar(32) NOT NULL COMMENT '权限标识',
  `url` varchar(128) DEFAULT NULL COMMENT '请求链接',
  `method` varchar(32) DEFAULT NULL COMMENT '请求方法',
  `description` varchar(128) DEFAULT NULL COMMENT '权限描述',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (3, 'DEMO_ADMIN', '/demo/**', '*', 'demo所有权限', '2017-11-08 09:57:27', '2018-12-03 15:20:52');
INSERT INTO `permission` VALUES (4, 'MPS_ADMIN', '/mps/**', '*', 'mps系统所有权限', '2017-11-08 10:13:37', '2018-12-03 15:21:41');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `role_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_idx1_role_code` (`role_code`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, 'admin', 'ROLE_ADMIN', '超级管理员', '2017-10-29 15:45:51', '2018-04-22 11:40:29', '0');
INSERT INTO `role` VALUES (14, 'user', 'ROLE_USER', '普通用户', '2018-04-20 07:14:32', '2018-04-21 23:35:22', '0');
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `permission_id` int(11) NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_permission_idx1` (`role_id`,`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='角色权限表';

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (2, 1, 3);
INSERT INTO `role_permission` VALUES (3, 1, 4);
COMMIT;

-- ----------------------------
-- Table structure for sys_log_0
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_0`;
CREATE TABLE `sys_log_0` (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='日志表';

-- ----------------------------
-- Table structure for sys_log_1
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_1`;
CREATE TABLE `sys_log_1` (
  `id` bigint(64) NOT NULL COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`create_by`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='日志表';


-- ----------------------------
-- Table structure for sys_zuul_route
-- ----------------------------
DROP TABLE IF EXISTS `sys_zuul_route`;
CREATE TABLE `sys_zuul_route` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'router Id',
  `path` varchar(255) NOT NULL COMMENT '路由路径',
  `service_id` varchar(255) NOT NULL COMMENT '服务名称',
  `url` varchar(255) DEFAULT NULL COMMENT 'url代理',
  `strip_prefix` char(1) DEFAULT '1' COMMENT '转发去掉前缀',
  `retryable` char(1) DEFAULT '1' COMMENT '是否重试',
  `enabled` char(1) DEFAULT '1' COMMENT '是否启用',
  `sensitiveHeaders_list` varchar(255) DEFAULT NULL COMMENT '敏感请求头',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标识（0-正常,1-删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='动态路由配置表';

-- ----------------------------
-- Records of sys_zuul_route
-- ----------------------------
BEGIN;
INSERT INTO `sys_zuul_route` VALUES (3, '/demo/**', 'vole-demo', '', '1', '1', '1', '', '2018-05-17 14:09:06', '2018-12-03 15:15:59', '0');
INSERT INTO `sys_zuul_route` VALUES (4, '/mps/**', 'vole-mps', '', '1', '1', '1', '', '2018-05-21 11:40:38', '2018-12-03 15:16:16', '0');
INSERT INTO `sys_zuul_route` VALUES (5, '/auth/**', 'vole-auth', '', '1', '1', '1', '', '2018-05-21 11:41:08', '2018-10-29 21:25:46', '0');
COMMIT;

-- ----------------------------
-- Table structure for zipkin_annotations
-- ----------------------------
DROP TABLE IF EXISTS `zipkin_annotations`;
CREATE TABLE `zipkin_annotations` (
  `trace_id_high` bigint(20) NOT NULL DEFAULT '0' COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` bigint(20) NOT NULL COMMENT 'coincides with zipkin_spans.trace_id',
  `span_id` bigint(20) NOT NULL COMMENT 'coincides with zipkin_spans.id',
  `a_key` varchar(255) NOT NULL COMMENT 'BinaryAnnotation.key or Annotation.value if type == -1',
  `a_value` blob COMMENT 'BinaryAnnotation.value(), which must be smaller than 64KB',
  `a_type` int(11) NOT NULL COMMENT 'BinaryAnnotation.type() or -1 if Annotation',
  `a_timestamp` bigint(20) DEFAULT NULL COMMENT 'Used to implement TTL; Annotation.timestamp or zipkin_spans.timestamp',
  `endpoint_ipv4` int(11) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_ipv6` binary(16) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null, or no IPv6 address',
  `endpoint_port` smallint(6) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  `endpoint_service_name` varchar(255) DEFAULT NULL COMMENT 'Null when Binary/Annotation.endpoint is null',
  UNIQUE KEY `trace_id_high` (`trace_id_high`,`trace_id`,`span_id`,`a_key`,`a_timestamp`) COMMENT 'Ignore insert on duplicate',
  UNIQUE KEY `trace_id_high_4` (`trace_id_high`,`trace_id`,`span_id`,`a_key`,`a_timestamp`) COMMENT 'Ignore insert on duplicate',
  KEY `trace_id_high_2` (`trace_id_high`,`trace_id`,`span_id`) COMMENT 'for joining with zipkin_spans',
  KEY `trace_id_high_3` (`trace_id_high`,`trace_id`) COMMENT 'for getTraces/ByIds',
  KEY `endpoint_service_name` (`endpoint_service_name`) COMMENT 'for getTraces and getServiceNames',
  KEY `a_type` (`a_type`) COMMENT 'for getTraces',
  KEY `a_key` (`a_key`) COMMENT 'for getTraces',
  KEY `trace_id` (`trace_id`,`span_id`,`a_key`) COMMENT 'for dependencies job',
  KEY `trace_id_high_5` (`trace_id_high`,`trace_id`,`span_id`) COMMENT 'for joining with zipkin_spans',
  KEY `trace_id_high_6` (`trace_id_high`,`trace_id`) COMMENT 'for getTraces/ByIds',
  KEY `endpoint_service_name_2` (`endpoint_service_name`) COMMENT 'for getTraces and getServiceNames',
  KEY `a_type_2` (`a_type`) COMMENT 'for getTraces',
  KEY `a_key_2` (`a_key`) COMMENT 'for getTraces',
  KEY `trace_id_2` (`trace_id`,`span_id`,`a_key`) COMMENT 'for dependencies job'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;

-- ----------------------------
-- Table structure for zipkin_dependencies
-- ----------------------------
DROP TABLE IF EXISTS `zipkin_dependencies`;
CREATE TABLE `zipkin_dependencies` (
  `day` date NOT NULL,
  `parent` varchar(255) NOT NULL,
  `child` varchar(255) NOT NULL,
  `call_count` bigint(20) DEFAULT NULL,
  UNIQUE KEY `day` (`day`,`parent`,`child`),
  UNIQUE KEY `day_2` (`day`,`parent`,`child`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;

-- ----------------------------
-- Table structure for zipkin_spans
-- ----------------------------
DROP TABLE IF EXISTS `zipkin_spans`;
CREATE TABLE `zipkin_spans` (
  `trace_id_high` bigint(20) NOT NULL DEFAULT '0' COMMENT 'If non zero, this means the trace uses 128 bit traceIds instead of 64 bit',
  `trace_id` bigint(20) NOT NULL,
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `debug` bit(1) DEFAULT NULL,
  `start_ts` bigint(20) DEFAULT NULL COMMENT 'Span.timestamp(): epoch micros used for endTs query and to implement TTL',
  `duration` bigint(20) DEFAULT NULL COMMENT 'Span.duration(): micros used for minDuration and maxDuration query',
  UNIQUE KEY `trace_id_high` (`trace_id_high`,`trace_id`,`id`) COMMENT 'ignore insert on duplicate',
  UNIQUE KEY `trace_id_high_4` (`trace_id_high`,`trace_id`,`id`) COMMENT 'ignore insert on duplicate',
  KEY `trace_id_high_2` (`trace_id_high`,`trace_id`,`id`) COMMENT 'for joining with zipkin_annotations',
  KEY `trace_id_high_3` (`trace_id_high`,`trace_id`) COMMENT 'for getTracesByIds',
  KEY `name` (`name`) COMMENT 'for getTraces and getSpanNames',
  KEY `start_ts` (`start_ts`) COMMENT 'for getTraces ordering and range',
  KEY `trace_id_high_5` (`trace_id_high`,`trace_id`,`id`) COMMENT 'for joining with zipkin_annotations',
  KEY `trace_id_high_6` (`trace_id_high`,`trace_id`) COMMENT 'for getTracesByIds',
  KEY `name_2` (`name`) COMMENT 'for getTraces and getSpanNames',
  KEY `start_ts_2` (`start_ts`) COMMENT 'for getTraces ordering and range'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPRESSED;

SET FOREIGN_KEY_CHECKS = 1;
