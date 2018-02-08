package com.fh.controller.wwjapp;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.appuser.AppUserCodeManager;
import com.fh.service.system.appuser.impl.AppUserAwardListService;
import com.fh.util.PageData;
import com.fh.util.RandomUtils;
import com.fh.util.StringUtils;
import com.fh.util.Const.BaseDictRedisHsetKey;
import com.fh.util.Const.RedisDictKeyConst;
import com.fh.util.wwjUtil.RedisUtil;
import com.fh.util.wwjUtil.RespStatus;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/app/award")
public class AppAwardController extends BaseController {


    @Resource(name = "appUserAwardListService")
    private AppUserAwardListService appUserAwardListService;


    @Resource(name = "appUserCodeService")
    private AppUserCodeManager appUserCodeService;
    
    
    /**
     * 获取用户邀请码
     * @return
     */
    @RequestMapping(value = "/getUserAwardCode", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getUserAwardCode(HttpServletRequest request) {
    	
    	
    	PageData reqData=this.getBaseParams(request);  //获取请求参数
    	String userId=reqData.getString("userId");
    	
    	//判断用户Id是否为空
    	if(StringUtils.isEmpty(userId)){
    		return RespStatus.exception();
    	}
    	try {
    		//获取用户兑换码
    		PageData userCode=appUserCodeService.getUserCodeByUserId(reqData.getString("userId"));
    		if(userCode==null){
    			String randomCode=RandomUtils.getRandomLetterStr(8);
    			userCode=appUserCodeService.getUserCodeByCode(randomCode);
    			if(userCode!=null){
    				randomCode=RandomUtils.getRandomLetterStr(8);
    			}
    			userCode=new PageData();
    			userCode.put("USER_ID", userId);
    			userCode.put("CODE_VALUE", randomCode);
    			userCode.put("CODE_NUM", 100);
    			userCode.put("CODE_TYPE", "1");
    			int oper=appUserCodeService.save(userCode); //保存用户兑换码
    			if(oper<1){
    				 return RespStatus.exception();
    			}
    		}
			
			//查询统计用户分享数 和 分享奖励金币数量
			PageData awardPd=appUserAwardListService.findAwardCountByUserId(userId);
			
			//兑换奖励限制
			PageData setPd=new PageData();
			//兑换金币数
			setPd.put("exchangeAmount", RedisUtil.hget(BaseDictRedisHsetKey.USER_AWARD_REDIS_HSET.getValue(),RedisDictKeyConst.USER_AWARD_CODE_AMOUNT.getValue()));
			//邀请金币数量
			setPd.put("inviteAmount", RedisUtil.hget(BaseDictRedisHsetKey.USER_AWARD_REDIS_HSET.getValue(),RedisDictKeyConst.USER_AWARD_CODE_INVITE_AMOUNT.getValue()));
			//邀请可兑换次数
			setPd.put("inviteTotalNum", RedisUtil.hget(BaseDictRedisHsetKey.USER_AWARD_REDIS_HSET.getValue(),RedisDictKeyConst.USER_AWARD_CODE_NUM.getValue()));
			

			
			//返回
			 Map<String, Object> dataMap = new HashMap<>();
			 dataMap.put("awardPd", awardPd);
			 dataMap.put("setPd", setPd);
			 dataMap.put("codeVale", userCode.getString("CODE_VALUE"));
			 
			/**查看用户是否已经兑换*/
			int s=appUserAwardListService.findUserAwardByUserId(userId);
			if(s>0){
				 dataMap.put("awradFlag", "Y");
			}else{
				 dataMap.put("awradFlag", "N");
			}
	
             return RespStatus.successs().element("data", dataMap);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return RespStatus.fail();
		}
    }
    
    
    /**
     * 获取邀请码兑换
     * @return
     */
    @RequestMapping(value = "/doAwardByUserCode", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject doAwardByUserCode(HttpServletRequest request) {
    	
    	PageData reqData=this.getBaseParams(request);  //获取请求参数
    	
    	String userId=reqData.getString("userId"); //用户Id
    	
    	String awardCode=request.getParameter("awardCode"); //用户兑换码
    	
    	//判断用户Id 和 邀请码是否为空
    	if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(awardCode)){
    		return RespStatus.exception();
    	}
    	
    	try {
    		
    		//step1 判断当前App是否已经兑换过
    		int s=appUserAwardListService.findUserAwardByUserId(userId);
			if(s>0){
				return RespStatus.fail("当前用户已经兑换");
			}
    		//step2 判断当前App是否已经兑换过
			if(!StringUtils.isEmpty(reqData.getString("sfId"))){
				//当前用户是否已经兑换
				s=appUserAwardListService.findUserAwardByAppId(reqData.getString("sfId"));
				if(s>0){
					return RespStatus.fail("当前设备已经兑换");
				}
			}
    		
    		//step3 判断兑换码是否存在
			PageData awarkPd=appUserCodeService.getUserCodeByCode(awardCode);
			if(awarkPd==null){
				return RespStatus.fail("兑换码不存在");
			}
			
			//step4 判断兑换码是否是当前用户
			if(userId.equals(awarkPd.getString("USER_ID"))){
				return RespStatus.fail("兑换码是当前用户");
			}
			
			//step5 查询兑换码已经兑换的次数
			PageData awardTotal=appUserAwardListService.findAwardCountByUserId(awarkPd.getString("USER_ID"));
			int awardCount=0; //已经兑换次数
			if(awardTotal !=null){
				//已经兑换的次数
				awardCount=Integer.parseInt(awardTotal.get("AWARDCOUNT").toString());
			}
			int invite_awardTotal=100;
			try{
				String award_Total_NumStr =RedisUtil.hget(BaseDictRedisHsetKey.USER_AWARD_REDIS_HSET.getValue(),RedisDictKeyConst.USER_AWARD_CODE_NUM.getValue());
				invite_awardTotal=Integer.parseInt(award_Total_NumStr);
			}catch(Exception ex){
				logger.info(ex.getMessage());
			}
			
			if(awardCount>invite_awardTotal){
				return RespStatus.fail("兑换码次数已经超过限制");
			}
			appUserAwardListService.doAwardByUserCode(awarkPd, userId, reqData.getString("sfId"));
			
			//兑换奖励限制
			PageData setPd=new PageData();
			//兑换金币数
			setPd.put("exchangeAmount", RedisUtil.hget(BaseDictRedisHsetKey.USER_AWARD_REDIS_HSET.getValue(),RedisDictKeyConst.USER_AWARD_CODE_AMOUNT.getValue()));
			//邀请金币数量
			setPd.put("inviteAmount", RedisUtil.hget(BaseDictRedisHsetKey.USER_AWARD_REDIS_HSET.getValue(),RedisDictKeyConst.USER_AWARD_CODE_INVITE_AMOUNT.getValue()));
			//邀请可兑换次数
			setPd.put("inviteTotalNum", RedisUtil.hget(BaseDictRedisHsetKey.USER_AWARD_REDIS_HSET.getValue(),RedisDictKeyConst.USER_AWARD_CODE_NUM.getValue()));
			 
			
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("setPd", setPd);
			
			/**查看用户是否已经兑换*/
			s=appUserAwardListService.findUserAwardByUserId(userId);
			if(s>0){
				 dataMap.put("awradFlag", "Y");
			}else{
				 dataMap.put("awradFlag", "N");
			}
			
			return RespStatus.successs().element("data", dataMap);
			
		} catch (Exception e) {
			logger.info(e.getMessage());
			return RespStatus.fail();
		}
    
    }

}
