package com.fh.websocket.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;

import com.fh.websocket.ws.PoohWebSocketUser;

import net.sf.json.JSONObject;

/**
 * WebSocket信息发送工具类
 * 采用Websokect-Redis分布部署：
 * webSocket项目分布式部署时，采用Redis的订阅和发布机制解决服务器间的通信
 * @author zqy
 *
 */
public class WebSocketMessageUtil {
	
	private static Logger log = LoggerFactory.getLogger(WebSocketMessageUtil.class);
	
	/**
	 * 
	 * @param server 发送对象
	 * @param message 发送信息
	 */
	public static  void  sendMessageToOne(String toUser ,TextMessage message){
		PoohWebSocketUser toServer = WebSocketServerPool.getWebSocketServer(toUser);
		if(toServer!=null && toServer.getSession()!=null){											
	
			try {
				if(toServer.getSession().isOpen()){
					PoohWebSocketUser wcUser= WebSocketServerPool.getWebSocketServer(toUser);
					if (wcUser.getSession() != null && wcUser.getSession().isOpen()) {
						wcUser.getSession().sendMessage(message);
					}
				}	
			} catch (Exception e) {				
				log.error(toServer.getFromUserId()+":"+e.fillInStackTrace());	
			}
			
		}
	}
	

	
	/**
	 * 构建统一格式消息
	 * 
	 * 
	 * @return
	 */
	public static String buildMessage(String message)
	{
		JSONObject result = JSONObject.fromObject(message);				
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");		
		result.element("sendTime", format.format(new Date()));		
		return result.toString();
	}
	
	
}
