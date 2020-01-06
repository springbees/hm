/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : hm

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 06/01/2020 23:50:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `css` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `href` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` tinyint(1) NOT NULL,
  `permission` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, 0, '用户管理', 'fa-users', '', 1, '', 1);
INSERT INTO `sys_permission` VALUES (2, 1, '用户查询', 'fa-user', '/api/getPage?pageName=admin/admin-list', 1, '', 2);
INSERT INTO `sys_permission` VALUES (3, 2, '查询', '', '', 2, 'sys:user:query', 100);
INSERT INTO `sys_permission` VALUES (4, 2, '新增', '', '', 2, 'sys:user:add', 100);
INSERT INTO `sys_permission` VALUES (5, 2, '删除', NULL, NULL, 2, 'sys:user:del', 100);
INSERT INTO `sys_permission` VALUES (6, 1, '修改密码', 'fa-pencil-square-o', '/api/getPage?pageName=admin/admin-change-password', 1, 'sys:user:password', 4);
INSERT INTO `sys_permission` VALUES (7, 0, '系统设置', 'fa-gears', '', 1, '', 5);
INSERT INTO `sys_permission` VALUES (8, 7, '菜单', 'fa-cog', '/api/getPage?pageName=permission/permission-list', 1, '', 6);
INSERT INTO `sys_permission` VALUES (9, 8, '查询', '', '', 2, 'sys:menu:query', 100);
INSERT INTO `sys_permission` VALUES (10, 8, '新增', '', '', 2, 'sys:menu:add', 100);
INSERT INTO `sys_permission` VALUES (11, 8, '删除', '', '', 2, 'sys:menu:del', 100);
INSERT INTO `sys_permission` VALUES (12, 7, '角色', 'fa-user-secret', '/api/getPage?pageName=role/role-list', 1, '', 7);
INSERT INTO `sys_permission` VALUES (13, 12, '查询', '', '', 2, 'sys:role:query', 100);
INSERT INTO `sys_permission` VALUES (14, 12, '新增', '', '', 2, 'sys:role:add', 100);
INSERT INTO `sys_permission` VALUES (15, 12, '删除', '', '', 2, 'sys:role:del', 100);
INSERT INTO `sys_permission` VALUES (16, 0, '文件管理', 'fa-folder-open', '/api/getPage?pageName=file/file-list', 1, '', 8);
INSERT INTO `sys_permission` VALUES (17, 16, '查询', '', '', 2, 'sys:file:query', 100);
INSERT INTO `sys_permission` VALUES (18, 16, '删除', '', '', 2, 'sys:file:del', 100);
INSERT INTO `sys_permission` VALUES (19, 0, '数据源监控', 'fa-eye', 'druid/index.html', 1, '', 9);
INSERT INTO `sys_permission` VALUES (20, 0, '接口swagger', 'fa-file-pdf-o', 'swagger-ui.html', 1, '', 10);
INSERT INTO `sys_permission` VALUES (21, 0, '代码生成', 'fa-wrench', '/api/getPage?pageName=generate/edit', 1, 'generate:edit', 11);
INSERT INTO `sys_permission` VALUES (22, 0, '日志查询', 'fa-reorder', '/api/getPage?pageName=log/log-list', 1, 'sys:log:query', 13);
INSERT INTO `sys_permission` VALUES (23, 8, '修改', NULL, NULL, 2, 'sys:menu:edit', 100);
INSERT INTO `sys_permission` VALUES (24, 12, '修改', NULL, NULL, 2, 'sys:role:edit', 100);
INSERT INTO `sys_permission` VALUES (25, 2, '修改', NULL, NULL, 2, 'sys:user:edit', 100);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `createTime` datetime(0) NULL,
  `updateTime` datetime(0) NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'ADMIN', '管理员', '2017-05-01 13:25:39', '2020-01-05 15:06:36');
INSERT INTO `sys_role` VALUES (2, 'USER', '普通用户', '2017-08-01 21:47:31', '2020-01-06 12:12:17');
INSERT INTO `sys_role` VALUES (16, 'CLIENT', '客户', '2020-01-06 12:08:28', '2020-01-06 12:25:10');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `roleId` int(11) NOT NULL,
  `permissionId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`, `permissionId`) USING BTREE,
  INDEX `fk_sysrolepermission_permissionId`(`permissionId`) USING BTREE,
  CONSTRAINT `sys_role_permission_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `sys_role_permission_ibfk_2` FOREIGN KEY (`permissionId`) REFERENCES `sys_permission` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (2, 1);
INSERT INTO `sys_role_permission` VALUES (16, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (2, 2);
INSERT INTO `sys_role_permission` VALUES (16, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (2, 3);
INSERT INTO `sys_role_permission` VALUES (16, 3);
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (16, 4);
INSERT INTO `sys_role_permission` VALUES (1, 5);
INSERT INTO `sys_role_permission` VALUES (16, 5);
INSERT INTO `sys_role_permission` VALUES (1, 6);
INSERT INTO `sys_role_permission` VALUES (2, 6);
INSERT INTO `sys_role_permission` VALUES (16, 6);
INSERT INTO `sys_role_permission` VALUES (1, 7);
INSERT INTO `sys_role_permission` VALUES (1, 8);
INSERT INTO `sys_role_permission` VALUES (1, 9);
INSERT INTO `sys_role_permission` VALUES (1, 10);
INSERT INTO `sys_role_permission` VALUES (1, 11);
INSERT INTO `sys_role_permission` VALUES (1, 12);
INSERT INTO `sys_role_permission` VALUES (1, 13);
INSERT INTO `sys_role_permission` VALUES (1, 14);
INSERT INTO `sys_role_permission` VALUES (1, 15);
INSERT INTO `sys_role_permission` VALUES (1, 16);
INSERT INTO `sys_role_permission` VALUES (1, 17);
INSERT INTO `sys_role_permission` VALUES (1, 18);
INSERT INTO `sys_role_permission` VALUES (1, 19);
INSERT INTO `sys_role_permission` VALUES (1, 20);
INSERT INTO `sys_role_permission` VALUES (2, 20);
INSERT INTO `sys_role_permission` VALUES (1, 21);
INSERT INTO `sys_role_permission` VALUES (2, 21);
INSERT INTO `sys_role_permission` VALUES (1, 23);
INSERT INTO `sys_role_permission` VALUES (1, 24);
INSERT INTO `sys_role_permission` VALUES (1, 25);
INSERT INTO `sys_role_permission` VALUES (16, 25);

-- ----------------------------
-- Table structure for sys_role_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_user`;
CREATE TABLE `sys_role_user`  (
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`userId`, `roleId`) USING BTREE,
  INDEX `fk_roleId`(`roleId`) USING BTREE,
  CONSTRAINT `sys_role_user_ibfk_1` FOREIGN KEY (`roleId`) REFERENCES `sys_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `sys_role_user_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `sys_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_user
-- ----------------------------
INSERT INTO `sys_role_user` VALUES (1, 1);
INSERT INTO `sys_role_user` VALUES (2, 2);
INSERT INTO `sys_role_user` VALUES (46, 16);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `headImgUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `telephone` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `sex` tinyint(1) NULL DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT 1,
  `createTime` datetime(0) NULL,
  `updateTime` datetime(0) NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$AOHaubV/568lZuyhaCnDfORLUuc/1DIRrzqaybBODLRw1APzw932y', '管理员', 'http://file.hm.com/hm-Image/2020/01/04/202001040857220783475.jpg', NULL, '158784879852', '12@qq.com', '2020-01-05', 0, 1, '2019-04-08 00:20:51', '2019-04-08 00:20:51');
INSERT INTO `sys_user` VALUES (2, 'user', '$2a$10$ILWAB4ZOoRr2pXqarliI6uAuL7Q/7dAMTpWO9p7dyVSHHO7zQMTeW', '普通用户', 'http://file.hm.com/hm-Image/2020/01/04/202001040913240187953.jpg', NULL, '1111111111', '11@qq.com', '2019-03-31', 1, 1, '2019-04-09 06:44:50', '2019-04-09 06:44:50');
INSERT INTO `sys_user` VALUES (46, 'client', '$2a$10$Y.CCmWfiUm1AJXnitAP1DuKf/uW28kkxfgYWzErBJrZzgxRcEtxbe', '客户', 'http://file.hm.com/hm-Image/2020/01/06/202001060824380075980.jpg', NULL, '13479978780', '1347@qq.com', '2020-01-05', NULL, 1, '2020-01-06 20:24:40', '2020-01-06 20:24:40');

SET FOREIGN_KEY_CHECKS = 1;
