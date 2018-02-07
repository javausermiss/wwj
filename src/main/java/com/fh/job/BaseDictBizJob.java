package com.fh.job;

import java.util.List;

import javax.annotation.Resource;

import com.fh.service.system.dict.BaseDictManager;
import com.fh.util.Const.BaseDictRedisHsetKey;
import com.fh.util.PageData;
import com.fh.util.wwjUtil.RedisUtil;

/**
 * 字典数据刷新 Job
 *
 *@author zhuqiuyou
 */
public class BaseDictBizJob{
	
    
    @Resource(name = "baseDictService")
    private BaseDictManager baseDictService;
	
	
	/**
	 * 定时任务同步方法
	 * @throws Exception
	 */
	public void doBaseDictJob() throws Exception{
		
		//获取所有的字典数据
		List<PageData> dictList=baseDictService.listAll(null);
		for (PageData pd : dictList) {
			RedisUtil.hset(BaseDictRedisHsetKey.USER_AWARD_REDIS_HSET.getValue(), pd.getString("DICT_KEY"),pd.getString("DICT_VALUE"));
		}
	}

}