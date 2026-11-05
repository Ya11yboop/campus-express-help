-- ============================================================
-- 校园快递配送系统 (CampusPickUp) 数据库初始化脚本
-- 数据库：MySQL 8.x，字符集 utf8mb4
-- 角色：super_admin 超级管理员 / operation_admin 运营管理员 /
--       cs_admin 客服管理员 / base_admin 基础数据管理员 /
--       courier 跑腿人员 / user 普通用户(学生)
-- 初始账号：admin/admin123，其余均为 123456
-- ============================================================

CREATE DATABASE IF NOT EXISTS `campus_pickup` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `campus_pickup`;

-- ------------------------------------------------------------
-- 1. 用户表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `user_id`       bigint       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `user_name`     varchar(30)  NOT NULL COMMENT '用户名(登录账号)',
    `nick_name`     varchar(30)  DEFAULT NULL COMMENT '昵称',
    `real_name`     varchar(30)  DEFAULT NULL COMMENT '真实姓名',
    `password`      varchar(100) DEFAULT '' COMMENT '密码(MD5加密)',
    `school`        varchar(50)  DEFAULT NULL COMMENT '学校',
    `student_no`    varchar(30)  DEFAULT NULL COMMENT '学号',
    `id_card`       varchar(18)  DEFAULT NULL COMMENT '身份证号',
    `phone`         varchar(20)  DEFAULT NULL COMMENT '手机号',
    `email`         varchar(50)  DEFAULT NULL COMMENT '邮箱',
    `avatar`        varchar(255) DEFAULT NULL COMMENT '头像地址',
    `gender`        char(1)      DEFAULT '2' COMMENT '性别(0男 1女 2未知)',
    `dormitory_id`  bigint       DEFAULT NULL COMMENT '宿舍楼ID',
    `room_no`       varchar(30)  DEFAULT NULL COMMENT '宿舍房间号',
    `credit_score`  int          DEFAULT 100 COMMENT '信誉分',
    `status`        char(1)      DEFAULT '0' COMMENT '状态(0正常 1停用)',
    `user_type`     varchar(20)  DEFAULT 'user' COMMENT '用户类型(admin/courier/user)',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`        varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`user_id`),
    UNIQUE KEY `uk_user_name` (`user_name`),
    KEY `idx_student_no` (`student_no`),
    KEY `idx_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ------------------------------------------------------------
-- 2. 角色表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `role_id`     bigint       NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(30)  NOT NULL COMMENT '角色名称',
    `role_key`    varchar(100) NOT NULL COMMENT '角色权限字符串',
    `role_sort`   int          DEFAULT 0 COMMENT '显示顺序',
    `status`      char(1)      DEFAULT '0' COMMENT '状态(0正常 1停用)',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`role_id`),
    UNIQUE KEY `uk_role_key` (`role_key`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ------------------------------------------------------------
-- 3. 用户角色关联表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
    `user_id` bigint NOT NULL COMMENT '用户ID',
    `role_id` bigint NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ------------------------------------------------------------
-- 4. 跑腿人员表（代取跑腿人员管理）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_courier`;
CREATE TABLE `sys_courier` (
    `courier_id`    bigint       NOT NULL AUTO_INCREMENT COMMENT '跑腿人员ID',
    `user_id`       bigint       NOT NULL COMMENT '关联用户ID',
    `real_name`     varchar(30)  NOT NULL COMMENT '真实姓名',
    `id_card`       varchar(18)  DEFAULT NULL COMMENT '身份证号',
    `phone`         varchar(20)  DEFAULT NULL COMMENT '联系电话',
    `service_area`  varchar(100) DEFAULT NULL COMMENT '服务区域',
    `audit_status`  char(1)      DEFAULT '0' COMMENT '审核状态(0待审核 1通过 2驳回)',
    `audit_result`  varchar(200) DEFAULT NULL COMMENT '审核结果/说明',
    `audit_time`    datetime     DEFAULT NULL COMMENT '审核时间',
    `status`        char(1)      DEFAULT '0' COMMENT '工作状态(0在岗 1休息)',
    `score`         decimal(3,2) DEFAULT 5.00 COMMENT '服务评分',
    `order_count`   int          DEFAULT 0 COMMENT '完成订单数',
    `create_time`   datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`   datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`        varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`courier_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='跑腿人员表';

-- ------------------------------------------------------------
-- 5. 驿站表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_express_point`;
CREATE TABLE `sys_express_point` (
    `point_id`     bigint       NOT NULL AUTO_INCREMENT COMMENT '驿站ID',
    `point_name`   varchar(50)  NOT NULL COMMENT '驿站名称',
    `location`     varchar(100) DEFAULT NULL COMMENT '具体位置',
    `manager_name` varchar(30)  DEFAULT NULL COMMENT '负责人',
    `phone`        varchar(20)  DEFAULT NULL COMMENT '联系电话',
    `status`       char(1)      DEFAULT '0' COMMENT '状态(0营业 1停用)',
    `sort`         int          DEFAULT 0 COMMENT '排序',
    `create_time`  datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`  datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`       varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`point_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='驿站表';

-- ------------------------------------------------------------
-- 6. 宿舍楼表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_dormitory`;
CREATE TABLE `sys_dormitory` (
    `dormitory_id`   bigint       NOT NULL AUTO_INCREMENT COMMENT '宿舍楼ID',
    `dormitory_name` varchar(50)  NOT NULL COMMENT '宿舍楼名称',
    `building_no`    varchar(20)  DEFAULT NULL COMMENT '楼栋号',
    `floor_count`    int          DEFAULT NULL COMMENT '楼层数',
    `location`       varchar(100) DEFAULT NULL COMMENT '位置',
    `status`         char(1)      DEFAULT '0' COMMENT '状态(0正常 1停用)',
    `create_time`    datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`         varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dormitory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='宿舍楼表';

-- ------------------------------------------------------------
-- 7. 快递大小分类表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_parcel_size`;
CREATE TABLE `sys_parcel_size` (
    `size_id`     bigint        NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `size_name`   varchar(30)   NOT NULL COMMENT '分类名称(小件/中件/大件/超重件)',
    `size_desc`   varchar(100)  DEFAULT NULL COMMENT '说明',
    `fee`         decimal(10,2) DEFAULT 0.00 COMMENT '基础配送费',
    `status`      char(1)       DEFAULT '0' COMMENT '状态(0正常 1停用)',
    `sort`        int           DEFAULT 0 COMMENT '排序',
    `create_time` datetime      DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime      DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`size_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='快递大小分类表';

-- ------------------------------------------------------------
-- 8. 订单表（帮拿快递订单）
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_order`;
CREATE TABLE `sys_order` (
    `order_id`         bigint        NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `order_no`         varchar(40)   NOT NULL COMMENT '订单编号',
    `user_id`          bigint        NOT NULL COMMENT '下单用户ID',
    `courier_id`       bigint        DEFAULT NULL COMMENT '接单人ID(跑腿人员/用户)',
    `order_type`       char(1)       NOT NULL COMMENT '订单类型(1帮取 2帮寄)',
    `tracking_no`      varchar(50)   DEFAULT NULL COMMENT '快递单号',
    `express_company`  varchar(30)   DEFAULT NULL COMMENT '快递公司',
    `size_id`          bigint        DEFAULT NULL COMMENT '快递大小分类ID',
    `point_id`         bigint        DEFAULT NULL COMMENT '取件驿站ID',
    `pickup_address`   varchar(200)  DEFAULT NULL COMMENT '取件地址',
    `delivery_address` varchar(200)  DEFAULT NULL COMMENT '送达地址(宿舍楼/房间)',
    `receiver_name`    varchar(30)   DEFAULT NULL COMMENT '收件人姓名',
    `receiver_phone`   varchar(20)   DEFAULT NULL COMMENT '收件人电话',
    `fee`              decimal(10,2) DEFAULT 0.00 COMMENT '配送费',
    `order_status`     char(1)       DEFAULT '0' COMMENT '状态(0待接单 1配送中 2已完成 3已取消 4申诉中)',
    `create_time`      datetime      DEFAULT NULL COMMENT '下单时间',
    `accept_time`      datetime      DEFAULT NULL COMMENT '接单时间',
    `finish_time`      datetime      DEFAULT NULL COMMENT '完成时间',
    `remark`           varchar(500)  DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`order_id`),
    UNIQUE KEY `uk_order_no` (`order_no`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_courier_id` (`courier_id`),
    KEY `idx_order_status` (`order_status`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ------------------------------------------------------------
-- 9. 申诉/投诉表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_complaint`;
CREATE TABLE `sys_complaint` (
    `complaint_id`   bigint       NOT NULL AUTO_INCREMENT COMMENT '申诉/投诉ID',
    `order_id`       bigint       NOT NULL COMMENT '关联订单ID',
    `user_id`        bigint       DEFAULT NULL COMMENT '发起人ID',
    `complaint_type` char(1)      DEFAULT '1' COMMENT '类型(1申诉 2投诉)',
    `content`        varchar(500) DEFAULT NULL COMMENT '内容',
    `images`         varchar(1000) DEFAULT NULL COMMENT '图片(逗号分隔)',
    `handle_status`  char(1)      DEFAULT '0' COMMENT '处理状态(0待处理 1已处理)',
    `handle_result`  varchar(500) DEFAULT NULL COMMENT '处理结果/审核结果',
    `handler_id`     bigint       DEFAULT NULL COMMENT '处理人ID',
    `handle_time`    datetime     DEFAULT NULL COMMENT '处理时间',
    `create_time`    datetime     DEFAULT NULL COMMENT '创建时间',
    `remark`         varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`complaint_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='申诉/投诉表';

-- ------------------------------------------------------------
-- 10. 评价表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_evaluate`;
CREATE TABLE `sys_evaluate` (
    `evaluate_id` bigint       NOT NULL AUTO_INCREMENT COMMENT '评价ID',
    `order_id`    bigint       NOT NULL COMMENT '订单ID',
    `user_id`     bigint       DEFAULT NULL COMMENT '评价人ID',
    `courier_id`  bigint       DEFAULT NULL COMMENT '被评价人ID',
    `score`       tinyint      DEFAULT 5 COMMENT '评分(1-5)',
    `content`     varchar(500) DEFAULT NULL COMMENT '评价内容',
    `reply`       varchar(500) DEFAULT NULL COMMENT '客服回复',
    `reply_time`  datetime     DEFAULT NULL COMMENT '回复时间',
    `create_time` datetime     DEFAULT NULL COMMENT '评价时间',
    PRIMARY KEY (`evaluate_id`),
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- ------------------------------------------------------------
-- 11. 公告表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice` (
    `notice_id`   bigint       NOT NULL AUTO_INCREMENT COMMENT '公告ID',
    `title`       varchar(100) NOT NULL COMMENT '标题',
    `content`     text         COMMENT '内容',
    `notice_type` char(1)      DEFAULT '1' COMMENT '类型(1通知 2公告)',
    `status`      char(1)      DEFAULT '0' COMMENT '状态(0发布 1下线)',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='公告表';

-- ------------------------------------------------------------
-- 12. 轮播图表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_banner`;
CREATE TABLE `sys_banner` (
    `banner_id`   bigint       NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
    `title`       varchar(50)  DEFAULT NULL COMMENT '标题',
    `image`       varchar(255) NOT NULL COMMENT '图片地址',
    `link_type`   char(1)      DEFAULT '0' COMMENT '跳转类型(0无 1链接 2页面)',
    `link_url`    varchar(255) DEFAULT NULL COMMENT '跳转地址',
    `status`      char(1)      DEFAULT '0' COMMENT '状态(0启用 1停用)',
    `sort`        int          DEFAULT 0 COMMENT '排序',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`banner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- ------------------------------------------------------------
-- 13. 字典类型表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
    `dict_id`     bigint       NOT NULL AUTO_INCREMENT COMMENT '字典类型ID',
    `dict_name`   varchar(100) NOT NULL COMMENT '字典名称',
    `dict_type`   varchar(100) NOT NULL COMMENT '字典类型',
    `status`      char(1)      DEFAULT '0' COMMENT '状态(0正常 1停用)',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_id`),
    UNIQUE KEY `uk_dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='字典类型表';

-- ------------------------------------------------------------
-- 14. 字典数据表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
    `dict_code`   bigint       NOT NULL AUTO_INCREMENT COMMENT '字典数据ID',
    `dict_sort`   int          DEFAULT 0 COMMENT '排序',
    `dict_label`  varchar(100) DEFAULT '' COMMENT '字典标签',
    `dict_value`  varchar(100) DEFAULT '' COMMENT '字典键值',
    `dict_type`   varchar(100) DEFAULT '' COMMENT '字典类型',
    `status`      char(1)      DEFAULT '0' COMMENT '状态(0正常 1停用)',
    `create_time` datetime     DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime     DEFAULT NULL COMMENT '更新时间',
    `remark`      varchar(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`dict_code`),
    KEY `idx_dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='字典数据表';

-- ------------------------------------------------------------
-- 15. 信誉分变更记录表
-- ------------------------------------------------------------
DROP TABLE IF EXISTS `sys_credit_log`;
CREATE TABLE `sys_credit_log` (
    `log_id`       bigint       NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `user_id`      bigint       NOT NULL COMMENT '用户ID',
    `change_score` int          DEFAULT 0 COMMENT '变更分数(可正负)',
    `credit_score` int          DEFAULT 0 COMMENT '变更后信誉分',
    `reason`       varchar(200) DEFAULT NULL COMMENT '原因',
    `operator_id`  bigint       DEFAULT NULL COMMENT '操作人ID',
    `create_time`  datetime     DEFAULT NULL COMMENT '记录时间',
    PRIMARY KEY (`log_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COMMENT='信誉分变更记录表';

-- ============================================================
-- 初始化示例数据
-- ============================================================

-- 角色
INSERT INTO `sys_role` (`role_id`, `role_name`, `role_key`, `role_sort`, `status`, `create_time`, `remark`) VALUES
(1, '超级系统管理员', 'super_admin',     1, '0', now(), '拥有后台所有功能模块权限'),
(2, '运营管理员',     'operation_admin', 2, '0', now(), '学生/跑腿/订单/评价/公告/轮播图/数据流图'),
(3, '订单客服管理员', 'cs_admin',        3, '0', now(), '订单查看/申诉投诉/信誉分/评价回复'),
(4, '基础数据管理员', 'base_admin',      4, '0', now(), '驿站/宿舍楼/快递大小分类/数据字典'),
(5, '跑腿人员',       'courier',         5, '0', now(), '接单配送'),
(6, '普通用户',       'user',            6, '0', now(), '学生，使用微信小程序');

-- 用户（密码：admin/admin123，其余均为 123456 的 MD5）
INSERT INTO `sys_user` (`user_id`, `user_name`, `nick_name`, `real_name`, `password`, `school`, `student_no`, `id_card`, `phone`, `status`, `user_type`, `credit_score`, `create_time`, `remark`) VALUES
(1, 'admin',     '超级管理员', '系统管理员', '0192023a7bbd73250516f069df18b500', '示例大学', NULL, NULL, '13800000000', '0', 'admin',   100, now(), '超级系统管理员'),
(2, 'operation', '运营管理员', '运营小张',   'e10adc3949ba59abbe56e057f20f883e', '示例大学', NULL, NULL, '13800000001', '0', 'admin',   100, now(), '运营管理员'),
(3, 'cs',        '客服管理员', '客服小李',   'e10adc3949ba59abbe56e057f20f883e', '示例大学', NULL, NULL, '13800000002', '0', 'admin',   100, now(), '订单客服管理员'),
(4, 'base',      '基础数据员', '数据小王',   'e10adc3949ba59abbe56e057f20f883e', '示例大学', NULL, NULL, '13800000003', '0', 'admin',   100, now(), '基础数据管理员'),
(5, 'courier01', '快递员小赵', '赵跑腿',     'e10adc3949ba59abbe56e057f20f883e', '示例大学', NULL, '110101199001011234', '13900000001', '0', 'courier', 100, now(), '跑腿人员'),
(6, 'student01', '学生小钱',   '钱同学',     'e10adc3949ba59abbe56e057f20f883e', '示例大学', '20230001', '110101200501011234', '13700000002', '0', 'user', 100, now(), '普通学生');

-- 用户角色关联
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6);

-- 跑腿人员
INSERT INTO `sys_courier` (`courier_id`, `user_id`, `real_name`, `id_card`, `phone`, `service_area`, `audit_status`, `status`, `score`, `create_time`, `remark`) VALUES
(1, 5, '赵跑腿', '110101199001011234', '13900000001', '东区宿舍', '1', '0', 5.00, now(), '示例跑腿人员');

-- 驿站
INSERT INTO `sys_express_point` (`point_id`, `point_name`, `location`, `manager_name`, `phone`, `status`, `sort`, `create_time`, `remark`) VALUES
(1, '菜鸟驿站(东区)', '东区宿舍3号楼一层', '王站长', '13811112222', '0', 1, now(), '代收代寄'),
(2, '京东派(西区)',   '西区体育馆旁',     '李站长', '13833334444', '0', 2, now(), '京东自营'),
(3, '顺丰速运点',     '北门商业街',       '赵站长', '13855556666', '0', 3, now(), '顺丰快递点');

-- 宿舍楼
INSERT INTO `sys_dormitory` (`dormitory_id`, `dormitory_name`, `building_no`, `floor_count`, `location`, `status`, `create_time`, `remark`) VALUES
(1, '东区1号宿舍楼', 'A1', 6, '东区', '0', now(), '男生宿舍'),
(2, '东区2号宿舍楼', 'A2', 6, '东区', '0', now(), '女生宿舍'),
(3, '西区3号宿舍楼', 'B3', 7, '西区', '0', now(), '研究生宿舍');

-- 快递大小分类
INSERT INTO `sys_parcel_size` (`size_id`, `size_name`, `size_desc`, `fee`, `status`, `sort`, `create_time`, `remark`) VALUES
(1, '小件', '信封/文件/小盒子', 2.00, '0', 1, now(), '重量<1kg'),
(2, '中件', '中等纸箱',         3.00, '0', 2, now(), '1-5kg'),
(3, '大件', '大型包裹',         5.00, '0', 3, now(), '5-20kg'),
(4, '超重件', '超重超大件',     8.00, '0', 4, now(), '>20kg');

-- 字典类型
INSERT INTO `sys_dict_type` (`dict_id`, `dict_name`, `dict_type`, `status`, `create_time`, `remark`) VALUES
(1, '订单类型', 'order_type',       '0', now(), '订单类型字典'),
(2, '订单状态', 'order_status',     '0', now(), '订单状态字典'),
(3, '快递公司', 'express_company',  '0', now(), '快递公司字典');

-- 字典数据
INSERT INTO `sys_dict_data` (`dict_code`, `dict_sort`, `dict_label`, `dict_value`, `dict_type`, `status`, `create_time`) VALUES
(1, 1, '帮取', '1', 'order_type', '0', now()),
(2, 2, '帮寄', '2', 'order_type', '0', now()),
(3, 1, '待接单', '0', 'order_status', '0', now()),
(4, 2, '配送中', '1', 'order_status', '0', now()),
(5, 3, '已完成', '2', 'order_status', '0', now()),
(6, 4, '已取消', '3', 'order_status', '0', now()),
(7, 5, '申诉中', '4', 'order_status', '0', now()),
(8, 1, '顺丰速运', '顺丰速运', 'express_company', '0', now()),
(9, 2, '京东物流', '京东物流', 'express_company', '0', now()),
(10, 3, '中通快递', '中通快递', 'express_company', '0', now());
