package com.fh.service.system.appuser.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.AppUser;
import com.fh.entity.system.Payment;
import com.fh.service.system.appuser.AppUserAwardListManager;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.util.Const.BaseDictRedisHsetKey;
import com.fh.util.Const.PlayMentCostType;
import com.fh.util.Const.RedisDictKeyConst;
import com.fh.util.wwjUtil.RedisUtil;
import com.fh.util.Logger;
import com.fh.util.PageData;

/** 
 * 说明： USER_CODE
 * 创建人：FH Q313596790
 * 创建时间：2018-02-06
 * @version
 */
@Service("appUserAwardListService")
public class AppUserAwardListService implements AppUserAwardListManager{
	
	 Logger logger = Logger.getLogger(this.getClass());
	 
    @Resource(name = "appuserService")
    private AppuserManager appuserService;
    
    @Resource(name = "paymentService")
    private PaymentManager paymentService;

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("AppUserAwardListMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("AppUserAwardListMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("AppUserAwardListMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppUserAwardListMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("AppUserAwardListMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppUserAwardListMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("AppUserAwardListMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * 查找用户兑换码获取奖励得金币
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public PageData  findAwardCountByUserId(String userId)throws Exception{
		return (PageData)dao.findForObject("AppUserAwardListMapper.findAwardCountByUserId", userId);
	}
	
	
	/**
	 * 查询用户Id是否已经兑换
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int findUserAwardByUserId(String userId)throws Exception{
		return (int)dao.findForObject("AppUserAwardListMapper.findUserAwardByUserId", userId);
	}
	
	/**
	 * 查询用户APP是否已经兑换
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public int findUserAwardByAppId(String appId)throws Exception{
		return (int)dao.findForObject("AppUserAwardListMapper.findUserAwardByAppId", appId);
	}
	
	/**
	 * awarkPd 用户邀请码对象
	 * userId  当前提交邀请码的userId
	 * @throws Exception
	 */
	public void doAwardByUserCode(PageData awarkPd,String userId,String IMEI_ID)throws Exception{
		
		
		//邀请码兑换奖励
		int awardNum=10;
		try{
			String awardNumStr =RedisUtil.hget(BaseDictRedisHsetKey.USER_AWARD_REDIS_HSET.getValue(),RedisDictKeyConst.USER_AWARD_CODE_AMOUNT.getValue());
			awardNum=Integer.parseInt(awardNumStr);
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		PageData appUserAwardList=new PageData();
		appUserAwardList.put("USER_ID", userId);
		appUserAwardList.put("CODE_ID", awarkPd.get("CODE_ID").toString()); //1：分享邀请人，2:兑换邀请码人
		appUserAwardList.put("AWARD_TYPE", "2");
		appUserAwardList.put("AWARD_NUM", awardNum);
		appUserAwardList.put("IMEI_ID", IMEI_ID);
		this.save(appUserAwardList); //邀请明细
		
        
        AppUser appUser = appuserService.getUserByID(userId);
        int userBlance1 = Integer.valueOf(appUser.getBALANCE()) + awardNum;
        appUser.setBALANCE(String.valueOf(userBlance1));
        appuserService.updateAppUserBalanceById(appUser);
        
     
        Payment payment = new Payment();
        payment.setGOLD("+" + awardNum);
        payment.setUSERID(userId);
        payment.setDOLLID(null);
        payment.setCOST_TYPE(PlayMentCostType.cost_type11.getValue());
        payment.setREMARK("邀请码兑换奖励");
        paymentService.reg(payment);
		
		
		//邀请码分享奖励
		int invite_awardNum=10;
		try{
			String award_Invite_NumStr =RedisUtil.hget(BaseDictRedisHsetKey.USER_AWARD_REDIS_HSET.getValue(),RedisDictKeyConst.USER_AWARD_CODE_INVITE_AMOUNT.getValue());
			invite_awardNum=Integer.parseInt(award_Invite_NumStr);
		}catch(Exception ex){
			logger.info(ex.getMessage());
		}
		appUserAwardList=new PageData();
		appUserAwardList.put("USER_ID", awarkPd.getString("USER_ID"));
		appUserAwardList.put("CODE_ID", awarkPd.get("CODE_ID").toString()); //1：分享邀请人，2:兑换邀请码人
		appUserAwardList.put("AWARD_TYPE", "1");
		appUserAwardList.put("AWARD_NUM",invite_awardNum);
		this.save(appUserAwardList);
		
        appUser = appuserService.getUserByID(awarkPd.getString("USER_ID"));
        int userBlance2 = Integer.valueOf(appUser.getBALANCE()) + invite_awardNum;
        appUser.setBALANCE(String.valueOf(userBlance2));
        appuserService.updateAppUserBalanceById(appUser);
        
     
        payment = new Payment();
        payment.setGOLD("+" + invite_awardNum);
        payment.setUSERID(awarkPd.getString("USER_ID"));
        payment.setDOLLID(null);
        payment.setCOST_TYPE(PlayMentCostType.cost_type12.getValue());
        payment.setREMARK("邀请码分享奖励");
        paymentService.reg(payment);
	}
}

