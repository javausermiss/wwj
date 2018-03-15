package com.fh.websocket.vo;

/**
 * 玩家pk 信息
 * 
 * @author zqy
 *
 */
public class UserDataBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3364106254589596316L;

	public String getSeatUserId() {
		return seatUserId;
	}

	public void setSeatUserId(String seatUserId) {
		this.seatUserId = seatUserId;
	}

	public String getSeatNickName() {
		return seatNickName;
	}

	public void setSeatNickName(String seatNickName) {
		this.seatNickName = seatNickName;
	}

	public String getSeatImageUrl() {
		return seatImageUrl;
	}

	public void setSeatImageUrl(String seatImageUrl) {
		this.seatImageUrl = seatImageUrl;
	}

	public String getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	private String seatUserId; // 玩家用户Id
	private String seatNickName;
	private String seatImageUrl;
	private String seatNumber; // 玩家席位
}
