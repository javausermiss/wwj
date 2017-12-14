package com.fh.entity.system;

import com.fh.entity.system.SendGoods;

/**
 * 回放实体类
 */
public class PlayBack {
    private Integer ID;
    private String DOLLNAME;//娃娃名字
    private String USERNAME;//用户昵称
    private String CREATETIME;//创建时间
    private String STATE;//状态
    private String DOLL_URL;
    private Integer DOLLTOTAL;
    private String POSTSTATE;
    private Integer CONVERSIONGOLD;
    private String COUNT;
    private String UPDATETIME;//结算时间
    private String SENDGOODS;
    private String GUESS_TIME;//场次生成时间
    private String IMAGE_URL;//用户头像信息


    public PlayBack() {
    }

    public PlayBack(String DOLLNAME, String USERNAME, String CREATETIME, Integer CONVERSIONGOLD, String STATE) {
        this.DOLLNAME = DOLLNAME;
        this.USERNAME = USERNAME;
        this.CREATETIME = CREATETIME;
        this.CONVERSIONGOLD = CONVERSIONGOLD;
        this.STATE = STATE;
    }

    public PlayBack(String DOLLNAME, String USERNAME) {
        this.DOLLNAME = DOLLNAME;
        this.USERNAME = USERNAME;
    }

    public String getIMAGE_URL() {
        return IMAGE_URL;
    }

    public void setIMAGE_URL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }

    public String getGUESS_TIME() {
        return GUESS_TIME;
    }

    public void setGUESS_TIME(String GUESS_TIME) {
        this.GUESS_TIME = GUESS_TIME;
    }

    public String getSENDGOODS() {
        return SENDGOODS;
    }

    public void setSENDGOODS(String SENDGOODS) {
        this.SENDGOODS = SENDGOODS;
    }

    public void setUPDATETIME(String UPDATETIME) {
        this.UPDATETIME = UPDATETIME;
    }

    public String getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(String COUNT) {
        this.COUNT = COUNT;
    }

    public String getPOSTSTATE() {
        return POSTSTATE;
    }

    public void setPOSTSTATE(String POSTSTATE) {
        this.POSTSTATE = POSTSTATE;
    }

    public Integer getCONVERSIONGOLD() {
        return CONVERSIONGOLD;
    }

    public void setCONVERSIONGOLD(Integer CONVERSIONGOLD) {
        this.CONVERSIONGOLD = CONVERSIONGOLD;
    }

    public Integer getDOLLTOTAL() {
        return DOLLTOTAL;
    }

    public void setDOLLTOTAL(Integer DOLLTOTAL) {
        this.DOLLTOTAL = DOLLTOTAL;
    }

    public String getDOLL_URL() {
        return DOLL_URL;
    }

    public void setDOLL_URL(String DOLL_URL) {
        this.DOLL_URL = DOLL_URL;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getDOLLNAME() {
        return DOLLNAME;
    }

    public void setDOLLNAME(String DOLLNAME) {
        this.DOLLNAME = DOLLNAME;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }
}
