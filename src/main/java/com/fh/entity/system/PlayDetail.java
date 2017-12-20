package com.fh.entity.system;

/**
 * 游戏记录实体类
 */
public class PlayDetail {

    private String ID;//主键
    private String USERID;//用户昵称
    private String GUESS_ID;//场次ID
    private String DOLLID;//房间昵称
    private String STATE;//是否抓取成功'1'抓取成功 '0'抓取失败
    private String CREATE_DATE;//创建时间
    private String GOLD;
    private String STOP_FLAG;//0可以下注 -1禁止下注
    private String CONVERSIONGOLD;


    public PlayDetail() {
    }

    public String getSTOP_FLAG() {
        return STOP_FLAG;
    }

    public void setSTOP_FLAG(String STOP_FLAG) {
        this.STOP_FLAG = STOP_FLAG;
    }

    public String getGOLD() {
        return GOLD;
    }

    public void setGOLD(String GOLD) {
        this.GOLD = GOLD;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCONVERSIONGOLD() {
        return CONVERSIONGOLD;
    }

    public void setCONVERSIONGOLD(String CONVERSIONGOLD) {
        this.CONVERSIONGOLD = CONVERSIONGOLD;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getDOLLID() {
        return DOLLID;
    }

    public void setDOLLID(String DOLLID) {
        this.DOLLID = DOLLID;
    }

    public String getGUESS_ID() {
        return GUESS_ID;
    }

    public void setGUESS_ID(String GUESS_ID) {
        this.GUESS_ID = GUESS_ID;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }
}
