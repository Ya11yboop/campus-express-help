-- ============================================================
-- 校园快递配送系统 - 用户收货地址表
-- 每个用户独立管理自己的收货地址，用于下单时的送达地址
-- ============================================================
USE `campus_pickup`;

CREATE TABLE IF NOT EXISTS `sys_user_address` (
    `address_id`    bigint       NOT NULL AUTO_INCREMENT COMMENT '地址ID',
    `user_id`       bigint       NOT NULL COMMENT '用户ID',
    `contact_name`  varchar(30)  DEFAULT NULL COMMENT '联系人姓名',
    `contact_phone` varchar(20)  DEFAULT NULL COMMENT '联系电话',
    `address`       varchar(200) DEFAULT NULL COMMENT '详细地址',
    `is_default`    char(1)      DEFAULT '0' COMMENT '是否默认(0否 1是)',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`address_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='用户收货地址表';
