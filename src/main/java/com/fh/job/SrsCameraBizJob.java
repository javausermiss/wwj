package com.fh.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.fh.service.system.camera.CameraManager;
import com.fh.service.system.doll.DollManager;
import com.fh.util.Logger;
import com.fh.vo.system.DollVo;
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
	
	@Autowired
	private CameraManager cameraManager;
	
	@Autowired
	private DollManager dollManager;
	
	/**
	 * 定时任务同步方法
	 * @throws Exception
	 */
	public void getCameraStateJob() throws Exception{
		List<DollVo> list=dollManager.getDollVoList();
		
		logger.info("srs on_connect events params is "+JSONObject.toJSONString(list));
		
//		try{
//			//获取所有的流媒体列表
//			StreamsVo vo=srsServerService.getSrsStreams(PropertiesUtils.getCurrProperty("srs.server.address.host"),PropertiesUtils.getCurrProperty("srs.server.api.port"));
//			List<Stream> list=vo.getStreams();
//			
//			//TODO 业务处理状态 更新摄像头信息表推流状态
//			if(list !=null && list.size()>0){
//				List<PageData> clist=cameraManager.listAll(new PageData());
//				if(clist !=null && clist.size()>0){
//					for(PageData pd:clist){
//						for(Stream stream:list){
//							if(pd.get("LIVESTREAM").equals(stream.getName())){
//								System.out.println(JSONObject.toJSON(pd));
//								if(stream.getPublish().isActive()){
//									pd.put("DEVICE_STATE", "0");
//								}else{
//									pd.put("DEVICE_STATE", "1"); //不可用
//								}
//								
//								cameraManager.edit(pd); //更新设备状态
//							}
//						}
//					}
//				}
//			}
//		}catch(Exception e){
//			logger.error("getCameraStateJob error is :", e);
//		}
		

	}

}