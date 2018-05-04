/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.5.40 : Database - cids
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cids` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cids`;

/*Table structure for table `t_gp_application` */

DROP TABLE IF EXISTS `t_gp_application`;

CREATE TABLE `t_gp_application` (
  `F_APP_ID` int(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `F_APP_NAME` varchar(50) DEFAULT NULL COMMENT '应用名称',
  `F_STORAGE_PATH` varchar(200) DEFAULT NULL COMMENT 'war包存储路径',
  `F_VIEW_NAME` varchar(50) DEFAULT NULL COMMENT '视图名称',
  `F_BUILD_INFO_URL` varchar(200) DEFAULT NULL COMMENT '详细信息地址',
  PRIMARY KEY (`F_APP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用信息';

/*Data for the table `t_gp_application` */

/*Table structure for table `t_gp_deploy_host` */

DROP TABLE IF EXISTS `t_gp_deploy_host`;

CREATE TABLE `t_gp_deploy_host` (
  `F_HOST_ID` int(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `F_HOST_NAME` varchar(30) DEFAULT NULL COMMENT '主机别名',
  `F_HOST_IP` varchar(20) DEFAULT NULL COMMENT '主机ip',
  `F_LOGIN_NAME` varchar(30) DEFAULT NULL COMMENT '访问用户名',
  `F_PASSWORD` varchar(50) DEFAULT NULL COMMENT '访问密码',
  `F_CREATE_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_UPDATE_TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`F_HOST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='目标主机';

/*Data for the table `t_gp_deploy_host` */

/*Table structure for table `t_gp_deploy_server` */

DROP TABLE IF EXISTS `t_gp_deploy_server`;

CREATE TABLE `t_gp_deploy_server` (
  `F_SERVER_ID` int(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `F_HOST_ID` int(5) NOT NULL COMMENT '主机id',
  `F_APP_ID` int(5) NOT NULL COMMENT '应用id',
  `F_SERVER_NAME` varchar(30) DEFAULT NULL COMMENT '应用服务器别名',
  `F_STORAGE_PATH` varchar(200) NOT NULL COMMENT '部署路径',
  `F_PROTOCOL` varchar(10) DEFAULT NULL COMMENT '请求协议',
  `F_PORT` varchar(10) NOT NULL COMMENT '端口号',
  `F_CONTEX_TPATH` varchar(10) DEFAULT NULL COMMENT '项目根路径',
  `F_CREATE_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_UPDATE_TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`F_SERVER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='目标应用服务器';

/*Data for the table `t_gp_deploy_server` */

/*Table structure for table `t_gp_permission` */

DROP TABLE IF EXISTS `t_gp_permission`;

CREATE TABLE `t_gp_permission` (
  `F_PERMISSION_ID` int(5) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `F_PERMISSION_NAME` varchar(30) DEFAULT NULL COMMENT '权限名称',
  `F_PERMISSION_DESC` varchar(30) DEFAULT NULL COMMENT '权限描述',
  `F_PERMISSION_URL` varchar(100) NOT NULL COMMENT '权限地址',
  `F_PARENT_ID` varchar(30) NOT NULL COMMENT '父节点',
  `F_CREATE_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_UPDATE_TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`F_PERMISSION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户权限';

/*Data for the table `t_gp_permission` */

insert  into `t_gp_permission`(`F_PERMISSION_ID`,`F_PERMISSION_NAME`,`F_PERMISSION_DESC`,`F_PERMISSION_URL`,`F_PARENT_ID`,`F_CREATE_TIMESTAMP`,`F_UPDATE_TIMESTAMP`) values (1,'权限管理','用户、角色权限管理','','0','2018-05-04 14:24:59','0000-00-00 00:00:00'),(2,'角色权限','管理角色权限','/permission/role.htm','1','2018-05-04 14:25:28','0000-00-00 00:00:00'),(3,'用户权限','管理用户权限','/permission/user.htm','1','2018-05-04 14:25:42','0000-00-00 00:00:00'),(4,'添加用户权限','添加','/permission/user/add.htm','3','2018-05-04 16:41:32','0000-00-00 00:00:00'),(5,'删除用户权限','删除','/permission/user/del.htm','3','2018-05-04 16:41:54','0000-00-00 00:00:00'),(6,'系统查询','查询系统信息','','0','2018-05-04 16:42:46','0000-00-00 00:00:00'),(7,'应用信息','application','/applicatiion/queryList.htm','6','2018-05-04 16:43:37','0000-00-00 00:00:00');

/*Table structure for table `t_gp_role` */

DROP TABLE IF EXISTS `t_gp_role`;

CREATE TABLE `t_gp_role` (
  `F_ROLE_ID` varchar(30) NOT NULL COMMENT 'id',
  `F_ROLE_NAME` varchar(30) DEFAULT NULL COMMENT '角色名称',
  `F_ROLE_DESC` varchar(30) DEFAULT NULL COMMENT '角色描述',
  `F_CREATE_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_UPDATE_TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`F_ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';

/*Data for the table `t_gp_role` */

insert  into `t_gp_role`(`F_ROLE_ID`,`F_ROLE_NAME`,`F_ROLE_DESC`,`F_CREATE_TIMESTAMP`,`F_UPDATE_TIMESTAMP`) values ('admincode','管理员','管理员','2018-04-27 15:50:12','0000-00-00 00:00:00'),('usercode','普通用户','普通用户','2018-04-27 15:59:07','0000-00-00 00:00:00');

/*Table structure for table `t_gp_role_permission` */

DROP TABLE IF EXISTS `t_gp_role_permission`;

CREATE TABLE `t_gp_role_permission` (
  `F_ROLE_ID` varchar(30) NOT NULL COMMENT '角色id',
  `F_PERMISSION_ID` int(5) NOT NULL COMMENT '权限id',
  `F_CREATE_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_UPDATE_TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关联表';

/*Data for the table `t_gp_role_permission` */

insert  into `t_gp_role_permission`(`F_ROLE_ID`,`F_PERMISSION_ID`,`F_CREATE_TIMESTAMP`,`F_UPDATE_TIMESTAMP`) values ('admincode',1,'2018-04-27 15:51:36','0000-00-00 00:00:00'),('admincode',2,'2018-04-27 15:57:37','0000-00-00 00:00:00'),('admincode',3,'2018-05-04 14:47:28','0000-00-00 00:00:00'),('admincode',4,'2018-05-04 16:44:33','0000-00-00 00:00:00'),('admincode',5,'2018-05-04 16:44:37','0000-00-00 00:00:00'),('admincode',6,'2018-05-04 16:44:40','0000-00-00 00:00:00'),('admincode',7,'2018-05-04 16:44:45','0000-00-00 00:00:00');

/*Table structure for table `t_gp_user` */

DROP TABLE IF EXISTS `t_gp_user`;

CREATE TABLE `t_gp_user` (
  `F_USER_ID` varchar(32) NOT NULL COMMENT 'id',
  `F_LOGIN_NAME` varchar(30) NOT NULL COMMENT '登录名称',
  `F_PASSWORD` varchar(32) NOT NULL COMMENT '登录密码',
  `F_REAL_NAME` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `F_STATUS` varchar(10) DEFAULT NULL COMMENT '用户状态',
  `F_CREATE_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_UPDATE_TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`F_USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';

/*Data for the table `t_gp_user` */

insert  into `t_gp_user`(`F_USER_ID`,`F_LOGIN_NAME`,`F_PASSWORD`,`F_REAL_NAME`,`F_STATUS`,`F_CREATE_TIMESTAMP`,`F_UPDATE_TIMESTAMP`) values ('000001','admin','81dc9bdb52d04dc20036dbd8313ed055','秦豪','0','2018-04-27 17:54:58','0000-00-00 00:00:00');

/*Table structure for table `t_gp_user_role` */

DROP TABLE IF EXISTS `t_gp_user_role`;

CREATE TABLE `t_gp_user_role` (
  `F_USER_ID` varchar(32) NOT NULL COMMENT '用户id',
  `F_ROLE_ID` varchar(30) NOT NULL COMMENT '角色id',
  `F_CREATE_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `F_UPDATE_TIMESTAMP` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

/*Data for the table `t_gp_user_role` */

insert  into `t_gp_user_role`(`F_USER_ID`,`F_ROLE_ID`,`F_CREATE_TIMESTAMP`,`F_UPDATE_TIMESTAMP`) values ('000001','admincode','2018-04-27 15:50:32','0000-00-00 00:00:00'),('000001','usercode','2018-04-27 15:59:25','0000-00-00 00:00:00');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
