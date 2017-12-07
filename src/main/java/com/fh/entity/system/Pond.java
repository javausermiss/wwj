package com.fh.entity.system;

/**
 * 奖池实体类
 */
public class Pond {
    private Integer POND_ID;
    private Integer PLAYBACK_ID;
    private Integer GUESS_Y;
    private Integer GUESS_N;
    private Integer GUESS_GOLD;
    private String CREATE_DATE;
    private String UPDATE_DATE;

    public Pond() {

    }

    public Pond(Integer PLAYBACK_ID, String CREATE_DATE) {
        this.PLAYBACK_ID = PLAYBACK_ID;
        this.CREATE_DATE = CREATE_DATE;
    }

    public Integer getPOND_ID() {
        return POND_ID;
    }

    public void setPOND_ID(Integer POND_ID) {
        this.POND_ID = POND_ID;
    }

    public Integer getPLAYBACK_ID() {
        return PLAYBACK_ID;
    }

    public void setPLAYBACK_ID(Integer PLAYBACK_ID) {
        this.PLAYBACK_ID = PLAYBACK_ID;
    }

    public Integer getGUESS_Y() {
        return GUESS_Y;
    }

    public void setGUESS_Y(Integer GUESS_Y) {
        this.GUESS_Y = GUESS_Y;
    }

    public Integer getGUESS_N() {
        return GUESS_N;
    }

    public void setGUESS_N(Integer GUESS_N) {
        this.GUESS_N = GUESS_N;
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

    public String getUPDATE_DATE() {
        return UPDATE_DATE;
    }

    public void setUPDATE_DATE(String UPDATE_DATE) {
        this.UPDATE_DATE = UPDATE_DATE;
    }
}
