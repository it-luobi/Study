CREATE DATABASE IF NOT EXISTS `study_test`;


USE `study_test`;


DROP TABLE IF EXISTS `tb_user`;


CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `name` varchar(50) NOT NULL COMMENT '用户名',
  `gender` varchar(1) NOT NULL DEFAULT '1' COMMENT '性别，1男，0女',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `registeredDate` datetime DEFAULT NULL COMMENT '创建时间',
  `registeredUserId` varchar(100) DEFAULT NULL COMMENT '创建者ID',
  `modifiedDate` datetime DEFAULT NULL COMMENT '修改时间',
  `modifiedUserId` varchar(100) DEFAULT NULL COMMENT '修改者ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='用户表';


insert  into `tb_user`
(`id`,`name`,`gender`,`age`,`phone`,`email`,`registeredDate`,`registeredUserId`,`modifiedDate`,`modifiedUserId`)
values
(100,'luobi','1',25,'15066668888','15066668888@qq.com','2023-11-24 09:58:41','100','2023-11-24 10:36:50','100'),
(101,'zhaoYun','1',30,'17055557777','17055557777@qq.com','2023-11-24 10:05:39','100','2023-11-24 09:05:00','100'),
(102,'liuBei','1',27,'15057928223','1598891886@qq.com','2023-11-24 10:05:39','100','2023-11-24 10:05:39','100'),
(103,'guanYu','1',29,'15052268040','1680456838@qq.com','2023-11-24 10:05:39','100','2023-11-24 10:05:39','100'),
(104,'zhangFei','0',29,'15053558177','2002822168@qq.com','2023-11-24 10:05:39','100','2023-11-24 10:05:39','100'),
(105,'maChao','1',27,'15055438242','1056094366@qq.com','2023-11-24 10:05:39','100','2023-11-24 10:05:39','100'),
(106,'huangZhong','1',30,'15050278003','817888760@qq.com','2023-11-24 10:05:39','100','2023-11-24 10:05:39','100'),
(107,'zhuGeLiange','0',28,'15055828057','1483876800@qq.com','2023-11-24 10:05:39','100','2023-11-24 10:05:39','100'),
(108,'sunQuan','1',32,'15052428976','1063079355@qq.com','2023-11-24 10:05:39','100','2023-11-24 10:05:39','100'),
(109,'caoCao','0',34,'15055548657','1259256200@qq.com','2023-11-24 10:05:39','100','2023-11-24 10:05:39','100');

