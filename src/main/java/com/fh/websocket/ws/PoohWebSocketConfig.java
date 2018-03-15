package com.fh.websocket.ws;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebScoket配置处理器
 * @author zqy
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class PoohWebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {
	
	private  Logger logger =  LoggerFactory.getLogger(PoohWebSocketConfig.class);

	@Resource
	PoohWebSocketHandler handler;

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

		registry.addHandler(handler, "/app/websocket_cashier.do").addInterceptors(new PoohWebHandShake()).setAllowedOrigins("*");

		registry.addHandler(handler, "/app/sockjs/websocket_cashier.html").addInterceptors(new PoohWebHandShake()).withSockJS();
		
		logger.info("----------------------------spring websocket register 成功----------------------------");
	}

}
