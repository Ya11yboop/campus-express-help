-- MySQL dump 10.13  Distrib 9.6.0, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: campus_pickup
-- ------------------------------------------------------
-- Server version	9.6.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'ba0bca7b-0a53-11f1-be35-00e04c80bdb0:1-2390';

--
-- Table structure for table `sys_banner`
--

DROP TABLE IF EXISTS `sys_banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_banner` (
  `banner_id` bigint NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `image` varchar(255) NOT NULL COMMENT '图片地址',
  `link_type` char(1) DEFAULT '0' COMMENT '跳转类型(0无 1链接 2页面)',
  `link_url` varchar(255) DEFAULT NULL COMMENT '跳转地址',
  `status` char(1) DEFAULT '0' COMMENT '状态(0启用 1停用)',
  `sort` int DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`banner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='轮播图表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_banner`
--

LOCK TABLES `sys_banner` WRITE;
/*!40000 ALTER TABLE `sys_banner` DISABLE KEYS */;
INSERT INTO `sys_banner` VALUES (1,'校园帮取帮寄','https://xiaoyuanbang-parcel.oss-cn-beijing.aliyuncs.com/campus-pickup/119f15aeb68e467eadacd7fd8e8f5e5b.png','0','','0',1,'2026-10-29 09:24:01',NULL,'首页轮播图1'),(2,'快递驿站','https://xiaoyuanbang-parcel.oss-cn-beijing.aliyuncs.com/campus-pickup/6635af3cac2445a39ea840f8d7506aee.png','0','','0',2,'2026-10-29 09:24:01',NULL,'首页轮播图2'),(3,'学生代取服务','https://xiaoyuanbang-parcel.oss-cn-beijing.aliyuncs.com/campus-pickup/b51daf561b4b41aba419a4f486446248.png','0','','0',3,'2026-10-29 09:24:01',NULL,'首页轮播图3');
/*!40000 ALTER TABLE `sys_banner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_complaint`
--

DROP TABLE IF EXISTS `sys_complaint`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_complaint` (
  `complaint_id` bigint NOT NULL AUTO_INCREMENT COMMENT '申诉/投诉ID',
  `order_id` bigint NOT NULL COMMENT '关联订单ID',
  `school_id` bigint DEFAULT NULL COMMENT '学校ID',
  `user_id` bigint DEFAULT NULL COMMENT '发起人ID',
  `target_user_id` bigint DEFAULT NULL COMMENT '被投诉/申诉用户ID(接单的人)',
  `complaint_type` char(1) DEFAULT '1' COMMENT '类型(1申诉 2投诉)',
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `images` varchar(1000) DEFAULT NULL COMMENT '图片(逗号分隔)',
  `handle_status` char(1) DEFAULT '0' COMMENT '处理状态(0待处理 1已处理)',
  `handle_result` varchar(500) DEFAULT NULL COMMENT '处理结果/审核结果',
  `handler_id` bigint DEFAULT NULL COMMENT '处理人ID',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`complaint_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='申诉/投诉表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_complaint`
--

LOCK TABLES `sys_complaint` WRITE;
/*!40000 ALTER TABLE `sys_complaint` DISABLE KEYS */;
INSERT INTO `sys_complaint` VALUES (100,100,NULL,6,NULL,'1','????',NULL,'0',NULL,NULL,NULL,'2026-10-29 10:43:06',NULL),(101,101,NULL,1,1,'1','666',NULL,'1','扣信誉分-2',1,'2026-10-29 11:10:37','2026-10-29 11:10:00',NULL),(102,101,NULL,1,1,'2','777',NULL,'0',NULL,NULL,NULL,'2026-10-29 11:10:04',NULL),(103,108,NULL,1,1,'2','bux',NULL,'0',NULL,NULL,NULL,'2026-11-03 09:55:07',NULL),(104,108,NULL,113,1,'2','bux',NULL,'1','接单人信誉分-1',1,'2026-11-03 09:57:46','2026-11-03 09:56:52',NULL),(105,110,NULL,113,1,'2','包裹未到',NULL,'1','接单人信誉分-2',1,'2026-11-05 14:17:13','2026-11-05 14:16:52',NULL);
/*!40000 ALTER TABLE `sys_complaint` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_courier`
--

DROP TABLE IF EXISTS `sys_courier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_courier` (
  `courier_id` bigint NOT NULL AUTO_INCREMENT COMMENT '跑腿人员ID',
  `user_id` bigint NOT NULL COMMENT '关联用户ID',
  `real_name` varchar(30) NOT NULL COMMENT '真实姓名',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `service_area` varchar(100) DEFAULT NULL COMMENT '服务区域',
  `school_id` bigint DEFAULT NULL COMMENT '学校ID',
  `audit_status` char(1) DEFAULT '0' COMMENT '审核状态(0待审核 1通过 2驳回)',
  `audit_result` varchar(200) DEFAULT NULL COMMENT '审核结果/说明',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `status` char(1) DEFAULT '0' COMMENT '工作状态(0在岗 1休息)',
  `score` decimal(3,2) DEFAULT '5.00' COMMENT '服务评分',
  `order_count` int DEFAULT '0' COMMENT '完成订单数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`courier_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='跑腿人员表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_courier`
--

LOCK TABLES `sys_courier` WRITE;
/*!40000 ALTER TABLE `sys_courier` DISABLE KEYS */;
INSERT INTO `sys_courier` VALUES (1,5,'赵跑腿','110101199001011234','13900000001','东区宿舍',100,'1',NULL,NULL,'0',5.00,0,'2026-10-27 20:45:43',NULL,'示例跑腿人员');
/*!40000 ALTER TABLE `sys_courier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_credit_apply`
--

DROP TABLE IF EXISTS `sys_credit_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_credit_apply` (
  `apply_id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `user_id` bigint NOT NULL COMMENT '被扣分用户ID',
  `change_score` int DEFAULT '0' COMMENT '变更分数(负数=扣除)',
  `reason` varchar(200) DEFAULT NULL COMMENT '申请原因',
  `apply_status` char(1) DEFAULT '0' COMMENT '审核状态(0待审核 1通过 2驳回)',
  `apply_user_id` bigint DEFAULT NULL COMMENT '申请人ID(客服)',
  `audit_user_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_time` datetime DEFAULT NULL COMMENT '申请时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '审核备注/驳回原因',
  PRIMARY KEY (`apply_id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_apply_status` (`apply_status`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='信誉分扣除申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_credit_apply`
--

LOCK TABLES `sys_credit_apply` WRITE;
/*!40000 ALTER TABLE `sys_credit_apply` DISABLE KEYS */;
INSERT INTO `sys_credit_apply` VALUES (100,6,-10,'??????','1',3,1,'2026-10-29 10:42:55','2026-10-29 10:42:55',''),(101,6,-5,'????','2',3,1,'2026-10-29 10:43:06','2026-10-29 10:43:06','??????'),(102,1,-1,'测试','1',1,1,'2026-10-29 10:55:34','2026-10-29 10:54:55',''),(103,1,-1,'有人投诉','1',1,1,'2026-11-03 09:58:21','2026-11-03 09:58:16',''),(104,1,-2,'用户未收到包裹','1',1,1,'2026-11-05 14:20:46','2026-11-05 14:20:40','');
/*!40000 ALTER TABLE `sys_credit_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_credit_log`
--

DROP TABLE IF EXISTS `sys_credit_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_credit_log` (
  `log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `school_id` bigint DEFAULT NULL COMMENT '学校ID',
  `change_score` int DEFAULT '0' COMMENT '变更分数(可正负)',
  `credit_score` int DEFAULT '0' COMMENT '变更后信誉分',
  `reason` varchar(200) DEFAULT NULL COMMENT '原因',
  `operator_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `create_time` datetime DEFAULT NULL COMMENT '记录时间',
  PRIMARY KEY (`log_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='信誉分变更记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_credit_log`
--

LOCK TABLES `sys_credit_log` WRITE;
/*!40000 ALTER TABLE `sys_credit_log` DISABLE KEYS */;
INSERT INTO `sys_credit_log` VALUES (100,6,100,-10,90,'??????',1,'2026-10-29 10:42:55'),(101,1,100,-1,99,'测试',1,'2026-10-29 10:55:34'),(102,1,103,-1,98,'有人投诉',1,'2026-11-03 09:58:21'),(103,1,103,-2,96,'用户未收到包裹',1,'2026-11-05 14:20:46');
/*!40000 ALTER TABLE `sys_credit_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典数据ID',
  `dict_sort` int DEFAULT '0' COMMENT '排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`),
  KEY `idx_dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
INSERT INTO `sys_dict_data` VALUES (1,1,'帮取','1','order_type','0','2026-10-27 20:45:43',NULL,NULL),(2,2,'帮寄','2','order_type','0','2026-10-27 20:45:43',NULL,NULL),(3,1,'待接单','0','order_status','0','2026-10-27 20:45:43',NULL,NULL),(4,2,'配送中','1','order_status','0','2026-10-27 20:45:43',NULL,NULL),(5,3,'已完成','2','order_status','0','2026-10-27 20:45:43',NULL,NULL),(6,4,'已取消','3','order_status','0','2026-10-27 20:45:43',NULL,NULL),(7,5,'申诉中','4','order_status','0','2026-10-27 20:45:43',NULL,NULL),(8,1,'顺丰速运','顺丰速运','express_company','0','2026-10-27 20:45:43',NULL,NULL),(9,2,'京东物流','京东物流','express_company','0','2026-10-27 20:45:43',NULL,NULL),(10,3,'中通快递','中通快递','express_company','0','2026-10-27 20:45:43',NULL,NULL);
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典类型ID',
  `dict_name` varchar(100) NOT NULL COMMENT '字典名称',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`),
  UNIQUE KEY `uk_dict_type` (`dict_type`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
INSERT INTO `sys_dict_type` VALUES (1,'订单类型','order_type','0','2026-10-27 20:45:43',NULL,'订单类型字典'),(2,'订单状态','order_status','0','2026-10-27 20:45:43',NULL,'订单状态字典'),(3,'快递公司','express_company','0','2026-10-27 20:45:43',NULL,'快递公司字典');
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dormitory`
--

DROP TABLE IF EXISTS `sys_dormitory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dormitory` (
  `dormitory_id` bigint NOT NULL AUTO_INCREMENT COMMENT '宿舍楼ID',
  `dormitory_name` varchar(50) NOT NULL COMMENT '宿舍楼名称',
  `school_id` bigint DEFAULT NULL COMMENT '学校ID',
  `building_no` varchar(20) DEFAULT NULL COMMENT '楼栋号',
  `floor_count` int DEFAULT NULL COMMENT '楼层数',
  `location` varchar(100) DEFAULT NULL COMMENT '位置',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dormitory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿舍楼表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dormitory`
--

LOCK TABLES `sys_dormitory` WRITE;
/*!40000 ALTER TABLE `sys_dormitory` DISABLE KEYS */;
INSERT INTO `sys_dormitory` VALUES (1,'东区1号宿舍楼',100,'A1',6,'东区','0','2026-10-27 20:45:43',NULL,'男生宿舍'),(2,'东区2号宿舍楼',100,'A2',6,'东区','0','2026-10-27 20:45:43',NULL,'女生宿舍'),(3,'西区3号宿舍楼',100,'B3',7,'西区','0','2026-10-27 20:45:43',NULL,'研究生宿舍'),(100,'示例大学1号楼',100,'A1',6,'示例大学','0','2026-10-28 21:42:45',NULL,NULL),(101,'北京大学1号楼',101,'B1',6,'北京大学','0','2026-10-28 21:42:45',NULL,NULL),(102,'清华大学1号楼',102,'C1',7,'清华大学','0','2026-10-28 21:42:45',NULL,NULL);
/*!40000 ALTER TABLE `sys_dormitory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_evaluate`
--

DROP TABLE IF EXISTS `sys_evaluate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_evaluate` (
  `evaluate_id` bigint NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `school_id` bigint DEFAULT NULL COMMENT '学校ID',
  `user_id` bigint DEFAULT NULL COMMENT '评价人ID',
  `courier_id` bigint DEFAULT NULL COMMENT '被评价人ID',
  `score` tinyint DEFAULT '5' COMMENT '评分(1-5)',
  `content` varchar(500) DEFAULT NULL COMMENT '评价内容',
  `reply` varchar(500) DEFAULT NULL COMMENT '客服回复',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `create_time` datetime DEFAULT NULL COMMENT '评价时间',
  PRIMARY KEY (`evaluate_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评价表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_evaluate`
--

LOCK TABLES `sys_evaluate` WRITE;
/*!40000 ALTER TABLE `sys_evaluate` DISABLE KEYS */;
INSERT INTO `sys_evaluate` VALUES (100,101,NULL,1,NULL,4,'bux','谢谢','2026-10-29 11:36:49','2026-10-29 11:36:38'),(101,108,NULL,1,NULL,5,'666',NULL,NULL,'2026-11-03 09:54:37');
/*!40000 ALTER TABLE `sys_evaluate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_express_point`
--

DROP TABLE IF EXISTS `sys_express_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_express_point` (
  `point_id` bigint NOT NULL AUTO_INCREMENT COMMENT '驿站ID',
  `point_name` varchar(50) NOT NULL COMMENT '驿站名称',
  `school_id` bigint DEFAULT NULL COMMENT '学校ID',
  `location` varchar(100) DEFAULT NULL COMMENT '具体位置',
  `manager_name` varchar(30) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `status` char(1) DEFAULT '0' COMMENT '状态(0营业 1停用)',
  `sort` int DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`point_id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='驿站表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_express_point`
--

LOCK TABLES `sys_express_point` WRITE;
/*!40000 ALTER TABLE `sys_express_point` DISABLE KEYS */;
INSERT INTO `sys_express_point` VALUES (1,'菜鸟驿站(东区)',100,'东区宿舍3号楼一层','王站长','13811112222','0',1,'2026-10-27 20:45:43',NULL,'代收代寄'),(2,'京东派(西区)',100,'西区体育馆旁','李站长','13833334444','0',2,'2026-10-27 20:45:43',NULL,'京东自营'),(3,'顺丰速运点',100,'北门商业街','赵站长','13855556666','0',3,'2026-10-27 20:45:43',NULL,'顺丰快递点'),(101,'菜鸟驿站(示例大学)',100,'示例大学东区','张站长','13800001111','0',1,'2026-10-28 21:42:45',NULL,NULL),(102,'菜鸟驿站(北京大学)',101,'北京大学南门','李站长','13800002222','0',1,'2026-10-28 21:42:45',NULL,NULL),(103,'菜鸟驿站(清华大学)',102,'清华大学西门','王站长','13800003333','0',1,'2026-10-28 21:42:45',NULL,NULL),(104,'菜鸟驿站(东门)',103,'东门食堂一楼','陈站长','13900001111','0',1,'2026-11-03 09:37:44',NULL,'代收代寄'),(105,'京东派送点',103,'二号宿舍楼下','刘站长','13900002222','0',2,'2026-11-03 09:37:44',NULL,'京东自营'),(106,'顺丰速运点',103,'图书馆北侧','黄站长','13900003333','0',3,'2026-11-03 09:37:44',NULL,'顺丰快递点'),(107,'菜鸟驿站(南门)',103,'南门商业街','李站长','13900004444','0',4,'2026-11-03 09:37:44',NULL,'代收点');
/*!40000 ALTER TABLE `sys_express_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_finish_apply`
--

DROP TABLE IF EXISTS `sys_finish_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_finish_apply` (
  `apply_id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `courier_id` bigint DEFAULT NULL COMMENT '接单人ID',
  `image` varchar(500) DEFAULT NULL COMMENT '配送完成照片地址',
  `apply_status` char(1) DEFAULT '0' COMMENT '审核状态(0待审核 1通过 2驳回)',
  `remark` varchar(500) DEFAULT NULL COMMENT '审核备注/驳回原因',
  `audit_user_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_time` datetime DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`apply_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_apply_status` (`apply_status`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='配送完成申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_finish_apply`
--

LOCK TABLES `sys_finish_apply` WRITE;
/*!40000 ALTER TABLE `sys_finish_apply` DISABLE KEYS */;
INSERT INTO `sys_finish_apply` VALUES (100,101,1,'http://localhost:8080/uploads/test.jpg','1','',1,'2026-10-29 11:34:37','2026-10-29 11:34:37'),(101,102,1,'','2','未完成',1,'2026-10-29 11:47:13','2026-10-29 11:46:46'),(102,108,1,'','2','',1,'2026-11-03 09:53:59','2026-11-03 09:53:08'),(103,108,1,'','1','',1,'2026-11-03 09:54:18','2026-11-03 09:54:07'),(104,108,1,'','1','',1,'2026-11-03 09:54:27','2026-11-03 09:54:22'),(105,110,1,'','2','',1,'2026-11-05 14:15:29','2026-11-05 14:15:16'),(106,110,1,'','1','',1,'2026-11-05 14:15:47','2026-11-05 14:15:39');
/*!40000 ALTER TABLE `sys_finish_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` bigint NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `title` varchar(100) NOT NULL COMMENT '标题',
  `content` text COMMENT '内容',
  `notice_type` char(1) DEFAULT '1' COMMENT '类型(1通知 2公告)',
  `school_id` bigint DEFAULT NULL COMMENT '学校ID',
  `status` char(1) DEFAULT '0' COMMENT '状态(0发布 1下线)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
INSERT INTO `sys_notice` VALUES (1,'校园快递系统上线通知','校园快递帮取帮寄服务已正式上线，同学们可在小程序下单，由跑腿人员代取代寄快递。','1',NULL,'0','2026-10-29 09:24:01',NULL,'测试公告'),(2,'双十一快递高峰提醒','近期快递量激增，请同学们错峰取件，驿站已加派人员处理包裹。','2',NULL,'0','2026-10-29 09:24:01',NULL,'测试公告'),(3,'驿站营业时间调整','即日起东区驿站营业时间调整为 9:00-21:00，请合理安排取件时间。','2',NULL,'0','2026-10-29 09:24:01',NULL,'测试公告'),(4,'安全取件提醒','取件时请携带有效证件（学生证/身份证），代取请提前在订单中填写收件人信息。','1',NULL,'0','2026-10-29 09:24:01',NULL,'测试公告');
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notification`
--

DROP TABLE IF EXISTS `sys_notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notification` (
  `notif_id` bigint NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `user_id` bigint NOT NULL COMMENT '接收用户ID',
  `title` varchar(100) DEFAULT NULL COMMENT '标题',
  `content` varchar(500) DEFAULT NULL COMMENT '内容',
  `read_status` char(1) DEFAULT '0' COMMENT '已读状态(0未读 1已读)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`notif_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户通知表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notification`
--

LOCK TABLES `sys_notification` WRITE;
/*!40000 ALTER TABLE `sys_notification` DISABLE KEYS */;
INSERT INTO `sys_notification` VALUES (100,1,'配送完成申请','你的订单 CP17932455861955247 的配送员已申请配送完成，请留意。','1','2026-10-29 11:46:46'),(101,113,'配送完成申请','你的订单 CP17936702128060671 的配送员已申请配送完成，请留意。','1','2026-11-03 09:53:08'),(102,113,'配送完成申请','你的订单 CP17936702128060671 的配送员已申请配送完成，请留意。','1','2026-11-03 09:54:07'),(103,113,'配送完成申请','你的订单 CP17936702128060671 的配送员已申请配送完成，请留意。','1','2026-11-03 09:54:22'),(104,113,'配送完成申请','你的订单 CP17938592333608793 的配送员已申请配送完成，请留意。','1','2026-11-05 14:15:17'),(105,113,'配送完成申请','你的订单 CP17938592333608793 的配送员已申请配送完成，请留意。','1','2026-11-05 14:15:39');
/*!40000 ALTER TABLE `sys_notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_order`
--

DROP TABLE IF EXISTS `sys_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_order` (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(40) NOT NULL COMMENT '订单编号',
  `user_id` bigint NOT NULL COMMENT '下单用户ID',
  `courier_id` bigint DEFAULT NULL COMMENT '接单人ID(跑腿人员/用户)',
  `order_type` char(1) NOT NULL COMMENT '订单类型(1帮取 2帮寄)',
  `tracking_no` varchar(50) DEFAULT NULL COMMENT '快递单号',
  `express_company` varchar(30) DEFAULT NULL COMMENT '快递公司',
  `size_id` bigint DEFAULT NULL COMMENT '快递大小分类ID',
  `point_id` bigint DEFAULT NULL COMMENT '取件驿站ID',
  `school_id` bigint DEFAULT NULL COMMENT '学校ID',
  `pickup_address` varchar(200) DEFAULT NULL COMMENT '取件地址',
  `delivery_address` varchar(200) DEFAULT NULL COMMENT '送达地址(宿舍楼/房间)',
  `receiver_name` varchar(30) DEFAULT NULL COMMENT '收件人姓名',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '收件人电话',
  `fee` decimal(10,2) DEFAULT '0.00' COMMENT '配送费',
  `order_status` char(1) DEFAULT '0' COMMENT '状态(0待接单 1配送中 2已完成 3已取消 4申诉中)',
  `create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `accept_time` datetime DEFAULT NULL COMMENT '接单时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_courier_id` (`courier_id`),
  KEY `idx_order_status` (`order_status`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_order`
--

LOCK TABLES `sys_order` WRITE;
/*!40000 ALTER TABLE `sys_order` DISABLE KEYS */;
INSERT INTO `sys_order` VALUES (100,'CP17931084901530809',6,5,'1','TEST123','SF',1,1,100,NULL,'Dorm1-302','Qian','13700000002',3.00,'2','2026-10-27 21:41:30','2026-10-27 21:41:30','2026-10-27 21:41:30','test'),(101,'CP17932408226732912',1,1,'1','A202','顺丰速运',1,1,100,NULL,'404','韦大贵','13185647456',2.00,'2','2026-10-29 10:27:03','2026-10-29 11:02:32','2026-10-29 11:34:37',''),(102,'CP17932455861955247',1,1,'1','W002','顺丰快递',1,1,100,NULL,'404','韦大贵','13185647456',3.00,'1','2026-10-29 11:46:26','2026-10-29 11:46:40',NULL,''),(103,'CP17932667733767208',1,1,'1','A303','',2,1,100,NULL,'404','韦大贵','13185647456',5.00,'3','2026-10-29 17:39:33','2026-10-29 17:39:39',NULL,''),(104,'CP17932668505663637',1,NULL,'1','A404','',1,1,100,NULL,'404','韦大贵','13185647456',2.00,'3','2026-10-29 17:40:51',NULL,NULL,''),(107,'CP17932684345853489',1,NULL,'1','5646','',3,1,100,NULL,'404','韦大贵','13185647456',6.00,'0','2026-10-29 18:07:15',NULL,NULL,''),(108,'CP17936702128060671',113,1,'1','A2006','顺丰速运',1,104,103,NULL,'2栋666','罗涛','18164578998',1.00,'2','2026-11-03 09:43:33','2026-11-03 09:53:03','2026-11-03 09:54:27',''),(109,'CP17936711705236631',113,NULL,'1','D3322','顺丰速运',4,104,103,NULL,'2栋666','罗涛','18164578998',5.00,'0','2026-11-03 09:59:31',NULL,NULL,''),(110,'CP17938592333608793',113,1,'1','A404','顺丰速运',1,104,103,NULL,'2栋666','罗涛','18164578998',1.00,'2','2026-11-05 14:13:53','2026-11-05 14:14:44','2026-11-05 14:15:47','');
/*!40000 ALTER TABLE `sys_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_order_withdraw_apply`
--

DROP TABLE IF EXISTS `sys_order_withdraw_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_order_withdraw_apply` (
  `apply_id` bigint NOT NULL AUTO_INCREMENT COMMENT '申请ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `courier_id` bigint DEFAULT NULL COMMENT '接单人ID(申请人)',
  `reason` varchar(500) DEFAULT NULL COMMENT '撤回理由',
  `apply_status` char(1) DEFAULT '0' COMMENT '审核状态(0待审核 1通过 2驳回)',
  `audit_user_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '审核备注/驳回原因',
  `create_time` datetime DEFAULT NULL COMMENT '申请时间',
  PRIMARY KEY (`apply_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_apply_status` (`apply_status`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='接单撤回申请表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_order_withdraw_apply`
--

LOCK TABLES `sys_order_withdraw_apply` WRITE;
/*!40000 ALTER TABLE `sys_order_withdraw_apply` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_order_withdraw_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_parcel_size`
--

DROP TABLE IF EXISTS `sys_parcel_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_parcel_size` (
  `size_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `size_name` varchar(30) NOT NULL COMMENT '分类名称(小件/中件/大件/超重件)',
  `size_desc` varchar(100) DEFAULT NULL COMMENT '说明',
  `fee` decimal(10,2) DEFAULT '0.00' COMMENT '基础配送费',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `sort` int DEFAULT '0' COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`size_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='快递大小分类表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_parcel_size`
--

LOCK TABLES `sys_parcel_size` WRITE;
/*!40000 ALTER TABLE `sys_parcel_size` DISABLE KEYS */;
INSERT INTO `sys_parcel_size` VALUES (1,'小件','信封/文件/小盒子',2.00,'0',1,'2026-10-27 20:45:43',NULL,'重量<1kg'),(2,'中件','中等纸箱',3.00,'0',2,'2026-10-27 20:45:43',NULL,'1-5kg'),(3,'大件','大型包裹',5.00,'0',3,'2026-10-27 20:45:43',NULL,'5-20kg'),(4,'超重件','超重超大件',8.00,'0',4,'2026-10-27 20:45:43',NULL,'>20kg');
/*!40000 ALTER TABLE `sys_parcel_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int DEFAULT '0' COMMENT '显示顺序',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `uk_role_key` (`role_key`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'超级系统管理员','super_admin',1,'0','2026-10-27 20:45:43',NULL,'拥有后台所有功能模块权限'),(2,'运营管理员','operation_admin',2,'0','2026-10-27 20:45:43',NULL,'学生/跑腿/订单/评价/公告/轮播图/数据流图'),(3,'订单客服管理员','cs_admin',3,'0','2026-10-27 20:45:43',NULL,'订单查看/申诉投诉/信誉分/评价回复'),(4,'基础数据管理员','base_admin',4,'0','2026-10-27 20:45:43',NULL,'驿站/宿舍楼/快递大小分类/数据字典'),(5,'跑腿人员','courier',5,'0','2026-10-27 20:45:43',NULL,'接单配送'),(6,'普通用户','user',6,'0','2026-10-27 20:45:43',NULL,'学生，使用微信小程序'),(7,'校区管理员','campus_admin',7,'0','2026-10-28 21:30:38',NULL,'只能管理本校数据'),(8,'学院运营管理员','campus_operation_admin',8,'0','2026-10-28 22:40:19',NULL,'本学院运营模块权限，仅限本学院数据'),(9,'学院客服管理员','campus_cs_admin',9,'0','2026-10-28 22:40:19',NULL,'本学院客服模块权限，仅限本学院数据'),(10,'学院基础数据管理员','campus_base_admin',10,'0','2026-10-28 22:40:19',NULL,'本学院基础数据模块权限，仅限本学院数据');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_school`
--

DROP TABLE IF EXISTS `sys_school`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_school` (
  `school_id` bigint NOT NULL AUTO_INCREMENT COMMENT '学校ID',
  `school_name` varchar(50) NOT NULL COMMENT '学校名称',
  `school_code` varchar(30) DEFAULT NULL COMMENT '学校编码',
  `address` varchar(100) DEFAULT NULL COMMENT '学校地址',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`school_id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学校表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_school`
--

LOCK TABLES `sys_school` WRITE;
/*!40000 ALTER TABLE `sys_school` DISABLE KEYS */;
INSERT INTO `sys_school` VALUES (100,'示例大学','DEMO001','示例市示例区','0','2026-10-28 21:12:23',NULL,'初始学校'),(101,'北京大学','PKU001','北京市海淀区','0','2026-10-28 21:12:23',NULL,''),(102,'清华大学','THU001','北京市海淀区','0','2026-10-28 21:12:23',NULL,''),(103,'贵阳信息科技学院','550025','贵州省贵阳市贵安新区花溪大学城思雅路8号','0','2026-10-28 21:14:49',NULL,NULL);
/*!40000 ALTER TABLE `sys_school` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `openid` varchar(64) DEFAULT NULL COMMENT '微信openid',
  `user_name` varchar(30) NOT NULL COMMENT '用户名(登录账号)',
  `nick_name` varchar(30) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(30) DEFAULT NULL COMMENT '真实姓名',
  `password` varchar(100) DEFAULT '' COMMENT '密码(MD5加密)',
  `school` varchar(50) DEFAULT NULL COMMENT '学校',
  `school_id` bigint DEFAULT NULL COMMENT '学校ID',
  `student_no` varchar(30) DEFAULT NULL COMMENT '学号',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像地址',
  `gender` char(1) DEFAULT '2' COMMENT '性别(0男 1女 2未知)',
  `dormitory_id` bigint DEFAULT NULL COMMENT '宿舍楼ID',
  `room_no` varchar(30) DEFAULT NULL COMMENT '宿舍房间号',
  `credit_score` int DEFAULT '100' COMMENT '信誉分',
  `status` char(1) DEFAULT '0' COMMENT '状态(0正常 1停用)',
  `user_type` varchar(20) DEFAULT 'user' COMMENT '用户类型(admin/courier/user)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `uk_user_name` (`user_name`),
  UNIQUE KEY `uk_openid` (`openid`),
  KEY `idx_student_no` (`student_no`),
  KEY `idx_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,NULL,'LT','超级管理员','涛哥','bf9eeb6625b113b0151fb3713e7d40b5','示例大学',103,'24136502928','100155200512125566','18155668899',NULL,NULL,'2',NULL,NULL,96,'0','admin','2026-10-27 20:45:43','2026-11-05 14:27:58','超级系统管理员'),(2,NULL,'operation','运营管理员','运营小张','e10adc3949ba59abbe56e057f20f883e','示例大学',103,NULL,NULL,'13800000001',NULL,NULL,'2',NULL,NULL,100,'0','admin','2026-10-27 20:45:43','2026-10-28 21:44:59','运营管理员'),(3,NULL,'cs','客服管理员','客服小李','e10adc3949ba59abbe56e057f20f883e','示例大学',100,NULL,NULL,'13800000002',NULL,NULL,'2',NULL,NULL,100,'0','admin','2026-10-27 20:45:43',NULL,'订单客服管理员'),(4,NULL,'base','基础数据员','数据小王','e10adc3949ba59abbe56e057f20f883e','示例大学',100,NULL,NULL,'13800000003',NULL,NULL,'2',NULL,NULL,100,'0','admin','2026-10-27 20:45:43',NULL,'基础数据管理员'),(5,NULL,'courier01','快递员小赵','赵跑腿','e10adc3949ba59abbe56e057f20f883e','示例大学',103,NULL,'110101199001011234','13900000001',NULL,NULL,'2',NULL,NULL,100,'0','courier','2026-10-27 20:45:43','2026-10-28 21:16:32','跑腿人员'),(6,NULL,'student01','学生小钱','???','e10adc3949ba59abbe56e057f20f883e','示例大学',100,'20230001','110101200501011234','13700000002',NULL,NULL,'2',NULL,NULL,90,'0','user','2026-10-27 20:45:43','2026-10-29 18:31:10','普通学生'),(100,NULL,'campus_demo','示例校区管理员','示例管理员','e10adc3949ba59abbe56e057f20f883e',NULL,100,NULL,NULL,NULL,NULL,NULL,'2',NULL,NULL,100,'0','admin','2026-10-28 21:42:45',NULL,'示例大学校区管理员'),(101,NULL,'campus_pku','北大校区管理员','北大管理员','e10adc3949ba59abbe56e057f20f883e',NULL,101,NULL,NULL,NULL,NULL,NULL,'2',NULL,NULL,100,'0','admin','2026-10-28 21:42:45',NULL,'北京大学校区管理员'),(102,NULL,'campus_thu','清华校区管理员','清华管理员','e10adc3949ba59abbe56e057f20f883e',NULL,102,NULL,NULL,NULL,NULL,NULL,'2',NULL,NULL,100,'0','admin','2026-10-28 21:42:45',NULL,'清华大学校区管理员'),(103,NULL,'campus_op_demo','学院运营管理员','示例运营','e10adc3949ba59abbe56e057f20f883e','示例大学',100,NULL,NULL,'1380000103',NULL,NULL,'2',NULL,NULL,100,'0','admin','2026-10-28 22:40:19',NULL,'示例大学运营管理员'),(104,NULL,'campus_cs_demo','学院客服管理员','示例客服','e10adc3949ba59abbe56e057f20f883e','示例大学',100,NULL,NULL,'1380000104',NULL,NULL,'2',NULL,NULL,100,'0','admin','2026-10-28 22:40:19',NULL,'示例大学客服管理员'),(105,NULL,'campus_base_demo','学院基础数据管理员','示例数据','e10adc3949ba59abbe56e057f20f883e','示例大学',100,NULL,NULL,'1380000105',NULL,NULL,'2',NULL,NULL,100,'0','admin','2026-10-28 22:40:19',NULL,'示例大学基础数据管理员'),(109,NULL,'stu_alice','爱丽丝','艾丽','e10adc3949ba59abbe56e057f20f883e','示例大学',100,'S2026001','110101200001011234','13800001111',NULL,NULL,'2',NULL,NULL,100,'0','user','2026-10-29 18:39:37',NULL,'测试学生1'),(110,NULL,'stu_bob','鲍勃','鲍勃','e10adc3949ba59abbe56e057f20f883e','示例大学',100,'S2026002','110101200002011234','13800002222',NULL,NULL,'2',NULL,NULL,100,'0','user','2026-10-29 18:39:37',NULL,'测试学生2'),(111,NULL,'stu_carol','卡罗尔','卡罗','e10adc3949ba59abbe56e057f20f883e','贵阳信息科技学院',103,'S2026003','110101200003011234','13800003333',NULL,NULL,'2',NULL,NULL,100,'0','user','2026-10-29 18:39:37',NULL,'测试学生3(另一学校)'),(112,'oDemo_wx_openid_001','wxoDemo_wx_openid_001',NULL,NULL,'',NULL,103,NULL,NULL,NULL,NULL,NULL,'2',NULL,NULL,100,'0','user','2026-10-29 18:39:37','2026-11-03 09:51:21','微信未完善示例'),(113,'o11FY1199RG6ZqLRh-WtmbHFzlb0','admin123',NULL,'yj','c71a4d4fb1e8b1dbdb7146b44f82ee45',NULL,103,'24136502914','522624200505230076','18286563722',NULL,NULL,NULL,NULL,NULL,100,'0','user','2026-10-29 18:40:13','2026-11-03 09:51:13',NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_address`
--

DROP TABLE IF EXISTS `sys_user_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_address` (
  `address_id` bigint NOT NULL AUTO_INCREMENT COMMENT '地址ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `contact_name` varchar(30) DEFAULT NULL COMMENT '联系人姓名',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '详细地址',
  `is_default` char(1) DEFAULT '0' COMMENT '是否默认(0否 1是)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`address_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户收货地址表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_address`
--

LOCK TABLES `sys_user_address` WRITE;
/*!40000 ALTER TABLE `sys_user_address` DISABLE KEYS */;
INSERT INTO `sys_user_address` VALUES (100,6,'???','13700000002','??1??302','1','2026-10-29 09:55:20',NULL),(101,1,'罗涛','13185647456','303','1','2026-10-29 09:59:13',NULL),(102,1,'韦大贵','13185647456','404','0','2026-10-29 10:16:29',NULL),(103,113,'罗涛','18164578998','2栋666','1','2026-11-03 09:33:04',NULL);
/*!40000 ALTER TABLE `sys_user_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(100,7),(101,7),(102,7),(103,8),(104,9),(105,10),(109,6),(110,6),(111,6),(112,6),(113,6);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-11-05 16:35:42
