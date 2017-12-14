package com.fh.entity.system;

/**
 * 奖池实体类
 */
public class Pond {
    private Integer POND_ID;
    private String GUESS_ID;
    private Integer GUESS_Y;
    private Integer GUESS_N;
    private Integer GUESS_GOLD;
    private String CREATE_DATE;
    private String UPDATE_DATE;
    private String POND_FLAG;

    public Pond() {

    }

    public Pond(String GUESS_ID, String CREATE_DATE) {
        this.GUESS_ID = GUESS_ID;
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getPOND_FLAG() {
        return POND_FLAG;
    }

    public void setPOND_FLAG(String POND_FLAG) {
        this.POND_FLAG = POND_FLAG;
    }

    public Integer getPOND_ID() {
        return POND_ID;
    }

    public void setPOND_ID(Integer POND_ID) {
        this.POND_ID = POND_ID;
    }

    public String getGUESS_ID() {
        return GUESS_ID;
    }

    public void setGUESS_ID(String GUESS_ID) {
        this.GUESS_ID = GUESS_ID;
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
