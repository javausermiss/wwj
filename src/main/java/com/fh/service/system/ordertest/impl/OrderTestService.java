package com.fh.service.system.ordertest.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Order;
import com.fh.entity.system.TransLog;
import com.fh.service.system.ordertest.OrderTestManager;
import com.fh.service.system.promote.PromoteAppUserListManager;
import com.fh.service.system.promote.PromoteAppUserManager;
import com.fh.service.system.trans.AccountOperManager;
import com.fh.service.system.trans.TransLogManager;
import com.fh.util.Const;
import com.fh.util.Const.AccountTransType;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.StringUtils;
import com.fh.util.resp.TxnResp;

@Service("orderTestService")
public class OrderTestService  implements OrderTestManager{
	
	 private Logger logger = Logger.getLogger(this.getClass());
	 
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    
    @Resource(name = "accountOperService")
    public AccountOperManager accountOperService;
    
    
    /**
     * 账户交易订单
     */
    @Resource(name = "translogService")
    public TransLogManager translogService;
    
    @Resource(name = "promoteAppUserService")
    public PromoteAppUserManager promoteAppUserService;
    
    @Resource(name = "promoteAppUserListService")
    public PromoteAppUserListManager promoteAppUserListService;

    @Override
    public int regmount(Order order) throws Exception {
    	
    	//用户订单类型
    	if(!StringUtils.isEmpty(order.getPAY_TYPE())){
    		order.setPAY_TYPE(Const.OrderPayType.R_TYPE.getValue());
    	}
        return (int)dao.save("OrderMapper.regmount", order);
    }

    @Override
    public int update(Order order) throws Exception {
        return (int)dao.update("OrderMapper.update", order);
    }
    
    /**
     * 用户充值，支付回掉，修改订单状态
     * @param order
     * @return
     * @throws Exception
     */
    public int doRegCallbackUpdateOrder(Order order)throws Exception{
//    	try{
//	    	 //如果当前用户是购买加盟推广权益
//	    	 if(Const.OrderPayType.P_TYPE.getValue().equals(order.getPAY_TYPE())){
//	    		 //用户现金账户开户
//	    		accountOperService.openAccountInfByUser(order.getUSER_ID());
//	    		
//	    		//权益分成
//	    		PageData promotepd= promoteAppUserService.findByUserId(order.getUSER_ID());
//	    		if(promotepd==null){
//	    			promotepd=new PageData();
//	    			promotepd.put("USER_ID", order.getUSER_ID());
//		    		promotepd.put("PRO_MANAGE_ID", "");
//		    		promotepd.put("RETURN_RATIO", "");
//		    		promoteAppUserService.save(promotepd);
//	    		}else{
//		    		promotepd.put("PRO_MANAGE_ID", "");
//		    		promotepd.put("RETURN_RATIO", "");
//		    		promoteAppUserService.edit(promotepd);
//	    		}
//
//	    		promoteAppUserListService.save(promotepd); //权益分成明细表
//	    		
//	    	 }else{
//	    		 
//	    		 //如果当前用户的上级，推广用户ID不为空，则为用户增加权益
//	    		if(StringUtils.isNotEmpty(order.getPRO_USER_ID())){
//	    			//推广用户开户
//	    			TxnResp txnResp=accountOperService.openAccountInfByUser(order.getPRO_USER_ID());
//	    			if(txnResp !=null && txnResp.isRespCode()){
//	       		 		/***添加账户交易订单**/
//		       		 	TransLog transLog=new TransLog();
//		       		    transLog.setTransType(AccountTransType.TRANS_1001.getValue());
//		       		    transLog.setPriAccId(txnResp.getPriAccId()); //用户的主账户ID
//		       		 	translogService.save(transLog);
//	    			}
//	    		}
//	    	 }
//    	}catch(Exception ex){
//    		logger.error("doRegCallbackUpdateOrder Exception:", ex);
//    	}
    	 
    	 return (int)dao.update("OrderMapper.doRegCallbackUpdateOrder", order);
    }

    @Override
    public Order getOrderById(String id) throws Exception {
        return (Order)dao.findForObject("OrderMapper.getOrderById",id);
    }
    
	/**用户充值列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getUserRegDetailList(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrderMapper.getUserRegDetaillistPage", page);
	}
}
