
package com.fh.websocket.ws;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;


/**
 * Socket建立连接（握手）和断开
 */
@Component
public class PoohWebHandShake implements HandshakeInterceptor{
	
	private Logger logger = LoggerFactory.getLogger(PoohWebHandShake.class);
	
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {

		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			String fromUserId= servletRequest.getServletRequest().getParameter("fromUserId");
			String bizType= servletRequest.getServletRequest().getParameter("bizType");
			String accessToken= servletRequest.getServletRequest().getParameter("accessToken");
			logger.info("Websocket:用户[ID:" + fromUserId+ "]已经建立连接在---》" +servletRequest.getServletRequest().getLocalAddr()+":"+servletRequest.getServletRequest().getLocalPort());
			if(fromUserId!=null){
				attributes.put("fromUserId", fromUserId);
				attributes.put("bizType",bizType);
				attributes.put("accessToken",accessToken);
			}else{
				return false;
			}
		}
		return true;
	}

	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
	
	}

}
