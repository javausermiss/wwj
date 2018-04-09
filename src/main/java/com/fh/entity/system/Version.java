package com.fh.entity.system;

import java.util.Date;

/**
 * 版本内更新实体类
 * @author 
 *
 */
public class Version {
	private String ID;
	private String APP_ID;
	private String APP_NAME;
	private String VERSION_CODE;
	private String VERSION_NAME;
	private String USERAGENT;
	private String DOWNLOAD_URL;
	private String LOG;
	private String UPDATE_INSTALL;
	private String CREATER;
	private Date CREATE_DATE;
	private Date UPDATE_DATE;
	private String MODIFIER;
	
	public Version() {
		super();
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getAPP_ID() {
		return APP_ID;
	}

	public void setAPP_ID(String aPP_ID) {
		APP_ID = aPP_ID;
	}

	public String getAPP_NAME() {
		return APP_NAME;
	}

	public void setAPP_NAME(String aPP_NAME) {
		APP_NAME = aPP_NAME;
	}

	public String getVERSION_CODE() {
		return VERSION_CODE;
	}

	public void setVERSION_CODE(String vERSION_CODE) {
		VERSION_CODE = vERSION_CODE;
	}

	public String getVERSION_NAME() {
		return VERSION_NAME;
	}

	public void setVERSION_NAME(String vERSION_NAME) {
		VERSION_NAME = vERSION_NAME;
	}

	public String getUSERAGENT() {
		return USERAGENT;
	}

	public void setUSERAGENT(String uSERAGENT) {
		USERAGENT = uSERAGENT;
	}

	public String getDOWNLOAD_URL() {
		return DOWNLOAD_URL;
	}

	public void setDOWNLOAD_URL(String dOWNLOAD_URL) {
		DOWNLOAD_URL = dOWNLOAD_URL;
	}

	public String getLOG() {
		return LOG;
	}

	public void setLOG(String lOG) {
		LOG = lOG;
	}

	public String getUPDATE_INSTALL() {
		return UPDATE_INSTALL;
	}

	public void setUPDATE_INSTALL(String uPDATE_INSTALL) {
		UPDATE_INSTALL = uPDATE_INSTALL;
	}

	public String getCREATER() {
		return CREATER;
	}

	public void setCREATER(String cREATER) {
		CREATER = cREATER;
	}

	public Date getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(Date cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public Date getUPDATE_DATE() {
		return UPDATE_DATE;
	}

	public void setUPDATE_DATE(Date uPDATE_DATE) {
		UPDATE_DATE = uPDATE_DATE;
	}

	public String getMODIFIER() {
		return MODIFIER;
	}

	public void setMODIFIER(String mODIFIER) {
		MODIFIER = mODIFIER;
	}
	
	
}
