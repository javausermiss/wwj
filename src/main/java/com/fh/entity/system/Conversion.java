package com.fh.entity.system;

/**
 * 娃娃兑换实体类
 */
public class Conversion {
    private Integer ID;
    private String DOLLNAME;
    private  String CREATETIME;
    private String NUMBER;
    private String USERID;
    private String PLAYID;
    private String CONMONEY;


    public Conversion (){}

    public Conversion(String USERID) {
        this.USERID = USERID;
    }

    public Conversion(String DOLLNAME, String CREATETIME, String NUMBER, String USERID, String PLAYID) {
        this.DOLLNAME = DOLLNAME;
        this.CREATETIME = CREATETIME;
        this.NUMBER = NUMBER;
        this.USERID = USERID;
        this.PLAYID = PLAYID;
    }

    public String getCONMONEY() {
        return CONMONEY;
    }

    public void setCONMONEY(String CONMONEY) {
        this.CONMONEY = CONMONEY;
    }

    public String getPLAYID() {
        return PLAYID;
    }

    public void setPLAYID(String PLAYID) {
        this.PLAYID = PLAYID;
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

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getNUMBER() {
        return NUMBER;
    }

    public void setNUMBER(String NUMBER) {
        this.NUMBER = NUMBER;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }
}
