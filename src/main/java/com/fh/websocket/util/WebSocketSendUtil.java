package com.fh.websocket.util;

import org.springframework.web.socket.TextMessage;

import com.alibaba.fastjson.JSONArray;
import com.fh.websocket.vo.BizDataBean;


/**
 * 消息业务发送处理类 调用工具进行发送
 * 
 * @author zqy
 *
 */
public class WebSocketSendUtil {
	/**
	 * 发送信息<br>
	 * 如果开启redis则发布订阅信息<br>
	 * @param toTarget ONE、BUSS必须
	 * @param message
	 */
	public static void sendMessageToRedis(String toTarget ,String message) {
		
	}
	
	/**
	 * 发送信息<br>
	 * 如果开启redis则发布订阅信息<br>
	 * @param toType
	 * @param toTarget ONE、BUSS必须
	 * @param message
	 */
	public static void sendMessageTo(String message) {		
	
	}
	
	/**
	 * 发送信息<br>
	 * 如果开启redis则发布订阅信息<br>
	 * @param message
	 */
	public static void sendMessageToUser(BizDataBean msg){
		
	}
	
}