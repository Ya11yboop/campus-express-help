-- ============================================================
-- 校园快递配送系统 - 申诉/投诉表新增「被投诉人」字段
-- 投诉语义：下单的人(user_id) 投诉 接单的人(target_user_id)
-- ============================================================
USE `campus_pickup`;

ALTER TABLE `sys_complaint`
    ADD COLUMN `target_user_id` bigint DEFAULT NULL COMMENT '被投诉/申诉用户ID(接单的人)' AFTER `user_id`;
