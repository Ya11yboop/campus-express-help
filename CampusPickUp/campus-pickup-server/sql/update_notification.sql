-- ============================================================
-- 校园快递配送系统 - 用户通知表
-- 用于给小程序用户（下单用户/接单人）推送通知
-- ============================================================
USE `campus_pickup`;

CREATE TABLE IF NOT EXISTS `sys_notification` (
    `notif_id`    bigint       NOT NULL AUTO_INCREMENT COMMENT '通知ID',
    `user_id`     bigint       NOT NULL COMMENT '接收用户ID',
    `title`       varchar(100) DEFAULT NULL COMMENT '标题',
    `content`     varchar(500) DEFAULT NULL COMMENT '内容',
    `read_status` char(1)      DEFAULT '0' COMMENT '已读状态(0未读 1已读)',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    PRIMARY KEY (`notif_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='用户通知表';
