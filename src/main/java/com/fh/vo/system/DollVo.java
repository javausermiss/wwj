package com.fh.vo.system;

import java.util.List;

/**
 * 娃娃机实体类
 */

public class DollVo {

    private String dollId;
    private String dollName;
    private String dollState;
    private String roomId;
    private String dollSn;
    private Integer dollGold;
    private String dollUrl;
    private String cameraName01;
    private String cameraName02;
    private String dollCnversiongold;
    
    private List<CameraVo> cameras;

	public String getDollId() {
		return dollId;
	}

	public void setDollId(String dollId) {
		this.dollId = dollId;
	}

	public String getDollName() {
		return dollName;
	}

	public void setDollName(String dollName) {
		this.dollName = dollName;
	}

	public String getDollState() {
		return dollState;
	}

	public void setDollState(String dollState) {
		this.dollState = dollState;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getDollSn() {
		return dollSn;
	}

	public void setDollSn(String dollSn) {
		this.dollSn = dollSn;
	}

	public Integer getDollGold() {
		return dollGold;
	}

	public void setDollGold(Integer dollGold) {
		this.dollGold = dollGold;
	}

	public String getDollUrl() {
		return dollUrl;
	}

	public void setDollUrl(String dollUrl) {
		this.dollUrl = dollUrl;
	}

	public String getCameraName01() {
		return cameraName01;
	}

	public void setCameraName01(String cameraName01) {
		this.cameraName01 = cameraName01;
	}

	public String getCameraName02() {
		return cameraName02;
	}

	public void setCameraName02(String cameraName02) {
		this.cameraName02 = cameraName02;
	}

	public String getDollCnversiongold() {
		return dollCnversiongold;
	}

	public void setDollCnversiongold(String dollCnversiongold) {
		this.dollCnversiongold = dollCnversiongold;
	}

	public List<CameraVo> getCameras() {
		return cameras;
	}

	public void setCameras(List<CameraVo> cameras) {
		this.cameras = cameras;
	}
    
}
