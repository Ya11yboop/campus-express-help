-- ============================================================
-- 校园快递配送系统 - 学院/校区管理员角色增量脚本
-- 在已有数据库基础上执行，补充各学院分级管理员角色与示例账号
-- ============================================================
USE `campus_pickup`;

-- 学院分级管理员角色（campus_admin 若已存在则跳过）
INSERT IGNORE INTO `sys_role` (`role_id`, `role_name`, `role_key`, `role_sort`, `status`, `create_time`, `remark`) VALUES
(7,  '校区管理员',       'campus_admin',          7,  '0', now(), '拥有本学院所有模块权限，仅限本学院数据'),
(8,  '学院运营管理员',   'campus_operation_admin',8,  '0', now(), '本学院运营模块权限，仅限本学院数据'),
(9,  '学院客服管理员',   'campus_cs_admin',       9,  '0', now(), '本学院客服模块权限，仅限本学院数据'),
(10, '学院基础数据管理员','campus_base_admin',     10, '0', now(), '本学院基础数据模块权限，仅限本学院数据');

-- 示例：示例大学（school_id=100）各学院管理员账号，密码均为 123456
INSERT IGNORE INTO `sys_user`
(`user_id`, `user_name`, `nick_name`, `real_name`, `password`, `school`, `school_id`, `phone`, `status`, `user_type`, `credit_score`, `create_time`, `remark`) VALUES
(103, 'campus_op_demo',   '学院运营管理员',   '示例运营', 'e10adc3949ba59abbe56e057f20f883e', '示例大学', 100, '1380000103', '0', 'admin', 100, now(), '示例大学运营管理员'),
(104, 'campus_cs_demo',   '学院客服管理员',   '示例客服', 'e10adc3949ba59abbe56e057f20f883e', '示例大学', 100, '1380000104', '0', 'admin', 100, now(), '示例大学客服管理员'),
(105, 'campus_base_demo', '学院基础数据管理员','示例数据', 'e10adc3949ba59abbe56e057f20f883e', '示例大学', 100, '1380000105', '0', 'admin', 100, now(), '示例大学基础数据管理员');

-- 用户角色关联
INSERT IGNORE INTO `sys_user_role` (`user_id`, `role_id`) VALUES
(103, 8),
(104, 9),
(105, 10);
