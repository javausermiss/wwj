package com.fh.vo.system;

import java.util.Date;

/**
 * 摄像头实体类
 */
public class CameraVo {
	
	private String cameraId;
	private String dollId;
	private String cameraName;
	private String cameraNum;
	private String rtmpUrl;
	private String h5Url;
	private String serverName;
	private String livestream;
	private String serverId;
	private String clientId;
	private String cameraType;
	private String deviceState;
	private Date createDate;
	private Date updateDate;
	private Integer lockVersion;
	
	public String getCameraId() {
		return cameraId;
	}
	public void setCameraId(String cameraId) {
		this.cameraId = cameraId;
	}
	public String getDollId() {
		return dollId;
	}
	public void setDollId(String dollId) {
		this.dollId = dollId;
	}
	public String getCameraName() {
		return cameraName;
	}
	public void setCameraName(String cameraName) {
		this.cameraName = cameraName;
	}
	public String getCameraNum() {
		return cameraNum;
	}
	public void setCameraNum(String cameraNum) {
		this.cameraNum = cameraNum;
	}
	public String getRtmpUrl() {
		return rtmpUrl;
	}
	public void setRtmpUrl(String rtmpUrl) {
		this.rtmpUrl = rtmpUrl;
	}
	public String getH5Url() {
		return h5Url;
	}
	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getLivestream() {
		return livestream;
	}
	public void setLivestream(String livestream) {
		this.livestream = livestream;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getClientId() {
		return clientId;
	}
	public String getCameraType() {
		return cameraType;
	}
	public void setCameraType(String cameraType) {
		this.cameraType = cameraType;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getDeviceState() {
		return deviceState;
	}
	public void setDeviceState(String deviceState) {
		this.deviceState = deviceState;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Integer getLockVersion() {
		return lockVersion;
	}
	public void setLockVersion(Integer lockVersion) {
		this.lockVersion = lockVersion;
	}

}
