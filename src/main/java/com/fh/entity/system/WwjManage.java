package com.fh.entity.system;

import java.sql.Date;

/**
 * 渠道推广实体类
 */
public class WwjManage {
	private Integer PRO_MANAGE_ID;
	private String PAY_AMOUNT;
	private String GOLD;
	private Integer IMG_URL;
	private String TYPE;
	private String RETURN_RATIO;
	private Date CREATE_TIME;
	private Date UPDATE_TIME;
	
	public WwjManage() {
		super();
	}

	public Integer getPRO_ID() {
		return PRO_MANAGE_ID;
	}

	public void setPRO_ID(Integer pRO_ID) {
		PRO_MANAGE_ID = pRO_ID;
	}

	public String getPAY_AMOUNT() {
		return PAY_AMOUNT;
	}

	public void setPAY_AMOUNT(String pAY_AMOUNT) {
		PAY_AMOUNT = pAY_AMOUNT;
	}

	public String getGOLD() {
		return GOLD;
	}

	public void setGOLD(String gOLD) {
		GOLD = gOLD;
	}

	public Integer getIMG_URL() {
		return IMG_URL;
	}

	public void setIMG_URL(Integer iMG_URL) {
		IMG_URL = iMG_URL;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public String getRETURN_RATIO() {
		return RETURN_RATIO;
	}

	public void setRETURN_RATIO(String rETURN_RATIO) {
		RETURN_RATIO = rETURN_RATIO;
	}

	public Date getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(Date cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

	public Date getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	public void setUPDATE_TIME(Date uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}

	@Override
	public String toString() {
		return "WwjManage [PRO_ID=" + PRO_MANAGE_ID + ", PAY_AMOUNT=" + PAY_AMOUNT + ", GOLD=" + GOLD + ", IMG_URL=" + IMG_URL
				+ ", TYPE=" + TYPE + ", RETURN_RATIO=" + RETURN_RATIO + ", CREATE_TIME=" + CREATE_TIME
				+ ", UPDATE_TIME=" + UPDATE_TIME + "]";
	}
}
