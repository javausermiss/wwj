package com.fh.job;

import java.util.List;

import javax.annotation.Resource;

import com.fh.service.system.doll.DollManager;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.wwjUtil.RedisUtil;

/**
 *srs服务器 摄像头推流状态 Job
 *
 *@author zhuqiuyou
 */
public class DollStateBizJob{
	
    
    @Resource(name = "dollService")
    private DollManager dollService;
	
	
	/**
	 * 定时任务同步方法
	 * @throws Exception
	 */
	public void getDollStateJob() throws Exception{
		
		//获取已经发布的娃娃机列表
		List<PageData> dollList=dollService.getAllDollListByRelease();
		//遍历娃娃机
		for (PageData pd : dollList) {
			
			//获取网关状态
			String dollState=RedisUtil.getStr("roomInfo:" + pd.getString("DOLL_ID"));
			if(dollState !=null){
				
				//如果redis中存储和mysql相同
				if(!dollState.equals(pd.getString("DOLL_STATE"))){
					pd.put("DOLL_STATE", dollState);
					dollService.updateDollStateByDollId(pd);
				}
			}
		}
		
	}

}