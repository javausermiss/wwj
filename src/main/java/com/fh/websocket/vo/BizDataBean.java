package com.fh.websocket.vo;

import java.util.Map;

/**
 * 业务数据处理
 * @author JAVA_DEV
 *
 */
public class BizDataBean implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8534074348269010149L;
	
	
	private String toType; //发送消息类型
	
	private String toUserId; //接受对象用户Id
	
	//发送的文本
	private String data;
	
	private String reqType; //10:心跳
	
	private String sendType;
	
	

	public String getToType() {
		return toType;
	}

	public void setToType(String toType) {
		this.toType = toType;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getSendType() {
		return sendType;
	}

	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	
}
