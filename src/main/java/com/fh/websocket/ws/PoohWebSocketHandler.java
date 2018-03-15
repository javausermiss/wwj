package com.fh.websocket.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fh.util.StringUtils;
import com.fh.websocket.util.WebSocketServerPool;



/**
 * Socket处理器
 */
@Component
public class PoohWebSocketHandler implements WebSocketHandler {
	
	private  Logger logger =  LoggerFactory.getLogger(PoohWebSocketHandler.class);
	

	/**
	 * 建立连接后,把登录用户的id写入WebSocketSession
	 */
	public void afterConnectionEstablished(WebSocketSession session)throws Exception {
		
		 String fromUserId=(String) session.getAttributes().get("fromUserId"); //当前用户Id
		 String accessToken=(String) session.getAttributes().get("accessToken");  //当前用户登陆token
		 String bizType=(String) session.getAttributes().get("bizType"); //业务类型
		 
			if (StringUtils.isNotEmpty(fromUserId)) {
				PoohWebSocketUser wcUser=new PoohWebSocketUser();
				wcUser.setSession(session);
				wcUser.setFromUserId(fromUserId);
				wcUser.setAccessToken(accessToken);
				wcUser.setBizType(bizType);
				WebSocketServerPool.addWebSocketServer(wcUser);
			}
	}
	
	/**
	 * 关闭连接后
	 */
	public void afterConnectionClosed(WebSocketSession session,CloseStatus closeStatus) throws Exception {
		WebSocketServerPool.closeSession(session);
	}

	/**
	 * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
	 */
	public  void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		System.out.println("消息处理开始-----");
		logger.info(message.getPayload().toString());
		session.sendMessage(message);
	}

	/**
	 * 消息传输错误处理
	 */
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
	}

	public boolean supportsPartialMessages() {
		return false;
	}

}
