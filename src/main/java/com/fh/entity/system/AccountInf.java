package com.fh.entity.system;

import java.sql.Timestamp;

/**
 * 账户余额信息实体类
 */
public class AccountInf {
    private String ACC_ID;
    private String USER_ID;
    private String ACC_TYPE;
    private Character ACC_STATE;
    private String ACC_BAL;
    private String ACC_BAL_CODE;
    private String FREEZE_AMT;
    private String LAST_TXN_DATE;
    private String LAST_TXN_TIME;
    private Character DATA_STAT;
    private String CREATE_USER;
    private Timestamp CREATE_DATE;
    private String UPDATE_USER;
    private Timestamp UPDATE_DATE;
    private Integer LOCK_VERSION;
    private String RES_COLUMN1;
    private String RES_COLUMN2;
    private String RES_COLUMN3;

    public AccountInf(){}

    public String getACC_ID() {
        return ACC_ID;
    }

    public void setACC_ID(String ACC_ID) {
        this.ACC_ID = ACC_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getACC_TYPE() {
        return ACC_TYPE;
    }

    public void setACC_TYPE(String ACC_TYPE) {
        this.ACC_TYPE = ACC_TYPE;
    }

    public Character getACC_STATE() {
        return ACC_STATE;
    }

    public void setACC_STATE(Character ACC_STATE) {
        this.ACC_STATE = ACC_STATE;
    }

    public String getACC_BAL() {
        return ACC_BAL;
    }

    public void setACC_BAL(String ACC_BAL) {
        this.ACC_BAL = ACC_BAL;
    }

    public String getACC_BAL_CODE() {
        return ACC_BAL_CODE;
    }

    public void setACC_BAL_CODE(String ACC_BAL_CODE) {
        this.ACC_BAL_CODE = ACC_BAL_CODE;
    }

    public String getFREEZE_AMT() {
        return FREEZE_AMT;
    }

    public void setFREEZE_AMT(String FREEZE_AMT) {
        this.FREEZE_AMT = FREEZE_AMT;
    }

    public String getLAST_TXN_DATE() {
        return LAST_TXN_DATE;
    }

    public void setLAST_TXN_DATE(String LAST_TXN_DATE) {
        this.LAST_TXN_DATE = LAST_TXN_DATE;
    }

    public String getLAST_TXN_TIME() {
        return LAST_TXN_TIME;
    }

    public void setLAST_TXN_TIME(String LAST_TXN_TIME) {
        this.LAST_TXN_TIME = LAST_TXN_TIME;
    }

    public Character getDATA_STAT() {
        return DATA_STAT;
    }

    public void setDATA_STAT(Character DATA_STAT) {
        this.DATA_STAT = DATA_STAT;
    }

    public String getCREATE_USER() {
        return CREATE_USER;
    }

    public void setCREATE_USER(String CREATE_USER) {
        this.CREATE_USER = CREATE_USER;
    }



    public String getUPDATE_USER() {
        return UPDATE_USER;
    }

    public void setUPDATE_USER(String UPDATE_USER) {
        this.UPDATE_USER = UPDATE_USER;
    }



    public Integer getLOCK_VERSION() {
        return LOCK_VERSION;
    }

    public void setLOCK_VERSION(Integer LOCK_VERSION) {
        this.LOCK_VERSION = LOCK_VERSION;
    }

    public String getRES_COLUMN1() {
        return RES_COLUMN1;
    }

    public void setRES_COLUMN1(String RES_COLUMN1) {
        this.RES_COLUMN1 = RES_COLUMN1;
    }

    public String getRES_COLUMN2() {
        return RES_COLUMN2;
    }

    public void setRES_COLUMN2(String RES_COLUMN2) {
        this.RES_COLUMN2 = RES_COLUMN2;
    }

    public String getRES_COLUMN3() {
        return RES_COLUMN3;
    }

    public void setRES_COLUMN3(String RES_COLUMN3) {
        this.RES_COLUMN3 = RES_COLUMN3;
    }


}
