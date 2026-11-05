-- ============================================================
-- 校园快递配送系统 - 配送完成申请表
-- 接单人申请配送完成(上传照片) -> 管理员审核通过/驳回 -> 通过后订单完成
-- ============================================================
USE `campus_pickup`;

CREATE TABLE IF NOT EXISTS `sys_finish_apply` (
    `apply_id`      bigint        NOT NULL AUTO_INCREMENT COMMENT '申请ID',
    `order_id`      bigint        NOT NULL COMMENT '订单ID',
    `courier_id`    bigint        DEFAULT NULL COMMENT '接单人ID',
    `image`         varchar(500)  DEFAULT NULL COMMENT '配送完成照片地址',
    `apply_status`  char(1)       DEFAULT '0' COMMENT '审核状态(0待审核 1通过 2驳回)',
    `remark`        varchar(500)  DEFAULT NULL COMMENT '审核备注/驳回原因',
    `audit_user_id` bigint        DEFAULT NULL COMMENT '审核人ID',
    `audit_time`    datetime      DEFAULT NULL COMMENT '审核时间',
    `create_time`   datetime      DEFAULT NULL COMMENT '申请时间',
    PRIMARY KEY (`apply_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_apply_status` (`apply_status`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='配送完成申请表';
