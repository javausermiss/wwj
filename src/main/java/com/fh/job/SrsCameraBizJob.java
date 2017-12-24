package com.fh.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fh.util.Logger;
import com.fh.util.PropertiesUtils;
import com.iot.game.pooh.admin.srs.core.entity.api.Stream;
import com.iot.game.pooh.admin.srs.core.vo.StreamsVo;
import com.iot.game.pooh.admin.srs.interfaces.SrsServerService;

/**
 *srs服务器 摄像头推流状态 Job
 *
 *@author zhuqiuyou
 */
public class SrsCameraBizJob{
	
	private  Logger logger = Logger.getLogger(this.getClass());
    
	@Autowired
    private SrsServerService srsServerService;
	
	/**
	 * 定时任务同步方法
	 * @throws Exception
	 */
	public void getCameraStateJob() throws Exception{
		
		try{
			//获取所有的流媒体列表
			StreamsVo vo=srsServerService.getSrsStreams(PropertiesUtils.getCurrProperty("srs.server.address.host"),PropertiesUtils.getCurrProperty("srs.server.api.port"));
			List<Stream> list=vo.getStreams();
			
			//TODO 业务处理状态 更新摄像头信息表推流状态
			logger.info("list size="+list.size());
		}catch(Exception e){
			logger.error("getCameraStateJob error is :", e);
		}
		

	}

}