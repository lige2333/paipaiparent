/*
Navicat MySQL Data Transfer

Source Server         : java10
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : paipai

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2020-04-16 00:37:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `receiver` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES ('-1', '', '请完善地址信息', '', '', '', '', '');
INSERT INTO `address` VALUES ('5', '40afd21c-9866-4920-b70b-f4b950baf889', '北京', '县', '延庆县', '不才路', '不才', '13212345678');
INSERT INTO `address` VALUES ('6', 'e573e826-e50f-49da-80a3-e7bc8041711f', '广西', '南宁市', '兴宁区', '不才路', '不才', '13212345678');
INSERT INTO `address` VALUES ('7', 'b84977aa-d771-4efc-b196-fa6a1f6c230a', '陕西', '西安市', '市辖区', '不才路', 'lige2333', '13212345678');

-- ----------------------------
-- Table structure for bid
-- ----------------------------
DROP TABLE IF EXISTS `bid`;
CREATE TABLE `bid` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_id` int(11) DEFAULT NULL,
  `bid_price` decimal(10,2) DEFAULT NULL,
  `buyer_id` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bid
-- ----------------------------
INSERT INTO `bid` VALUES ('79', '74', '121.00', '7', '2020-01-08 15:02:15', '2');
INSERT INTO `bid` VALUES ('80', '74', '141.00', '5', '2020-01-08 15:04:20', '1');
INSERT INTO `bid` VALUES ('81', '87', '10000.00', '7', '2020-01-08 16:34:10', '0');
INSERT INTO `bid` VALUES ('82', '87', '10005.00', '7', '2020-01-08 19:51:17', '0');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '书籍');
INSERT INTO `category` VALUES ('2', '电子');
INSERT INTO `category` VALUES ('3', '衣装');
INSERT INTO `category` VALUES ('4', '房产');
INSERT INTO `category` VALUES ('5', '美妆');

-- ----------------------------
-- Table structure for code
-- ----------------------------
DROP TABLE IF EXISTS `code`;
CREATE TABLE `code` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rancode` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `expire_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of code
-- ----------------------------
INSERT INTO `code` VALUES ('8', '633664', '13212345678', '2019-11-28 12:29:02');
INSERT INTO `code` VALUES ('10', '233400', '13212345678', '2019-11-28 14:45:52');
INSERT INTO `code` VALUES ('11', '341911', '18702171092', '2019-11-28 14:50:26');
INSERT INTO `code` VALUES ('12', '879646', '18702171092', '2019-11-28 15:03:48');
INSERT INTO `code` VALUES ('13', '957272', '18516728003', '2019-11-28 16:24:06');
INSERT INTO `code` VALUES ('14', '407071', '18516728003', '2019-11-28 16:26:04');
INSERT INTO `code` VALUES ('15', '110574', '18516728003', '2019-11-28 16:30:54');
INSERT INTO `code` VALUES ('16', '069850', '18516728003', '2019-11-28 16:34:27');
INSERT INTO `code` VALUES ('17', '124561', '18702171092', '2019-11-28 16:53:37');
INSERT INTO `code` VALUES ('18', '160727', '18516728003', '2019-11-28 17:21:37');
INSERT INTO `code` VALUES ('19', '181279', '18516728003', '2019-11-28 17:22:41');
INSERT INTO `code` VALUES ('20', '637169', '18516728003', '2019-11-28 17:24:03');
INSERT INTO `code` VALUES ('21', '497114', '13212345678', '2019-11-28 17:24:30');
INSERT INTO `code` VALUES ('22', '059244', '18516728003', '2019-11-28 18:10:52');
INSERT INTO `code` VALUES ('23', '701360', '18621518173', '2019-11-28 21:32:43');
INSERT INTO `code` VALUES ('24', '578513', '18702171092', '2019-12-02 18:40:19');
INSERT INTO `code` VALUES ('25', '810961', '18702171092', '2019-12-05 15:47:22');
INSERT INTO `code` VALUES ('26', '004747', '18516728003', '2019-12-05 15:48:14');
INSERT INTO `code` VALUES ('27', '288514', '18702171092', '2019-12-06 11:16:59');
INSERT INTO `code` VALUES ('28', '826664', '18200679088', '2019-12-06 11:19:25');
INSERT INTO `code` VALUES ('29', '442329', '18300679088', '2019-12-06 11:19:37');

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `collection`;
CREATE TABLE `collection` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `proid` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collection
-- ----------------------------
INSERT INTO `collection` VALUES ('6', '87', '7');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` varchar(255) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  `buyer_id` int(11) DEFAULT NULL,
  `seller_name` varchar(255) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `address_id` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('a4a68341-5481-48de-af7c-f90aa5a61f42', '74', '5', '123456', '141.00', '-1', '0');

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_id` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `payment_status` int(255) DEFAULT NULL,
  `product_id` int(255) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of payment
-- ----------------------------
INSERT INTO `payment` VALUES ('46', 'e6741c5a-ea66-4ba6-84fa-0acff5b902dd', '7', '0', '88', '10.00');
INSERT INTO `payment` VALUES ('47', 'adef3a87-436e-479d-9bb1-0cc67e01fd84', '7', '1', '88', '10.00');
INSERT INTO `payment` VALUES ('48', '7a9d52e0-87b4-4780-97ae-d3a6d9a5d352', '7', '0', '74', '10.00');
INSERT INTO `payment` VALUES ('49', 'bc771b1a-7db9-43ac-a24f-da9fc62328c4', '7', '3', '74', '10.00');
INSERT INTO `payment` VALUES ('50', '412a1aa7-a29f-4b8f-be39-5fd384797fb8', '5', '1', '74', '10.00');
INSERT INTO `payment` VALUES ('51', '889d0087-60b3-43f3-a304-dd3e0506acc4', '9', '0', '70', '25.00');
INSERT INTO `payment` VALUES ('52', '40804412-c9e0-4984-9cf5-194145afab6e', '9', '0', '59', '10.00');
INSERT INTO `payment` VALUES ('53', '7de2f0ae-0240-4695-afa1-749df0ca1fc3', '7', '1', '87', '10.00');
INSERT INTO `payment` VALUES ('54', 'd2a5b0af-dcb9-4cfe-bf99-de4fef9da03c', '9', '1', '88', '10.00');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `start_price` decimal(20,2) DEFAULT NULL,
  `min_add_price` decimal(20,0) DEFAULT NULL,
  `current_price` decimal(20,0) DEFAULT NULL,
  `seller_id` int(11) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `state` int(255) DEFAULT NULL,
  `details` text,
  `start_date` datetime DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  FULLTEXT KEY `ft_index` (`name`,`details`) /*!50100 WITH PARSER `ngram` */ 
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('58', '不才专辑', '500.00', '5', '500', '6', 'http://127.0.0.1/763d23f2-579e-4f83-a23e-386ab7642d39-a6cd74949348dbc7edcaa3f4a63ceaa.jpg', '0', '<p>6666</p>', '2020-01-07 10:33:53', '5', '上海市辖区徐汇区', '书籍');
INSERT INTO `product` VALUES ('59', '生发水', '100.00', '5', '100', '6', 'http://127.0.0.1/ad9a887c-5a80-4c44-bf5f-a19d563af27f-687047d6cb8f709d10dbf49bd0e4efa.jpg', '0', '<p>6666</p>', '2020-01-07 10:33:53', '5', '上海市辖区徐汇区', '书籍');
INSERT INTO `product` VALUES ('60', '朱圣的微笑', '100.00', '5', '100', '6', 'http://127.0.0.1/bd1ce704-d2a1-45f3-ab16-74f56b556a79-04c116103fffa456e7b03d6c9b9700d.jpg', '0', '<p>6666</p>', '2020-01-07 10:33:53', '5', '上海市辖区徐汇区', '书籍');
INSERT INTO `product` VALUES ('70', '曾建文', '250.00', '50', '250', '8', 'http://127.0.0.1/c6ae39e9-6f93-49a1-9781-8c080a4a961d-007cIQExly8g6up1hjn1xj30u00u040r.jpg', '0', '<p><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/pcmoren_tian_org.png\" alt=\"[舔屏]\" data-w-e=\"1\"><br></p>', '2020-01-07 10:33:53', '5', '上海市辖区徐汇区', '书籍');
INSERT INTO `product` VALUES ('71', '清朝摆件', '3000.00', '10', '3000', '9', 'http://127.0.0.1/96a19aa0-5034-4e8b-b1cd-cd63fb3810ea-2.jpg', '0', '<p><br></p>', '2020-01-07 10:33:53', '5', '上海市辖区徐汇区', '书籍');
INSERT INTO `product` VALUES ('73', '宋杰远', '100.00', '5', '100', '4', 'http://127.0.0.1/16c3278a-1cb6-4f24-ab03-a3391807e7cc-007cIQExly8g6up1hjn1xj30u00u040r.jpg', '0', '<p>6666</p>', '2020-01-07 10:33:53', '5', '上海市辖区徐汇区', '书籍');
INSERT INTO `product` VALUES ('74', '齐志强', '100.00', '5', '141', '4', 'http://127.0.0.1/812849ac-7d61-47ba-8ce8-933c86414696-007cIQExly8g6up1hjn1xj30u00u040r.jpg', '1', '<p><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/50/pcmoren_huaixiao_org.png\" alt=\"[坏笑]\" data-w-e=\"1\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/50/pcmoren_huaixiao_org.png\" alt=\"[坏笑]\" data-w-e=\"1\" style=\"font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; font-size: 1.063rem;\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/50/pcmoren_huaixiao_org.png\" alt=\"[坏笑]\" data-w-e=\"1\" style=\"font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; font-size: 1.063rem;\"><img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/50/pcmoren_huaixiao_org.png\" alt=\"[坏笑]\" data-w-e=\"1\" style=\"font-size: 1.063rem; font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;;\">酷酷酷！志强Xeon！</p>', '2020-01-03 15:04:53', '5', '上海市辖区徐汇区', '书籍');
INSERT INTO `product` VALUES ('75', '严幸', '100.00', '5', '100', '4', 'http://127.0.0.1/30c3ddae-090e-4514-857e-0d5dc1e64913-de7e72380cd791235b96c92ca1345982b3b78076.jpg', '0', '<p>大帅哥</p>', '2020-01-07 10:33:53', '5', '上海市辖区徐汇区', '书籍');
INSERT INTO `product` VALUES ('76', 'Billy Yan', '100.00', '5', '100', '4', 'http://127.0.0.1/70f84c75-bcc4-4490-8ccb-3d028f001a49-微信图片_20191231171019.jpg', '0', '<p>Handsome Boy<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/pcmoren_tian_org.png\" alt=\"[舔屏]\" data-w-e=\"1\" style=\"font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; font-size: 1.063rem;\"></p>', '2020-01-07 10:33:53', '5', '上海市辖区黄浦区', '书籍');
INSERT INTO `product` VALUES ('77', '王雅', '100.00', '5', '100', '4', 'http://127.0.0.1/096d01a0-146a-476a-9820-736070b4e83f-微信截图_20191231171505.png', '0', '<p>大姐姐<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/pcmoren_tian_org.png\" alt=\"[舔屏]\" data-w-e=\"1\" style=\"font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; font-size: 1.063rem;\"></p>', '2020-01-06 15:04:53', '5', '上海市辖区浦东新区', '书籍');
INSERT INTO `product` VALUES ('78', '张无忌', '100.00', '5', '100', '4', 'http://127.0.0.1/5b5abe3e-00d5-44e1-ad92-2d477b91a42c-timg.jpg', '0', '<p>无忌哥哥</p>', '2020-01-07 10:33:53', '5', '西藏拉萨市当雄县', '书籍');
INSERT INTO `product` VALUES ('79', '赵敏', '100.00', '5', '100', '4', 'http://127.0.0.1/cbb53c68-34e4-42be-8998-a2c2152e75e3-20130913221702_AruwA.jpeg', '0', '<p>敏敏</p>', '2020-01-07 10:33:53', '5', '新疆乌鲁木齐市市辖区', '书籍');
INSERT INTO `product` VALUES ('80', '斐白', '100.00', '5', '100', '4', 'http://127.0.0.1/0fb95704-9004-4f03-9836-89806f1e109a-login_banner2.jpg', '0', '<p>斐白呀</p>', '2020-01-07 10:33:53', '5', '上海市辖区黄浦区', '书籍');
INSERT INTO `product` VALUES ('81', '斐白2', '100.00', '5', '100', '4', 'http://127.0.0.1/67bf186d-60f7-4e7f-9937-1b4e594af9f7-login_banner2.jpg', '0', '<p>66666</p>', '2020-01-06 12:22:53', '5', '重庆市辖区万州区', '书籍');
INSERT INTO `product` VALUES ('82', '冷漠贱', '100.00', '5', '100', '7', 'http://127.0.0.1/5728af87-4bbd-4b2c-9673-6233c1a78018-微信图片_20200106142751.jpg', '0', '<p>冷漠贱<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/3c/pcmoren_wu_org.png\" alt=\"[污]\" data-w-e=\"1\" style=\"font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; font-size: 1.063rem;\"></p>', '2020-01-06 14:33:13', '5', '新疆乌鲁木齐市市辖区', '电子');
INSERT INTO `product` VALUES ('84', '睿弟弟', '100.00', '5', '100', '4', 'http://127.0.0.1/129cd39f-7466-4d3f-96df-4a073f8f57f6-login_banner.jpg', '6', '<p>睿弟弟</p>', '2020-01-06 14:42:12', '3', '河北石家庄市市辖区', '书籍');
INSERT INTO `product` VALUES ('85', '烟熏睿', '100.00', '5', '100', '4', 'http://127.0.0.1/f8c59f0a-2771-4b1b-a88a-3f66ae95262a-微信图片_20200106144831.jpg', '0', '<p>烟熏睿</p>', '2020-01-06 14:53:44', '5', '上海市辖区黄浦区', '书籍');
INSERT INTO `product` VALUES ('86', '陈靖文', '100.00', '5', '100', '4', 'http://127.0.0.1/3d4a60d7-2b99-47f7-acbb-128ddad9671f-微信图片_20200106144836.jpg', '0', '<p>陈靖文<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/40/pcmoren_tian_org.png\" alt=\"[舔屏]\" data-w-e=\"1\" style=\"font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Roboto, &quot;Helvetica Neue&quot;, Arial, &quot;Noto Sans&quot;, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;, &quot;Noto Color Emoji&quot;; font-size: 1.063rem;\"></p>', '2020-01-06 14:54:20', '5', '广西防城港市市辖区', '电子');
INSERT INTO `product` VALUES ('87', '西域美女', '100.00', '5', '10005', '4', 'http://127.0.0.1/7cb5e382-cdcd-4457-9404-f9256d9ea5ff-微信图片_20200106145643.jpg', '0', '<p>西域美女</p>', '2020-01-06 15:03:09', '5', '青海西宁市市辖区', '书籍');
INSERT INTO `product` VALUES ('88', '大漠一枝花', '100.00', '5', '100', '4', 'http://127.0.0.1/63634bdc-c3bc-4f3d-9a49-91b777723f04-微信截图_20200106145638.png', '0', '<p>大漠一枝花</p>', '2020-01-06 15:00:25', '5', '甘肃庆阳市市辖区', '美妆');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `a` (`username`,`password`,`telephone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('4', '123456', '123456', '18621518173', '0');
INSERT INTO `user` VALUES ('5', 'zhangsan', '123456', '18702171092', '0');
INSERT INTO `user` VALUES ('6', 'lige2333', '123456', '18702171092', '0');
INSERT INTO `user` VALUES ('7', '1234567', '123456', '18516728003', '0');
INSERT INTO `user` VALUES ('8', '12345678', '123456', '18702171092', '0');
INSERT INTO `user` VALUES ('9', 'monica', 'monica', '18300679088', '0');
