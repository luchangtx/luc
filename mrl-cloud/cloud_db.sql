/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2020/6/28 15:57:53                           */
/*==============================================================*/

drop table if exists `dn_user`;
drop table if exists `dn_role`;
drop table if exists `dn_menu`;
drop table if exists `dn_user_role`;
drop table if exists `dn_role_menu`;

/*==============================================================*/
/* Table: `dn_user`                                             */
/*==============================================================*/
CREATE TABLE `dn_user`  (
  `USER_ID` varchar(36) NOT NULL COMMENT '用户ID',
  `USERNAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `PASSWORD` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `DEPT_ID` varchar(36) NULL DEFAULT NULL COMMENT '部门ID',
  `EMAIL` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `MOBILE` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `SEX` TINYINT(4) DEFAULT NULL COMMENT '性别 0保密 1男 2女',
  `AVATAR` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `OPENID` varchar(36) DEFAULT NULL COMMENT '小程序用户ID 小程序用户唯一标识',
  `LAST_LOGIN_TIME` datetime(0) NULL DEFAULT NULL COMMENT '最近访问时间',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间 ',
  `LAST_CHANGED` datetime DEFAULT NULL COMMENT '最后修改时间 ',
  `STATUS` smallint(2) DEFAULT NULL COMMENT '记录状态 1=正常，-1=已删除',
  PRIMARY KEY (`USER_ID`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;


/*==============================================================*/
/* Table: `dn_role`                                             */
/*==============================================================*/
CREATE TABLE `dn_role`  (
  `ROLE_ID` varchar(36) NOT NULL COMMENT '角色ID',
  `ROLE_CODE` varchar(64) DEFAULT NULL COMMENT '角色值 区分特殊角色',
  `ROLE_NAME` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `REMARK` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间 ',
  `LAST_CHANGED` datetime DEFAULT NULL COMMENT '最后修改时间 ',
  `STATUS` smallint(2) DEFAULT NULL COMMENT '记录状态 1=正常，-1=已删除',
  PRIMARY KEY (`ROLE_ID`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

/*==============================================================*/
/* Table: `dn_menu`                                             */
/*==============================================================*/
CREATE TABLE `dn_menu`  (
  `MENU_ID` varchar(36) NOT NULL COMMENT '菜单/按钮ID',
  `PARENT_ID` varchar(36) COMMENT '上级菜单ID',
  `MENU_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单/按钮名称',
  `PATH` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应路由path',
  `COMPONENT` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应路由组件component',
  `PERMS` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `ICON` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `TYPE` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型 0菜单 1按钮',
  `ORDER_NUM` double(20, 0) NULL DEFAULT NULL COMMENT '排序',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间 ',
  `LAST_CHANGED` datetime DEFAULT NULL COMMENT '最后修改时间 ',
  `STATUS` smallint(2) DEFAULT NULL COMMENT '记录状态 1=正常，-1=已删除',
  PRIMARY KEY (`MENU_ID`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

/*==============================================================*/
/* Table: `dn_user_role`                                        */
/*==============================================================*/
CREATE TABLE `dn_user_role`  (
  `USER_ROLE_ID` varchar(36) NOT NULL COMMENT '用户角色主键',
  `USER_ID` varchar(36) NOT NULL COMMENT '用户ID',
  `ROLE_ID` varchar(36) NOT NULL COMMENT '角色ID',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间 ',
  `LAST_CHANGED` datetime DEFAULT NULL COMMENT '最后修改时间 ',
  `STATUS` smallint(2) DEFAULT NULL COMMENT '记录状态 1=正常，-1=已删除',
   PRIMARY KEY (`USER_ROLE_ID`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

/*==============================================================*/
/* Table: `dn_role_menu`                                        */
/*==============================================================*/
CREATE TABLE `dn_role_menu`  (
  `ROLE_MENU_ID` varchar(36) NOT NULL COMMENT '角色菜单主键',
  `ROLE_ID` varchar(36) NOT NULL COMMENT '角色ID',
  `MENU_ID` varchar(36) NOT NULL COMMENT '菜单ID',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间 ',
  `LAST_CHANGED` datetime DEFAULT NULL COMMENT '最后修改时间 ',
  `STATUS` smallint(2) DEFAULT NULL COMMENT '记录状态 1=正常，-1=已删除',
   PRIMARY KEY (`ROLE_MENU_ID`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

/*==============================================================*/
/* Table: `dn_dept`                                             */
/*==============================================================*/
CREATE TABLE `dn_dept`  (
  `DEPT_ID` varchar(36) NOT NULL COMMENT '部门ID',
  `PARENT_ID` varchar(36) COMMENT '上级部门ID',
  `DEPT_NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `ORDER_NUM` double(20, 0) NULL DEFAULT NULL COMMENT '排序',
  `INSERT_TIME` datetime DEFAULT NULL COMMENT '插入时间 ',
  `LAST_CHANGED` datetime DEFAULT NULL COMMENT '最后修改时间 ',
  `STATUS` smallint(2) DEFAULT NULL COMMENT '记录状态 1=正常，-1=已删除',
  PRIMARY KEY (`DEPT_ID`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;


/*==============================================================*/
/* 插入数据                                                     */
/*------------------------`dn_user`-----------------------------*/
INSERT INTO `dn_user` VALUES (1, 'MrBird','MrBird', '$2a$10$gzhiUb1ldc1Rf3lka4k/WOoFKKGPepHSzJxzcPSN5/65SzkMdc.SK', 1, 'mrbird@qq.com', '17788888888', '1','default.jpg',null, '2019-06-14 20:39:22', '2019-07-19 10:18:36', '2019-08-02 15:57:00', 1);

/*------------------------`dn_role`-----------------------------*/
INSERT INTO `dn_role` VALUES (1,null, '管理员', '管理员', '2019-08-08 16:23:11', '2019-08-09 14:38:59',1);

/*------------------------`dn_menu`-----------------------------*/
INSERT INTO `dn_menu` VALUES (1, 0, '系统管理', '/system', 'Layout', NULL, 'el-icon-set-up', '0', 1, '2017-12-27 16:39:07', '2019-07-20 16:19:04',1);
INSERT INTO `dn_menu` VALUES (2, 1, '用户管理', '/system/user', 'febs/system/user/Index', 'user:view', '', '0', 1, '2017-12-27 16:47:13', '2019-01-22 06:45:55',1);
INSERT INTO `dn_menu` VALUES (3, 2, '新增用户', '', '', 'user:add', NULL, '1', NULL, '2017-12-27 17:02:58', NULL,1);
INSERT INTO `dn_menu` VALUES (4, 2, '修改用户', '', '', 'user:update', NULL, '1', NULL, '2017-12-27 17:04:07', NULL,1);
INSERT INTO `dn_menu` VALUES (5, 2, '删除用户', '', '', 'user:delete', NULL, '1', NULL, '2017-12-27 17:04:58', NULL,1);

/*------------------------`dn_user_role`------------------------*/
INSERT INTO `dn_user_role` VALUES (1,1, 1,null,null,1);

/*------------------------`dn_role_menu`------------------------*/
INSERT INTO `dn_role_menu` VALUES (1,1, 1,null,null,1);
INSERT INTO `dn_role_menu` VALUES (2,1, 2,null,null,1);
INSERT INTO `dn_role_menu` VALUES (3,1, 3,null,null,1);
INSERT INTO `dn_role_menu` VALUES (4,1, 4,null,null,1);
INSERT INTO `dn_role_menu` VALUES (5,1, 5,null,null,1);

/*------------------------`dn_dept`-----------------------------*/
INSERT INTO `dn_dept` VALUES (1, null, '开发部', 1, '2018-01-04 15:42:26', '2019-01-05 21:08:27',1);