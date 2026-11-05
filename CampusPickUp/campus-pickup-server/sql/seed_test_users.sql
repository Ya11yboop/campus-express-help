-- ============================================================
-- 校园快递配送系统 - 微信登录/信息完善 测试数据
-- 可重复执行（使用了 NOT EXISTS 防重复）
-- ============================================================
USE `campus_pickup`;

-- ------------------------------------------------------------
-- 1. 完整学生账号（可直接用「用户名 + 密码 123456」登录测试）
-- ------------------------------------------------------------
INSERT INTO `sys_user`
(`openid`, `user_name`, `nick_name`, `real_name`, `password`, `school`, `school_id`, `student_no`, `id_card`, `phone`, `credit_score`, `status`, `user_type`, `create_time`, `remark`)
VALUES
(NULL, 'stu_alice', '爱丽丝', '艾丽',   'e10adc3949ba59abbe56e057f20f883e', '示例大学',         100, 'S2026001', '110101200001011234', '13800001111', 100, '0', 'user', now(), '测试学生1'),
(NULL, 'stu_bob',   '鲍勃',   '鲍勃',   'e10adc3949ba59abbe56e057f20f883e', '示例大学',         100, 'S2026002', '110101200002011234', '13800002222', 100, '0', 'user', now(), '测试学生2'),
(NULL, 'stu_carol', '卡罗尔', '卡罗',   'e10adc3949ba59abbe56e057f20f883e', '贵阳信息科技学院', 103, 'S2026003', '110101200003011234', '13800003333', 100, '0', 'user', now(), '测试学生3(另一学校)');

-- 给以上学生分配「普通用户」角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`)
SELECT u.`user_id`, r.`role_id`
FROM `sys_user` u
JOIN `sys_role` r ON r.`role_key` = 'user'
WHERE u.`user_name` IN ('stu_alice', 'stu_bob', 'stu_carol')
  AND NOT EXISTS (SELECT 1 FROM `sys_user_role` ur WHERE ur.`user_id` = u.`user_id` AND ur.`role_id` = r.`role_id`);

-- ------------------------------------------------------------
-- 2. 微信自动注册 - 未完善用户（模拟首次微信登录后的状态）
--    说明：真实流程由小程序 wx.login -> 后端 /login/wechat 自动创建；
--    此处只是造一条等价数据，方便直接查看「未完善」判定效果。
--    该账号密码为空，无法用用户名密码登录，需先完善信息。
-- ------------------------------------------------------------
INSERT INTO `sys_user`
(`openid`, `user_name`, `password`, `credit_score`, `status`, `user_type`, `create_time`, `remark`)
SELECT 'oDemo_wx_openid_001', 'wxoDemo_wx_openid_001', '', 100, '0', 'user', now(), '微信未完善示例'
WHERE NOT EXISTS (SELECT 1 FROM `sys_user` WHERE `openid` = 'oDemo_wx_openid_001');

INSERT INTO `sys_user_role` (`user_id`, `role_id`)
SELECT u.`user_id`, r.`role_id`
FROM `sys_user` u
JOIN `sys_role` r ON r.`role_key` = 'user'
WHERE u.`openid` = 'oDemo_wx_openid_001'
  AND NOT EXISTS (SELECT 1 FROM `sys_user_role` ur WHERE ur.`user_id` = u.`user_id` AND ur.`role_id` = r.`role_id`);
