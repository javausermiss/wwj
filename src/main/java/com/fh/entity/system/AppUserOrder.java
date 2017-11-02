package com.fh.entity.system;

/**
 * 用户订单类
 * @author wjy
 * 创建时间 2017/10/23
 */
public class AppUserOrder {
    private String REC_ID;//主键ID
    private String ORDER_ID;//订单ID
    private String USER_ID;//用户ID
    private String CREATETIME;//创建时间
    private String REGAMOUNT;//充值金额
    private String STATUS;//支付状态
    private String REGMODE;//充值方式
    private String UCTIME;//修改时间
    private String page;//分页对象

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

    public String getREGMODE() {
        return REGMODE;
    }

    public void setREGMODE(String REGMODE) {
        this.REGMODE = REGMODE;
    }

    public String getUCTIME() {
        return UCTIME;
    }

    public void setUCTIME(String UCTIME) {
        this.UCTIME = UCTIME;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
