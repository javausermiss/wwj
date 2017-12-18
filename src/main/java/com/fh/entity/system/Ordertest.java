package com.fh.entity.system;

/**
 * 支付订单临时测试实体类
 */
public class Ordertest {
    private String REC_ID;
    private String ORDER_ID;
    private String USER_ID;
    private String CREATETIME;
    private String  REGAMOUNT;
    private String STATUS;

    public Ordertest() {
    }

    public String getREC_ID() {
        return REC_ID;
    }

    public void setREC_ID(String REC_ID) {
        this.REC_ID = REC_ID;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getCREATETIME() {
        return CREATETIME;
    }

    public void setCREATETIME(String CREATETIME) {
        this.CREATETIME = CREATETIME;
    }

    public String getREGAMOUNT() {
        return REGAMOUNT;
    }

    public void setREGAMOUNT(String REGAMOUNT) {
        this.REGAMOUNT = REGAMOUNT;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }
}
