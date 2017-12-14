package com.fh.entity.system;

/**
 * 竞猜游戏实体类
 */
public class GuessDetail {
    private Integer GUESS_ID;
    private String APP_USER_ID;
    private String DOLL_ID;
    private String GUESS_TYPE;//最终竞猜结果
    private String GUESS_KEY;//1中 0不中
    private Integer GUESS_GOLD;
    private String CREATE_DATE;
    private String PLAYBACK_ID;
    private String SETTLEMENT_FLAG;//Y 清算 N 未清算
    private String SETTLEMENT_DATE;

    public GuessDetail(){

    }

    public GuessDetail(String APP_USER_ID, String DOLL_ID, String GUESS_KEY, Integer GUESS_GOLD, String PLAYBACK_ID) {
        this.APP_USER_ID = APP_USER_ID;
        this.DOLL_ID = DOLL_ID;
        this.GUESS_KEY = GUESS_KEY;
        this.GUESS_GOLD = GUESS_GOLD;
        this.PLAYBACK_ID = PLAYBACK_ID;
    }

    public GuessDetail(String GUESS_KEY, String PLAYBACK_ID) {
        this.GUESS_KEY = GUESS_KEY;
        this.PLAYBACK_ID = PLAYBACK_ID;
    }

    public Integer getGUESS_ID() {
        return GUESS_ID;
    }

    public void setGUESS_ID(Integer GUESS_ID) {
        this.GUESS_ID = GUESS_ID;
    }

    public String getAPP_USER_ID() {
        return APP_USER_ID;
    }

    public void setAPP_USER_ID(String APP_USER_ID) {
        this.APP_USER_ID = APP_USER_ID;
    }

    public String getDOLL_ID() {
        return DOLL_ID;
    }

    public void setDOLL_ID(String DOLL_ID) {
        this.DOLL_ID = DOLL_ID;
    }

    public String getGUESS_TYPE() {
        return GUESS_TYPE;
    }

    public void setGUESS_TYPE(String GUESS_TYPE) {
        this.GUESS_TYPE = GUESS_TYPE;
    }

    public String getGUESS_KEY() {
        return GUESS_KEY;
    }

    public void setGUESS_KEY(String GUESS_KEY) {
        this.GUESS_KEY = GUESS_KEY;
    }

    public Integer getGUESS_GOLD() {
        return GUESS_GOLD;
    }

    public void setGUESS_GOLD(Integer GUESS_GOLD) {
        this.GUESS_GOLD = GUESS_GOLD;
    }

    public String getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(String CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getPLAYBACK_ID() {
        return PLAYBACK_ID;
    }

    public void setPLAYBACK_ID(String PLAYBACK_ID) {
        this.PLAYBACK_ID = PLAYBACK_ID;
    }

    public String getSETTLEMENT_FLAG() {
        return SETTLEMENT_FLAG;
    }

    public void setSETTLEMENT_FLAG(String SETTLEMENT_FLAG) {
        this.SETTLEMENT_FLAG = SETTLEMENT_FLAG;
    }

    public String getSETTLEMENT_DATE() {
        return SETTLEMENT_DATE;
    }

    public void setSETTLEMENT_DATE(String SETTLEMENT_DATE) {
        this.SETTLEMENT_DATE = SETTLEMENT_DATE;
    }
}
