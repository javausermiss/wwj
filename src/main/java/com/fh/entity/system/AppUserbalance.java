package com.fh.entity.system;

/**
 * 用户余额类
 * @author wjy
 * 创建时间2017/10/23
 */
public class AppUserbalance {
    private String RES_ID;//主键ID
    private  String USER_ID;//用户ID
    private  String BALANCE;//用户余额
    private  String UCTIME;//更新日期
    private String page;//分页对象

    public String getRES_ID() {
        return RES_ID;
    }

    public void setRES_ID(String RES_ID) {
        this.RES_ID = RES_ID;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public String getBALANCE() {
        return BALANCE;
    }

    public void setBALANCE(String BALANCE) {
        this.BALANCE = BALANCE;
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
