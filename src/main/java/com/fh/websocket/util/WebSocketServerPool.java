package com.fh.websocket.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import com.fh.websocket.ws.PoohWebSocketUser;



/**
 * WebSokect连接信息池
 * 实现服务连接的存储和获取
 * @author zqy
 *
 */
public class WebSocketServerPool {
	
	private static Logger log = LoggerFactory.getLogger(WebSocketServerPool.class);
	/**
	 * HashMap不能直接用于多线程
	 * ConcurrentHashMap的出现正解决上诉问题,是HashMap的线程安全版本，性能方面也优于HashTable
	 */
	private static final  ConcurrentHashMap<String,PoohWebSocketUser> connections = new ConcurrentHashMap<String,PoohWebSocketUser>();//保存连接的Map容器
	
	
	/**
	 * 向连接池中添加连接
	 *
	 * @author bai
	 */
	public static void addWebSocketServer(PoohWebSocketUser wServer){
		
		if(wServer == null)
		{
			return ;
		}
		if(connections.get(wServer.getFromUserId())==null)
		{
			connections.put(wServer.getFromUserId(), wServer);				
		}			
	}	
	
	
	/**
	 * 移除服务连接
	 *
	 * @author bai
	 */
	public static void removeWebSocketServer(PoohWebSocketUser wServer){
		if(wServer == null)
		{
			return ;
		}		
		connections.remove(wServer.getFromUserId());
	}
	

	/**
	 * 获取当前服务器所有在线用户
	 * @param wServer
	 * @return 返回所有用户bussId_userId
	 */
	public static Set<String> getAllOnlineUser()
	{		
		return connections.keySet();
	}
	

	/**
	 * 获取服务连接 userId 对象
	 * @param userId 全KEY
	 * @return
	 */
	public static PoohWebSocketUser getWebSocketServer(String userId)
	{
		return connections.get(userId);
	}
	
	
	public static void closeSession(WebSocketSession session){
		String fromUserId = (String) session.getAttributes().get("fromUserId");
		Iterator<Entry<String, PoohWebSocketUser>> it = connections.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, PoohWebSocketUser> entry = it.next();
			if (entry.getKey().equals(fromUserId)) {
				connections.remove(entry.getKey());
				log.info("Socket会话已经移除:用户ID" + entry.getKey()+", sessionId==" + session.getId());
				if (session.isOpen()) {
					try {
						session.close();
					} catch (IOException e) {
						
						e.printStackTrace();
					}
				}
			}
		}
	}
}
