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