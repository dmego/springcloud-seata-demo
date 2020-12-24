SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` varchar(255) NOT NULL COMMENT '账户',
  `balance` double DEFAULT NULL COMMENT '账户余额',
  `freezed` double DEFAULT NULL COMMENT '账户冻结金额',
  `incoming` double DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
BEGIN;
INSERT INTO `account` VALUES ('1', 10, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for account_trans
-- ----------------------------
DROP TABLE IF EXISTS `account_trans`;
CREATE TABLE `account_trans` (
  `tx_id` varchar(255) NOT NULL,
  `account_id` varchar(255) NOT NULL,
  `amount` double DEFAULT NULL,
  PRIMARY KEY (`tx_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `temp`;
CREATE TABLE `temp` (
  `id` varchar(255) NOT NULL,
  `data` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of temp
-- ----------------------------
BEGIN;
INSERT INTO `temp` VALUES ('1', '1');
INSERT INTO `temp` VALUES ('2', '1');
INSERT INTO `temp` VALUES ('3', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
