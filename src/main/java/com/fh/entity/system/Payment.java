package com.fh.entity.system;

/**
 * 用户收支实体类
 */
public class Payment {
    private Integer ID;
    private String DOLLID;
    private String GOLD;
    private String USERID;
    private String COST_TYPE;
    private String CREATE_TIME;
    private String REMARK;
    private String UPDATE_TIME;

    public Payment() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDOLLID() {
        return DOLLID;
    }

    public void setDOLLID(String DOLLID) {
        this.DOLLID = DOLLID;
    }

    public String getGOLD() {
        return GOLD;
    }

    public void setGOLD(String GOLD) {
        this.GOLD = GOLD;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getCOST_TYPE() {
        return COST_TYPE;
    }

    public void setCOST_TYPE(String COST_TYPE) {
        this.COST_TYPE = COST_TYPE;
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
