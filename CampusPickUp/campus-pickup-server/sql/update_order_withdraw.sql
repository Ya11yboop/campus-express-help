-- ============================================================
-- 校园快递配送系统 - 接单撤回申请表
-- 接单人接单后 30 分钟内可无条件撤回；超过 30 分钟需提交申请，
-- 由管理员审核，通过后订单恢复为待接单，驳回则保持原状态
-- ============================================================
USE `campus_pickup`;

CREATE TABLE IF NOT EXISTS `sys_order_withdraw_apply` (
    `apply_id`      bigint        NOT NULL AUTO_INCREMENT COMMENT '申请ID',
    `order_id`      bigint        NOT NULL COMMENT '订单ID',
    `courier_id`    bigint        DEFAULT NULL COMMENT '接单人ID(申请人)',
    `reason`        varchar(500)  DEFAULT NULL COMMENT '撤回理由',
    `apply_status`  char(1)       DEFAULT '0' COMMENT '审核状态(0待审核 1通过 2驳回)',
    `audit_user_id` bigint        DEFAULT NULL COMMENT '审核人ID',
    `audit_time`    datetime      DEFAULT NULL COMMENT '审核时间',
    `remark`        varchar(500)  DEFAULT NULL COMMENT '审核备注/驳回原因',
    `create_time`   datetime      DEFAULT NULL COMMENT '申请时间',
    PRIMARY KEY (`apply_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_apply_status` (`apply_status`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='接单撤回申请表';
