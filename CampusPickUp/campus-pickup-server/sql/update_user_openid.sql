-- ============================================================
-- 校园快递配送系统 - 用户表增加微信唯一标识 openid
-- 微信一键登录时，用 openid 关联用户；首次登录自动注册
-- ============================================================
USE `campus_pickup`;

ALTER TABLE `sys_user`
    ADD COLUMN `openid` varchar(64) DEFAULT NULL COMMENT '微信openid' AFTER `user_id`,
    ADD UNIQUE KEY `uk_openid` (`openid`);
