-- ============================================================
-- 校园快递配送系统 - 学校模块增量脚本
-- 在已有数据库基础上执行（用户表需新增 school_id 字段）
-- ============================================================
USE `campus_pickup`;

-- 学校表
DROP TABLE IF EXISTS `sys_school`;
CREATE TABLE `sys_school` (
    `school_id`   bigint       NOT NULL AUTO_INCREMENT COMMENT '学校ID',
    `school_name` varchar(50)  NOT NULL COMMENT '学校名称',
    `school_code` varchar(30)  DEFAULT NULL COMMENT '学校编码',
    `address`     varchar(100) DEFAULT NULL COMMENT '学校地址',
    `status`      char(1)      DEFAULT '0' COMMENT '状态(0正常 1停用)',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='学校表';

-- 用户表新增学校ID字段（school 原文本字段保留兼容）
ALTER TABLE `sys_user` ADD COLUMN `school_id` bigint DEFAULT NULL COMMENT '学校ID' AFTER `school`;

-- 初始化学校数据
INSERT INTO `sys_school` (`school_id`, `school_name`, `school_code`, `address`, `status`, `create_time`, `remark`) VALUES
(1, '示例大学', 'DEMO001', '示例市示例区', '0', now(), '初始学校');
