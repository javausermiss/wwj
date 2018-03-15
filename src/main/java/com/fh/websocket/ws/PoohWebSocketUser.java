package com.fh.websocket.ws;

import org.springframework.web.socket.WebSocketSession;

import com.fh.websocket.vo.BizDataBean;
import com.fh.websocket.vo.UserDataBean;

public class PoohWebSocketUser implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5748396677349467116L;

	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private WebSocketSession session;

	private String fromUserId; //当前用户Id
	
	private String accessToken;  //当前用户登陆token
	
	private String bizType; //业务类型 TOY_PK：抓娃娃Pk
	
	private  UserDataBean userDataBean; //当前链接的用户信息
	
	private BizDataBean bizDataBean; //当前业务数据

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public UserDataBean getUserDataBean() {
		return userDataBean;
	}

	public void setUserDataBean(UserDataBean userDataBean) {
		this.userDataBean = userDataBean;
	}

	public BizDataBean getBizDataBean() {
		return bizDataBean;
	}

	public void setBizDataBean(BizDataBean bizDataBean) {
		this.bizDataBean = bizDataBean;
	}
	
}
