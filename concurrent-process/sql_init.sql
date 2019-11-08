CREATE DATABASE `middle_demo` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

DROP TABLE IF EXISTS `md_user`;
CREATE TABLE `md_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `user_password` varchar(64) DEFAULT NULL COMMENT '用户密码',
  `user_age` tinyint(32) DEFAULT NULL COMMENT '用户年龄',
  `version` int(12) DEFAULT NULL COMMENT '版本',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `delete_flag` tinyint(255) DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`id`),
  UNIQUE KEY `merge_index` (`user_name`,`delete_flag`) USING BTREE COMMENT '组合索引'
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `md_index`;
CREATE TABLE `md_index` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `index_a` varchar(32) NOT NULL COMMENT '唯一索引a',
  `index_b` varchar(32) DEFAULT NULL COMMENT '唯一索引b',
  `index_c` tinyint(4) DEFAULT NULL COMMENT '范围索引',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `merge_a_b_c` (`index_a`,`index_b`,`index_c`) USING BTREE COMMENT '聚合索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;