CREATE DATABASE IF NOT EXISTS `study_test`;

USE `study_test`;

-- --------------------------------------------------------
-- 创建 - tb_user表
-- --------------------------------------------------------
DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `id`                 int(10)        NOT NULL AUTO_INCREMENT   COMMENT '用户ID',
  `name`               varchar(30)    NOT NULL                  COMMENT '用户名',
  `gender`             int(5)         NOT NULL DEFAULT '1'      COMMENT '性别，1男，0女',
  `age`                int(5)         DEFAULT NULL              COMMENT '年龄',
  `phone`              varchar(30)    DEFAULT NULL              COMMENT '手机号',
  `email`              varchar(30)    DEFAULT NULL              COMMENT '邮箱',
  `registeredDate`     datetime       DEFAULT NULL              COMMENT '创建时间',
  `registeredUserId`   int(10)        DEFAULT NULL              COMMENT '创建者ID',
  `modifiedDate`       datetime       DEFAULT NULL              COMMENT '修改时间',
  `modifiedUserId`     int(10)        DEFAULT NULL              COMMENT '修改者ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- --------------------------------------------------------
-- 初始化 - tb_user表数据
-- --------------------------------------------------------
insert into `tb_user` values (100, 'luobi',      '1', 25, '15066668888', '15066668888@qq.com', '2023-11-24 09:58:41', '100', '2023-11-24 10:36:50', '100');
insert into `tb_user` values (101, 'zhaoYun',    '1', 30, '17055557777', '17055557777@qq.com', '2023-11-24 10:05:39', '100', '2023-11-24 09:05:00', '100');
insert into `tb_user` values (102, 'liuBei',     '1', 27, '15057928223', '1598891886@163.com', '2023-11-24 10:05:39', '100', '2023-11-24 10:05:39', '100');
insert into `tb_user` values (103, 'guanYu',     '1', 29, '15052268040', '1680456838@qq.com',  '2023-11-24 10:05:39', '100', '2023-11-24 10:05:39', '100');
insert into `tb_user` values (104, 'zhangFei',   '0', 29, '15053558177', '2002822@gmail.com',  '2023-11-24 10:05:39', '100', '2023-11-24 10:05:39', '100');
insert into `tb_user` values (105, 'maChao',     '1', 27, '15055438242', '1056094366@qq.com',  '2023-11-24 10:05:39', '100', '2023-11-24 10:05:39', '100');
insert into `tb_user` values (106, 'huangZhong', '1', 30, '15050278003', '817888760@qq.com',   '2023-11-24 10:05:39', '100', '2023-11-24 10:05:39', '100');
insert into `tb_user` values (107, 'zhuGeLiang', '0', 28, '15055828057', '148387680@163.com',  '2023-11-24 10:05:39', '100', '2023-11-24 10:05:39', '100');
insert into `tb_user` values (108, 'sunQuan',    '1', 32, '15052428976', 'SunQuan@gmail.com',  '2023-11-24 10:05:39', '100', '2023-11-24 10:05:39', '100');
insert into `tb_user` values (109, 'caoCao',     '0', 34, '15055548657', '1259256200@qq.com',  '2023-11-24 10:05:39', '100', '2023-11-24 10:05:39', '100');
