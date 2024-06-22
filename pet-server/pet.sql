/*
Navicat MySQL Data Transfer

Source Server         : 本地MySQL
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : pet

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2023-03-12 18:22:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accusation
-- ----------------------------
DROP TABLE IF EXISTS `accusation`;
CREATE TABLE `accusation` (
  `acc_id` int(11) NOT NULL AUTO_INCREMENT,
  `accusater` int(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `pet_id` int(11) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `acc_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`acc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of accusation
-- ----------------------------
INSERT INTO `accusation` VALUES ('1', '4', '5', '5', '测试数据', '2023-02-27 15:33:16', '已处理');
INSERT INTO `accusation` VALUES ('2', '1', '5', '5', '我就看看，不举报', '2023-02-27 15:36:00', '已处理');
INSERT INTO `accusation` VALUES ('3', '1', '4', '2', '举报二哈', '2023-02-27 15:36:43', '已处理');

-- ----------------------------
-- Table structure for adopt
-- ----------------------------
DROP TABLE IF EXISTS `adopt`;
CREATE TABLE `adopt` (
  `adopt_id` int(11) NOT NULL AUTO_INCREMENT,
  `adopt_user` int(11) DEFAULT NULL,
  `publish_user` int(11) DEFAULT NULL,
  `pet_id` int(11) DEFAULT NULL,
  `adopt_date` datetime DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`adopt_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of adopt
-- ----------------------------
INSERT INTO `adopt` VALUES ('4', '4', '2', '3', '2023-03-06 09:21:13', '已通过');
INSERT INTO `adopt` VALUES ('5', '6', '3', '1', '2023-03-09 14:13:29', '已通过');

-- ----------------------------
-- Table structure for black_list
-- ----------------------------
DROP TABLE IF EXISTS `black_list`;
CREATE TABLE `black_list` (
  `black_list_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `put_in_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`black_list_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of black_list
-- ----------------------------

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `blog_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `publish_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `user_id` int(11) DEFAULT NULL,
  `pet_id` int(11) DEFAULT NULL,
  `content` text,
  `pic` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`blog_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('1', '我与小肉丸的日常', '2023-03-09 14:07:45', '4', '3', '领养了这只英短后我给她取名叫“小肉丸”，因为她肉乎乎的，非常可爱，现在小肉丸身体健康，生活的很好', 'http://118.25.89.125:18080/images/2022040914060991yph.jpg', null, null);
INSERT INTO `blog` VALUES ('3', '测试数据我与小肉丸的日常', '2023-03-09 14:07:45', '4', '3', '领养了这只英短后我给她取名叫“小肉丸”，因为她肉乎乎的，非常可爱，现在小肉丸身体健康，生活的很好', 'http://118.25.89.125:18080/images/2022040914060991yph.jpg', null, null);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `send_time` datetime DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `is_read` int(11) DEFAULT NULL,
  `sender` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES ('2', '4', '寻宠线索', '2023-02-27 14:06:52', '环市东路最近收入一只小哈士奇', '1', 'USER:6');
INSERT INTO `message` VALUES ('3', '4', '系统消息：举报反馈', '2023-03-02 09:30:29', '您好，您于2023-02-27 15:33举报的宠物领养信息\"流浪宠物中心：布偶猫求收养\"，管理员已经处理，处理结果：信息与实际不符', '0', 'SYS');
INSERT INTO `message` VALUES ('4', '5', '系统消息：举报反馈', '2023-03-02 09:30:29', '您好，您于2023-02-27 14:39发布的宠物领养信息\"流浪宠物中心：布偶猫求收养\"，被举报，管理员处理结果为：信息与实际不符', '0', 'SYS');
INSERT INTO `message` VALUES ('5', '1', '系统消息：举报反馈', '2023-03-02 22:53:27', '您好，您于2023-02-27 15:36举报的宠物领养信息\"流浪宠物中心：布偶猫求收养\"，管理员已经处理，处理结果：你被拉黑了', '1', 'SYS');
INSERT INTO `message` VALUES ('6', '5', '系统消息：举报反馈', '2023-03-02 22:53:27', '您好，您于2023-02-27 14:39发布的宠物领养信息\"流浪宠物中心：布偶猫求收养\"，被举报，管理员处理结果为：你被拉黑了', '0', 'SYS');
INSERT INTO `message` VALUES ('7', '4', '宠物领养审核结果', '2023-03-06 09:32:16', '您的领养申请【2岁美国短毛猫求收养】已通过送养人的审核，请与送养人联系沟通。', '0', 'SYS');
INSERT INTO `message` VALUES ('8', '1', '系统消息：举报反馈', '2023-03-09 13:56:12', '您好，您于2023-02-27 15:36举报的宠物领养信息\"小哈士奇求收养\"，管理员已经处理，处理结果：举报不属实', '0', 'SYS');
INSERT INTO `message` VALUES ('9', '4', '系统消息：举报反馈', '2023-03-09 13:56:13', '您好，您于2023-02-28 14:26发布的宠物领养信息\"小哈士奇求收养\"，被举报，管理员处理结果为：举报不属实', '0', 'SYS');
INSERT INTO `message` VALUES ('10', '6', '宠物领养审核结果', '2023-03-09 14:14:07', '您的领养申请【测试数据】已通过送养人的审核，请与送养人联系沟通。', '0', 'SYS');

-- ----------------------------
-- Table structure for pet
-- ----------------------------
DROP TABLE IF EXISTS `pet`;
CREATE TABLE `pet` (
  `pet_id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL,
  `sub_category` varchar(255) DEFAULT NULL,
  `age` varchar(8) DEFAULT NULL,
  `gender` varchar(8) DEFAULT NULL,
  `jieyu` varchar(8) DEFAULT NULL,
  `mianyi` varchar(8) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `detail` text,
  `pic` varchar(255) DEFAULT NULL,
  `pos_lat` varchar(255) DEFAULT NULL,
  `pos_lng` varchar(255) DEFAULT NULL,
  `pos_txt` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `remark` text,
  `publish_time` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`pet_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pet
-- ----------------------------
INSERT INTO `pet` VALUES ('1', '猫', '橘猫', '2', '男', '不详', '已免疫', '测试数据', '这是一只可爱的橘猫', 'http://118.25.89.125:18080/images/20220325085826o2tav.jpg', '23.17792', '113.26324', '广东省广州市白云区广园中路', '已领养', null, '2023-02-29 14:25:56', '3');
INSERT INTO `pet` VALUES ('2', '狗', '哈士奇', '7个月', '男', '未绝育', '已免疫', '小哈士奇求收养', '小哈士奇，很容易养', 'http://118.25.89.125:18080/images/2022032509382228qsv.jpg', '23.15792', '113.28524', '广东省广州市白云区广园中路', '待领养', null, '2023-02-28 14:26:03', '4');
INSERT INTO `pet` VALUES ('3', '猫', '英国短毛猫', '2', '女', '已绝育', '不详', '2岁美国短毛猫求收养', '这只英短很听话', 'http://118.25.89.125:18080/images/202203270916510fbdj.jpg', '23.16292', '113.28424', '广东省广州市白云区广园中路', '已领养', null, '2023-02-25 14:26:15', '2');
INSERT INTO `pet` VALUES ('4', '狗', '金毛', '1', '男', '已绝育', '已免疫', '流浪金毛求收养', '这是测试数据详细描述', 'http://118.25.89.125:18080/images/20220327092745fvqqk.jpg', '23.16192', '113.27324', '广东省广州市白云区广园中路', '待领养', null, '2023-02-26 09:27:52', '4');
INSERT INTO `pet` VALUES ('5', '猫', '布偶猫', '1', '女', '已绝育', '已免疫', '流浪宠物中心：布偶猫求收养', '近日有志愿者带了一只布偶猫到流浪宠物中心，求有心人收养，小猫很健康', 'http://118.25.89.125:18080/images/20220327093229diq10.jpg', '23.17092', '113.28324', '广东省广州市白云区广园中路', '待领养', null, '2023-02-27 14:39:09', '5');

-- ----------------------------
-- Table structure for pet_notice
-- ----------------------------
DROP TABLE IF EXISTS `pet_notice`;
CREATE TABLE `pet_notice` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `publish_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `category` varchar(255) DEFAULT NULL,
  `features` varchar(255) DEFAULT NULL,
  `detail` text,
  `pic` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`notice_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of pet_notice
-- ----------------------------
INSERT INTO `pet_notice` VALUES ('1', '寻找小哈士奇', '2023-02-27 10:15:39', '哈士奇', '尾巴有3个斑点 ', '3月26日在环市东路友谊酒店附近路段走丢', 'http://118.25.89.125:18080/images/20220327095524idwgm.jpg', '4', '环市东路友谊酒店附近', '15800987654', '寻找中');
INSERT INTO `pet_notice` VALUES ('2', '寻找中华田园犬', '2023-03-09 14:11:39', '中华田园犬', '后背是黄色，脖子与肚子是白色', '脖子上挂有一个铃铛', 'http://118.25.89.125:18080/images/202204091409415aqgz.jpg', '6', '白云公园附近', '13566667777', '寻找中');

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `next_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `last_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(255) DEFAULT NULL,
  `pwd` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `id_card` varchar(255) DEFAULT NULL,
  `img` varchar(255) DEFAULT NULL,
  `is_validate` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '13760613080', '123456', '广州无艳祖', null, '男', '1997-12-03', '广东省-广州市-天河区', null, null, '-1');
INSERT INTO `user` VALUES ('2', '17722223333', '123456', '无敌铲屎官', null, '男', '1997-05-21', '广东省-广州市-海珠区', null, null, '-1');
INSERT INTO `user` VALUES ('3', '15899990000', '1234', '我们一起学狗叫', null, '男', '1992-03-03', '广东省-深圳市-南山区', null, null, '-1');
INSERT INTO `user` VALUES ('4', '18900005555', '1234', '小圆圆', '张晓霞', '女', '1996-03-03', '福建省-厦门市-集美区', null, 'http://118.25.89.125:18080/images/202203302023226d8h1.jpg', '1');
INSERT INTO `user` VALUES ('5', '18977776666', '1234', '有猫的小刘', null, '女', '1995-07-05', '四川省-成都市-武侯区', null, null, '-1');
INSERT INTO `user` VALUES ('6', '13566667777', '1234', '胖小帆', '奥巴马', '男', '1993-05-01', '云南省-昆明市-官渡区', null, 'http://118.25.89.125:18080/images/20220409134512.jpg', '1');
SET FOREIGN_KEY_CHECKS=1;
