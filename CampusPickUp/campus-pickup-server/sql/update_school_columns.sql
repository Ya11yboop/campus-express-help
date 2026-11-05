-- ============================================================
-- 校园快递配送系统 - 业务表 school_id 字段增量脚本（补丁）
--
-- 说明：
--   update_school.sql 只给 sys_user 加了 school_id 并建了 sys_school；
--   下面这些业务表当时是在库上手工加的 school_id，未落进仓库。
--   本脚本用于在「全新数据库」上补齐这些字段，保证可复现。
--   ⚠️ 若当前库已存在 school_id 列，请勿重复执行（会报 Duplicate column）。
-- ============================================================
USE `campus_pickup`;

ALTER TABLE `sys_order`          ADD COLUMN `school_id` bigint DEFAULT NULL COMMENT '学校ID', ADD KEY `idx_school_id` (`school_id`);
ALTER TABLE `sys_courier`        ADD COLUMN `school_id` bigint DEFAULT NULL COMMENT '学校ID', ADD KEY `idx_school_id` (`school_id`);
ALTER TABLE `sys_complaint`      ADD COLUMN `school_id` bigint DEFAULT NULL COMMENT '学校ID', ADD KEY `idx_school_id` (`school_id`);
ALTER TABLE `sys_evaluate`       ADD COLUMN `school_id` bigint DEFAULT NULL COMMENT '学校ID', ADD KEY `idx_school_id` (`school_id`);
ALTER TABLE `sys_credit_log`     ADD COLUMN `school_id` bigint DEFAULT NULL COMMENT '学校ID', ADD KEY `idx_school_id` (`school_id`);
ALTER TABLE `sys_notice`         ADD COLUMN `school_id` bigint DEFAULT NULL COMMENT '学校ID', ADD KEY `idx_school_id` (`school_id`);
ALTER TABLE `sys_express_point`  ADD COLUMN `school_id` bigint DEFAULT NULL COMMENT '学校ID', ADD KEY `idx_school_id` (`school_id`);
ALTER TABLE `sys_dormitory`      ADD COLUMN `school_id` bigint DEFAULT NULL COMMENT '学校ID', ADD KEY `idx_school_id` (`school_id`);
