DROP TABLE IF EXISTS `TB_TRANS_LOG`;
CREATE TABLE `TB_TRANS_LOG` (
		`TRANS_ID` BIGINT(20)  unsigned NOT NULL AUTO_INCREMENT COMMENT '交易流水号',
		`ORG_TRANS_ID` BIGINT(20) DEFAULT NULL COMMENT '原交易流水号',
		`DMS_RELATED_KEY` varchar(32) DEFAULT NULL COMMENT '外部系统流水号',
		`ORG_DMS_RELATED_KEY` varchar(32) DEFAULT NULL COMMENT '原外部系统交易流水号',
		`ORDER_ST` varchar(4) DEFAULT NULL COMMENT '交易类型',
		`TRANS_ST` varchar(1) DEFAULT NULL COMMENT '交易状态',
		`TRANS_CODE` varchar(32) DEFAULT NULL COMMENT '终端设备号',
		`RESP_CODE` varchar(1) DEFAULT NULL COMMENT '响应码',
		`PRI_ACC_ID` varchar(255) DEFAULT NULL COMMENT '当前操作主账号',
		`DMS_USER_ID` varchar(255) DEFAULT NULL COMMENT '外部系统用户主键',
		`DMS_USER_UNION_ID` varchar(255) DEFAULT NULL COMMENT '外部系统用户联合主键',
		`TRANS_AMT` varchar(64) DEFAULT NULL COMMENT '实际交易金额',
		`ORG_TRANS_AMT` varchar(64) DEFAULT NULL COMMENT '原交易金额',
		`TRANS_CURR_CD` varchar(4) DEFAULT NULL COMMENT '交易货币代码',
		`TRANS_CHNL` varchar(16) DEFAULT NULL COMMENT '交易渠道',
		`TRANS_FEE` varchar(16) DEFAULT NULL COMMENT '手续费',
		`TRANS_FEE_TYPE` varchar(8) DEFAULT NULL COMMENT '手续费类型',
		`TFR_IN_ACC_ID` varchar(32) DEFAULT NULL COMMENT '转入账户',
		`TFR_OUT_ACC_ID` varchar(32) DEFAULT NULL COMMENT '转出账户',
		`ADD_INFO` varchar(255) DEFAULT NULL COMMENT '附加信息',
		`REMARKS` varchar(255) DEFAULT NULL COMMENT '备注',
		`CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
		`CREATE_DATE` varchar(32) DEFAULT NULL COMMENT '创建时间',
		`UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '最后修改人',
		`UPDATE_DATE` varchar(32) DEFAULT NULL COMMENT '最后修改时间',
		`LOCK_VERSION` int(11) NOT NULL COMMENT '乐观锁版本号',
		`MCHNT_ID` varchar(32) DEFAULT NULL COMMENT '所属商户',
  		PRIMARY KEY (`TRANS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=888888000000 DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `TB_ACCOUNT_LOG`;
CREATE TABLE `TB_ACCOUNT_LOG` (
		`LOG_ID` BIGINT(20)  unsigned NOT NULL AUTO_INCREMENT COMMENT '日志主键',
		`ACC_ID` BIGINT(20) DEFAULT NULL COMMENT '账户主键',
		`LAST_TXN_DATE` varchar(8) DEFAULT NULL COMMENT '账户交易日期',
		`LAST_TXN_TIME` varchar(6) DEFAULT NULL COMMENT '账户交易时间',
		`TRANS_ID` BIGINT(20) DEFAULT NULL COMMENT '交易流水号',
		`TRANS_AMT` varchar(16) DEFAULT NULL COMMENT '交易金额',
		`ACC_AMT` varchar(16) DEFAULT NULL COMMENT '账户处理金额',
		`ACC_TOTAL_AMT` varchar(16) DEFAULT NULL COMMENT '账户处理后总余额',
		`RES_COLUMN1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
		`RES_COLUMN2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
		`RES_COLUMN3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
		`CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
		`CREATE_DATE` varchar(32) DEFAULT NULL COMMENT '创建时间',
		`UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '最后修改人',
		`UPDATE_DATE` varchar(32) DEFAULT NULL COMMENT '最后修改时间',
		`LOCK_VERSION` int(11) NOT NULL COMMENT '乐观锁版本号',
  		PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=888888000000 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `TB_ACCOUNT_INF`;
CREATE TABLE `TB_ACCOUNT_INF` (
		`ACC_ID` BIGINT(20)  unsigned NOT NULL AUTO_INCREMENT COMMENT '账户主键',
		`MCHNT_ID` INT(4) DEFAULT NULL COMMENT '商户主键',
		`USER_ID` varchar(32) DEFAULT NULL COMMENT '用户主键',
		`ACC_TYPE` varchar(4) DEFAULT NULL COMMENT '账户类型',
		`ACC_STATE` varchar(2) DEFAULT NULL COMMENT '账户状态',
		`ACC_BAL` varchar(16) DEFAULT NULL COMMENT '可用余额明文',
		`ACC_BAL_CODE` varchar(64) DEFAULT NULL COMMENT '可用余额密文',
		`FREEZE_AMT` varchar(16) DEFAULT NULL COMMENT '冻结金额',
		`LAST_TXN_DATE` varchar(8) DEFAULT NULL COMMENT '账户交易日期',
		`LAST_TXN_TIME` varchar(6) DEFAULT NULL COMMENT '账户交易时间',
		`RES_COLUMN1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
		`RES_COLUMN2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
		`RES_COLUMN3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
		`CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
		`CREATE_DATE` varchar(32) DEFAULT NULL COMMENT '创建时间',
		`UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '最后修改人',
		`UPDATE_DATE` varchar(32) DEFAULT NULL COMMENT '最后修改时间',
		`LOCK_VERSION` int(11) NOT NULL COMMENT '乐观锁版本号',
  		PRIMARY KEY (`ACC_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;


/*2018 01 10*/
ALTER TABLE `sys_app_user`
ADD COLUMN `CREATE_TIME`  datetime NULL COMMENT '用户注册时间' AFTER `SIGN_TAG`;

ALTER TABLE `tb_app_doll_toy`
MODIFY COLUMN `TOY_CONVERSION`  int(6) NULL DEFAULT 0 COMMENT '娃娃兑换金币' AFTER `TOY_NUM`;

ALTER TABLE `tb_send_goods`
DROP COLUMN `REMARK`,
ADD COLUMN `REMARK`  varchar(255) NULL COMMENT '备注' AFTER `POST_REMARK`,
ADD COLUMN `FMS_TIME`  datetime NULL COMMENT '发货时间' AFTER `REMARK`,
ADD COLUMN `FMS_ORDER_NO`  varchar(50) NULL COMMENT '物流单号' AFTER `FMS_TIME`,
ADD COLUMN `FMS_NAME`  varchar(255) NULL COMMENT '物流名称' AFTER `FMS_ORDER_NO`,
AUTO_INCREMENT=81;

/*2018 01 20*/
ALTER TABLE `sys_app_doll`
ADD COLUMN `DOLL_TAG`  varchar(255) NULL COMMENT '娃娃标签，爆款，畅销' AFTER `RELEASE_STATUS`;

ALTER TABLE `tb_doll_play_detail`
ADD COLUMN `FREE_DATE`  varchar(50) NULL COMMENT 'FREE状态更新时间' AFTER `CREATE_DATE`;

/*2018 01 22*/
ALTER TABLE `tb_doll_play_detail`
ADD COLUMN `SEND_ORDER_ID`  bigint(20) NULL COMMENT '发货订单主键ID' AFTER `FREE_DATE`;


/*2018 01 23*/
ALTER TABLE `tb_app_runimage` ADD COLUMN `HREF_ST`  varchar(255) NULL COMMENT '跳转路径';
alter table `tb_app_runimage` MODIFY COLUMN CONTENT text;

ALTER TABLE `tb_app_doll_toy`
ADD COLUMN `TOY_TYPE`  varchar(255) NULL COMMENT '类别' AFTER `DOOL_GOLD`;

/*2018 01 25*/
ALTER TABLE `tb_doll_play_detail`
ADD COLUMN `TOY_ID`  int(11) NULL COMMENT '娃娃编号' AFTER `CREATE_DATE`;




/*2018 02 02*/
/**更新未充值的用户账户余额  慎用**/
UPDATE sys_app_user u set u.BALANCE='0' WHERE u.USER_ID NOT IN (
	SELECT o.USER_ID  from sys_app_order o where 1=1 and o.STATUS='1' GROUP BY o.USER_ID
)


/*2018 02 05*/
ALTER TABLE `sys_app_doll` ADD COLUMN `DOLL_TYPE`  varchar(255) NULL;

ALTER TABLE `tb_device_camera` ADD COLUMN `H5_URL`  varchar(255) NULL COMMENT 'H5播放地址' AFTER `RTMP_URL`;

/* 2018 01 26*/
ALTER TABLE `sys_app_user`
ADD COLUMN `BET_NUM`  int(11) NULL DEFAULT 0 COMMENT '竞猜次数，10次为上限' AFTER `CNEE_NAME`;

/*2018 02 01*/
CREATE TABLE `tb_sign_gold` (
`SIGNID`  int(11) NOT NULL ,
`DAY_NUM`  varchar(50) NULL ,
`GOLD_NUM`  varchar(50) NULL ,
`UPDATE_TIME`  timestamp(4) NULL ON UPDATE CURRENT_TIMESTAMP(4) ,
PRIMARY KEY (`SIGNID`)
)
;

ALTER TABLE `tb_sign_gold`
MODIFY COLUMN `SIGNID`  int(11) NOT NULL AUTO_INCREMENT FIRST ;

ALTER TABLE `tb_doll_guess_detail`
ADD COLUMN `GUSESS_Y_PEOPLE`  varchar(50) NULL COMMENT '竞猜总人数' AFTER `GUESS_GOLD`;

/*2018 02 05*/
ALTER TABLE `tb_doll_play_detail`
ADD COLUMN `REWARD_NUM`  varchar(10) NULL COMMENT '开奖号码' AFTER `SENDGOODS`;

ALTER TABLE `SYS_APP_DOLL` ADD COLUMN `DOLL_TYPE`  varchar(255) NULL COMMENT 'H5是否可见';
ALTER TABLE `TB_DEVICE_CAMERA` ADD COLUMN `H5_URL`  varchar(255) NULL COMMENT 'H5拉流地址';

/*2018 02 07 zhuqiuyou*/
DROP TABLE IF EXISTS `SYS_APP_USER_CODE`;
CREATE TABLE `SYS_APP_USER_CODE` (
`CODE_ID`  BIGINT(12)  NOT NULL AUTO_INCREMENT,
`USER_ID`  varchar(255) NULL  COMMENT '用户ID',
`CODE_VALUE`  varchar(255) NULL  COMMENT '邀请码',
`CODE_NUM`  varchar(50) NULL COMMENT '兑换次数',
`CODE_TYPE`  varchar(1) NULL COMMENT '1：邀请兑换，2:分享兑换',
`REMARK`  varchar(255) NULL COMMENT '备注',
`UPDATE_TIME`  timestamp(4) NULL ON UPDATE CURRENT_TIMESTAMP(4) ,
PRIMARY KEY (`CODE_ID`),
UNIQUE KEY `SYS_APP_USER_CODE_UNIQUE_CODE_VALUE` (`CODE_VALUE`),
INDEX `SYS_APP_USER_CODE_INDEX_USER_ID` (`USER_ID`) 
)ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `SYS_APP_USER_AWARD_LIST`;
CREATE TABLE `SYS_APP_USER_AWARD_LIST` (
`AWARD_ID`  BIGINT(12) NOT NULL AUTO_INCREMENT ,
`CODE_ID`  BIGINT(12)  NULL ,
`USER_ID`  varchar(255) NULL  COMMENT '用户ID',
`AWARD_TYPE`  varchar(2) NULL COMMENT '1：邀请人，2:接受邀请人',
`AWARD_NUM` INT NOT NULL COMMENT '奖励类型' DEFAULT 0,
`IMEI_ID` varchar(50) NULL COMMENT '设备序列',
`REMARK`  varchar(255) NULL COMMENT '备注',
`CREATE_TIME`  timestamp(4) NULL ON UPDATE CURRENT_TIMESTAMP(4) ,
PRIMARY KEY (`AWARD_ID`),
INDEX `SYS_APP_USER_AWARD_LIST_INDEX_USER_ID` (`USER_ID`) 
)ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `SYS_BASE_DICT`;
CREATE TABLE `SYS_BASE_DICT` (
`DICT_ID`  BIGINT(12)  NOT NULL AUTO_INCREMENT,
`DICT_NAME`  varchar(255) NULL  COMMENT '字典名称',
`DICT_KEY`  varchar(255) NULL  COMMENT '字典KEY',
`DICT_VALUE`  varchar(255) NULL  COMMENT '字典值',
`PID`  BIGINT(12) NULL  COMMENT '父类ID',
`REMARK`  varchar(255) NULL COMMENT '备注',
`UPDATE_TIME`  timestamp(4) NULL ON UPDATE CURRENT_TIMESTAMP(4) ,
PRIMARY KEY (`DICT_ID`)
)ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;

/*2018 02 08*/
ALTER TABLE `tb_doll_guess_pond`
ADD COLUMN `GUESSER_NAME`  varchar(255) NULL COMMENT '竞猜获胜者昵称' AFTER `GUESS_GOLD`;

/* 2018 02 26*/
ALTER TABLE `sys_app_order`
ADD COLUMN `CTYPE`  varchar(50) NULL COMMENT 'sdk类型' AFTER `REGGOLD`,
ADD COLUMN `CHANNEL`  varchar(50) NULL COMMENT '渠道类型' AFTER `CTYPE`;

/* 2018 02 27*/
ALTER TABLE `sys_app_paycard`
ADD COLUMN `RECHARE`  varchar(255) NULL COMMENT '充值' AFTER `IMAGEURL`,
ADD COLUMN `AWARD`  varchar(255) NULL COMMENT '奖励' AFTER `RECHARE`;



/* 2018 03 06*/
ALTER TABLE `SYS_APP_USER` ADD COLUMN `PRO_USER_ID`  varchar(255) NULL COMMENT '渠道推广的用户ID';

ALTER TABLE `SYS_APP_ORDER` ADD COLUMN `PAY_TYPE`  varchar(50) NULL COMMENT '支付类型，R：充值，P，购买渠道推广';
ALTER TABLE `SYS_APP_ORDER` ADD COLUMN `PRO_USER_ID`  varchar(255) NULL COMMENT '渠道推广的用户ID';
ALTER TABLE `SYS_APP_ORDER` ADD COLUMN `ADD_INFO`  varchar(255) NULL COMMENT '订单追加信息';
ALTER TABLE `SYS_APP_ORDER` ADD COLUMN `OUT_ORDER_ID`  varchar(255) NULL COMMENT '外部订单';

+ALTER TABLE `tb_toytype` ADD COLUMN `IMG_URL`  varchar(255) NULL COMMENT '微缩图地址';
+ALTER TABLE `tb_toytype` ADD COLUMN `TOY_FLAG`  varchar(255) NULL COMMENT '分类标记  1：原生APP，2：H5跳转';
+ALTER TABLE `tb_toytype` ADD COLUMN `HREF_URL`  varchar(255) NULL COMMENT '跳转地址';
+ALTER TABLE `tb_toytype` AUTO_INCREMENT=200000 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `TB_TRANS_LOG`;
CREATE TABLE `TB_TRANS_LOG` (
		`TRANS_ID` BIGINT(20)  unsigned NOT NULL AUTO_INCREMENT COMMENT '交易流水号',
		`ORG_TRANS_ID` BIGINT(20) DEFAULT NULL COMMENT '原交易流水号',
		`DMS_RELATED_KEY` varchar(32) DEFAULT NULL COMMENT '外部系统流水号',
		`ORG_DMS_RELATED_KEY` varchar(32) DEFAULT NULL COMMENT '原外部系统交易流水号',
		`TRANS_TYPE` varchar(4) DEFAULT NULL COMMENT '交易类型',
		`TRANS_ST` varchar(1) DEFAULT NULL COMMENT '交易状态',
		`TRANS_CODE` varchar(32) DEFAULT NULL COMMENT '终端设备号',
		`RESP_CODE` varchar(6) DEFAULT NULL COMMENT '响应码',
		`PRI_ACC_ID` varchar(255) DEFAULT NULL COMMENT '当前操作主账号',
		`DMS_USER_ID` varchar(255) DEFAULT NULL COMMENT '外部系统用户主键',
		`DMS_USER_UNION_ID` varchar(255) DEFAULT NULL COMMENT '外部系统用户联合主键',
		`TRANS_AMT` varchar(64) DEFAULT NULL COMMENT '实际交易金额',
		`ORG_TRANS_AMT` varchar(64) DEFAULT NULL COMMENT '原交易金额',
		`TRANS_CURR_CD` varchar(4) DEFAULT NULL COMMENT '交易货币代码',
		`TRANS_CHNL` varchar(16) DEFAULT NULL COMMENT '交易渠道',
		`TRANS_FEE` varchar(16) DEFAULT NULL COMMENT '手续费',
		`TRANS_FEE_TYPE` varchar(8) DEFAULT NULL COMMENT '手续费类型',
		`TFR_IN_ACC_ID` varchar(32) DEFAULT NULL COMMENT '转入账户',
		`TFR_OUT_ACC_ID` varchar(32) DEFAULT NULL COMMENT '转出账户',
		`ADD_INFO` varchar(255) DEFAULT NULL COMMENT '附加信息',
		`REMARKS` varchar(255) DEFAULT NULL COMMENT '备注',
		`RES_COLUMN1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
		`RES_COLUMN2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
		`RES_COLUMN3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
		`CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
		`CREATE_DATE` DATETIME DEFAULT NULL COMMENT '创建时间',
		`UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '最后修改人',
		`UPDATE_DATE` DATETIME DEFAULT NULL COMMENT '最后修改时间',
		`LOCK_VERSION` int(11) NOT NULL COMMENT '乐观锁版本号',
		`MCHNT_ID` varchar(32) DEFAULT NULL COMMENT '所属商户',
  		PRIMARY KEY (`TRANS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=888888000000 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `TB_TRANS_ORDER`;
CREATE TABLE `TB_TRANS_ORDER` (
		`ORDER_ID` BIGINT(20)  unsigned NOT NULL AUTO_INCREMENT COMMENT '交易号',
		`ORG_ORDER_ID` BIGINT(20) DEFAULT NULL COMMENT '原交易号',
		`DMS_RELATED_KEY` varchar(32) DEFAULT NULL COMMENT '外部系统流水号',
		`ORG_DMS_RELATED_KEY` varchar(32) DEFAULT NULL COMMENT '原外部系统交易流水号',
		`USER_ID` varchar(32) DEFAULT NULL COMMENT '用户ID',
		`TRANS_TYPE` varchar(4) DEFAULT NULL COMMENT '交易类型',
		`ORDER_ST` varchar(1) DEFAULT NULL COMMENT '订单状态 (0:已提交，1：处理中，2：已取消，3：处理失败，9：已完成)',
		`TRANS_CODE` varchar(32) DEFAULT NULL COMMENT '终端设备号',
		`PRI_ACC_ID` varchar(255) DEFAULT NULL COMMENT '当前操作主账号',
		`DMS_USER_ID` varchar(255) DEFAULT NULL COMMENT '外部系统用户主键',
		`DMS_USER_UNION_ID` varchar(255) DEFAULT NULL COMMENT '外部系统用户联合主键',
		`TRANS_AMT` varchar(64) DEFAULT NULL COMMENT '实际交易金额',
		`ORG_TRANS_AMT` varchar(64) DEFAULT NULL COMMENT '原交易金额',
		`TRANS_CURR_CD` varchar(4) DEFAULT NULL COMMENT '交易货币代码',
		`TRANS_CHNL` varchar(16) DEFAULT NULL COMMENT '交易渠道',
		`TRANS_FEE` varchar(16) DEFAULT NULL COMMENT '手续费',
		`TRANS_FEE_TYPE` varchar(8) DEFAULT NULL COMMENT '手续费类型',
		`TFR_IN_ACC_ID` varchar(32) DEFAULT NULL COMMENT '转入账户',
		`TFR_OUT_ACC_ID` varchar(32) DEFAULT NULL COMMENT '转出账户',
		`ADD_INFO` varchar(255) DEFAULT NULL COMMENT '附加信息',
		`REMARKS` varchar(255) DEFAULT NULL COMMENT '备注',
		`RES_COLUMN1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
		`RES_COLUMN2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
		`RES_COLUMN3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
		`CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
		`CREATE_DATE` DATETIME DEFAULT NULL COMMENT '创建时间',
		`UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '最后修改人',
		`UPDATE_DATE` DATETIME DEFAULT NULL COMMENT '最后修改时间',
		`LOCK_VERSION` int(11) NOT NULL COMMENT '乐观锁版本号',
		`MCHNT_ID` varchar(32) DEFAULT NULL COMMENT '所属商户',
  		PRIMARY KEY (`ORDER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=111111000000 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `TB_ACCOUNT_LOG`;
CREATE TABLE `TB_ACCOUNT_LOG` (
		`LOG_ID` BIGINT(20)  unsigned NOT NULL AUTO_INCREMENT COMMENT '日志主键',
		`ACC_ID` BIGINT(20) DEFAULT NULL COMMENT '账户主键',
		`LAST_TXN_DATE` varchar(8) DEFAULT NULL COMMENT '账户交易日期',
		`LAST_TXN_TIME` varchar(6) DEFAULT NULL COMMENT '账户交易时间',
		`TRANS_TYPE` varchar(16) DEFAULT NULL COMMENT '交易流水号',
		`TRANS_CHNL` varchar(16) DEFAULT NULL COMMENT '交易金额',
		`ORG_TRANS_AMT` varchar(16) DEFAULT NULL COMMENT '原交易金额',
		`TRANS_AMT` varchar(16) DEFAULT NULL COMMENT '交易金额',
		`ACC_AMT` varchar(16) DEFAULT NULL COMMENT '账户处理金额',
		`ACC_TOTAL_AMT` varchar(16) DEFAULT NULL COMMENT '账户处理后总余额',
		`LOG_TYPE` varchar(2) DEFAULT NULL COMMENT '日志类型：1：加款，2：减款',
		`RES_COLUMN1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
		`RES_COLUMN2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
		`RES_COLUMN3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
		`CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
		`CREATE_DATE` DATETIME DEFAULT NULL COMMENT '创建时间',
		`UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '最后修改人',
		`UPDATE_DATE` DATETIME  NULL COMMENT '最后修改时间',
		`LOCK_VERSION` int(11) NOT NULL COMMENT '乐观锁版本号',
  		PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=888888000000 DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `TB_ACCOUNT_INF`;
CREATE TABLE `TB_ACCOUNT_INF` (
		`ACC_ID` BIGINT(20)  unsigned NOT NULL AUTO_INCREMENT COMMENT '账户主键',
		`MCHNT_ID` INT(4) DEFAULT NULL COMMENT '商户主键',
		`USER_ID` varchar(32) DEFAULT NULL COMMENT '用户主键',
		`ACC_TYPE` varchar(4) DEFAULT NULL COMMENT '账户类型',
		`ACC_STATE` varchar(2) DEFAULT NULL COMMENT '账户状态',
		`ACC_BAL` varchar(16) DEFAULT NULL COMMENT '可用余额明文',
		`ACC_BAL_CODE` varchar(64) DEFAULT NULL COMMENT '可用余额密文',
		`FREEZE_AMT` varchar(16) DEFAULT NULL COMMENT '冻结金额',
		`LAST_TXN_DATE` varchar(8) DEFAULT NULL COMMENT '账户交易日期',
		`LAST_TXN_TIME` varchar(6) DEFAULT NULL COMMENT '账户交易时间',
		`RES_COLUMN1` varchar(128) DEFAULT NULL COMMENT '备用字段1',
		`RES_COLUMN2` varchar(128) DEFAULT NULL COMMENT '备用字段2',
		`RES_COLUMN3` varchar(128) DEFAULT NULL COMMENT '备用字段3',
		`CREATE_USER` varchar(32) DEFAULT NULL COMMENT '创建人',
		`CREATE_DATE` DATETIME DEFAULT NULL COMMENT '创建时间',
		`UPDATE_USER` varchar(32) DEFAULT NULL COMMENT '最后修改人',
		`UPDATE_DATE` DATETIME DEFAULT NULL COMMENT '最后修改时间',
		`LOCK_VERSION` int(11) NOT NULL COMMENT '乐观锁版本号',
  		PRIMARY KEY (`ACC_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;


CREATE TABLE `SYS_WWJ_PROMOTE_MANAGE` (
  `PRO_MANAGE_ID` bigint(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `PRO_MANAGE_NAME` varchar(50) DEFAULT NULL,
  `PAY_AMOUNT` varchar(12) DEFAULT NULL COMMENT '支付金额',
  `PAY_GOLD` varchar(12) DEFAULT NULL COMMENT '支付金币数',
  `CONVER_GOLD` varchar(12) DEFAULT NULL COMMENT '下级用户兑换金币数量',
  `IMG_URL` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `PRO_TYPE` varchar(1) DEFAULT NULL COMMENT '1:金币支付,2:金钱支付',
  `RETURN_RATIO` varchar(12) DEFAULT NULL COMMENT '返回比例',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`PRO_MANAGE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8;

CREATE TABLE `SYS_WWJ_PROMOTE_APPUSER` (
  `PRO_ID` bigint(12) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `PRO_MANAGE_ID` bigint(4) DEFAULT NULL COMMENT '加盟权益分成ID',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户主键',
  `RETURN_RATIO` varchar(12) DEFAULT NULL COMMENT '用户分成比例',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`PRO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=100001 DEFAULT CHARSET=utf8;



/*2018 03 12*/

CREATE TABLE `tb_bankcard_inf` (
`BANKCARD_ID`  varchar(50) NOT NULL ,
`USER_ID`  varchar(50) NULL COMMENT '用户ID' ,
`BANK_ADDRESS`  varchar(255) NULL COMMENT '开户地点' ,
`BANK_NAME`  varchar(255) NULL COMMENT '银行名称' ,
`BANK_BRANCH`  varchar(255) NULL COMMENT '开户支行' ,
`BANK_CARD_NO`  varchar(255) NULL COMMENT '银行卡号' ,
`ID_NUMBER`  varchar(255) NULL COMMENT '身份证号' ,
`USER_REA_NAME`  varchar(255) NULL COMMENT '真实姓名' ,
PRIMARY KEY (`BANKCARD_ID`)
);

ALTER TABLE `tb_bankcard_inf`
ADD COLUMN `IS_DEFAULT`  varchar(5) NULL AFTER `USER_REA_NAME`,
ADD COLUMN `UPDATE_TIME`  timestamp NULL ON UPDATE CURRENT_TIMESTAMP AFTER `IS_DEFAULT`;
;

ALTER TABLE `tb_bankcard_inf`
ADD COLUMN `BANK_PHONE`  varchar(50) NULL AFTER `UPDATE_TIME`;

/*2018 03 19*/
CREATE TABLE `TB_QUIZZES_CHASE` (
`ID`  varchar(255) NOT NULL ,
`USER_ID`  varchar(50) NULL ,
`ROOM_ID`  varchar(50) NULL ,
`AFTER_VOTING`  int(255) NULL COMMENT '追投期数 1 ,5 , 10 , 20' ,
`LOTTERY_NUM`  varchar(10) NULL COMMENT '竞猜数字(0-9)' ,
`MULTIPLE`  int(10) NULL ,
`UPDATE_TIME`  timestamp(4) NULL ON UPDATE CURRENT_TIMESTAMP(4) ,
PRIMARY KEY (`ID`)
)
;

/* 2018 05 11*/
CREATE TABLE `NewTable` (
`COINFACTORY_ID`  varchar(50) NOT NULL ,
`COIN_SN`  varchar(255) NULL ,
`COIN_NAME`  varchar(255) NULL ,
`COIN_STATE`  varchar(255) NULL ,
`ROOM_ID`  varchar(20) NULL ,
`COIN_GOLD`  varchar(255) NULL ,
`IMAGE_URL`  varchar(255) NULL ,
`CONVERSIONGOLD`  varchar(255) NULL ,
`CAMERA_NAME_01`  varchar(255) NULL ,
`CAMERA_NAME_02`  varchar(255) NULL ,
`RES_01`  varchar(255) NULL ,
`RES_02`  varchar(255) NULL ,
PRIMARY KEY (`COINFACTORY_ID`)
)
;

/* 2018 05 31*/
CREATE TABLE `NewTable` (
`COINPUSHER_ID`  bigint(20) NOT NULL AUTO_INCREMENT ,
`ROOMID`  varchar(32) NULL ,
`USER_ID`  varchar(32) NULL ,
`COSTGOLD`  varchar(32) NULL ,
`RETURNGOLD`  varchar(32) NULL DEFAULT '0' ,
`FINISH_FLAG`  varchar(4) NULL DEFAULT 'N' ,
`CREATE_DATE`  varchar(32) NULL ,
`UPDATE_DATE`  timestamp NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`COINPUSHER_ID`)
)
;