package com.fh.websocket.vo;

/**
 * 用户发送websocket 消息业务bean
 * 
 * @author zqy
 *
 */
public class PlayerPkDataBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1039137270372607743L;

	private String userId;
	private String nickName;
	private String phone;
	private String imageUrl;
	private String balance;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

}
