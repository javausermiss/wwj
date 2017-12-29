package com.fh.entity.system;

public class SendGoods {
    private Integer ID;
    private Integer PLAYBACK_ID;
    private String USER_ID;
    private String GOODS_NUM;
    private String CNEE_NAME;
    private String CNEE_ADDRESS;
    private String CNEE_PHONE;
    private String CREATE_TIME;
    private String REMARK;
    private String UPDATE_TIME;
    private String POST_REMARK;//发货备注
    private String MODE_DESPATCH;//发货方式



    public SendGoods(){}

    public SendGoods(Integer PLAYBACK_ID, String USER_ID, String GOODS_NUM, String CNEE_NAME, String CNEE_ADDRESS, String CNEE_PHONE, String CREATE_TIME, String REMARK) {
        this.PLAYBACK_ID = PLAYBACK_ID;
        this.USER_ID = USER_ID;
        this.GOODS_NUM = GOODS_NUM;
        this.CNEE_NAME = CNEE_NAME;
        this.CNEE_ADDRESS = CNEE_ADDRESS;
        this.CNEE_PHONE = CNEE_PHONE;
        this.CREATE_TIME = CREATE_TIME;
        this.REMARK = REMARK;
    }

    public String getMODE_DESPATCH() {
        return MODE_DESPATCH;
    }

    public void setMODE_DESPATCH(String MODE_DESPATCH) {
        this.MODE_DESPATCH = MODE_DESPATCH;
    }

    public String getPOST_REMARK() {
        return POST_REMARK;
    }

    public void setPOST_REMARK(String POST_REMARK) {
        this.POST_REMARK = POST_REMARK;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getPLAYBACK_ID() {
        return PLAYBACK_ID;
    }

    public void setPLAYBACK_ID(Integer PLAYBACK_ID) {
        this.PLAYBACK_ID = PLAYBACK_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getGOODS_NUM() {
        return GOODS_NUM;
    }

    public void setGOODS_NUM(String GOODS_NUM) {
        this.GOODS_NUM = GOODS_NUM;
    }

    public String getCNEE_NAME() {
        return CNEE_NAME;
    }

    public void setCNEE_NAME(String CNEE_NAME) {
        this.CNEE_NAME = CNEE_NAME;
    }

    public String getCNEE_ADDRESS() {
        return CNEE_ADDRESS;
    }

    public void setCNEE_ADDRESS(String CNEE_ADDRESS) {
        this.CNEE_ADDRESS = CNEE_ADDRESS;
    }

    public String getCNEE_PHONE() {
        return CNEE_PHONE;
    }

    public void setCNEE_PHONE(String CNEE_PHONE) {
        this.CNEE_PHONE = CNEE_PHONE;
    }

    public String getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(String CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }
}
