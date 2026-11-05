-- ============================================================
-- 校园快递配送系统 - 信誉分扣除申请表
-- 客服提交扣除申请，管理员审核通过后实际扣除信誉分
-- ============================================================
USE `campus_pickup`;

CREATE TABLE IF NOT EXISTS `sys_credit_apply` (
    `apply_id`      bigint       NOT NULL AUTO_INCREMENT COMMENT '申请ID',
    `user_id`       bigint       NOT NULL COMMENT '被扣分用户ID',
    `change_score`  int          DEFAULT 0 COMMENT '变更分数(负数=扣除)',
    `reason`        varchar(200) DEFAULT NULL COMMENT '申请原因',
    `apply_status`  char(1)      DEFAULT '0' COMMENT '审核状态(0待审核 1通过 2驳回)',
    `apply_user_id` bigint       DEFAULT NULL COMMENT '申请人ID(客服)',
    `audit_user_id` bigint       DEFAULT NULL COMMENT '审核人ID',
    `audit_time`    datetime     DEFAULT NULL COMMENT '审核时间',
    `create_time`   datetime     DEFAULT NULL COMMENT '申请时间',
    `remark`        varchar(500) DEFAULT NULL COMMENT '审核备注/驳回原因',
    PRIMARY KEY (`apply_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_apply_status` (`apply_status`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='信誉分扣除申请表';
