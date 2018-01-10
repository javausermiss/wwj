package com.fh.service.system.ordertest.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Order;
import com.fh.service.system.ordertest.OrderTestManager;
import com.fh.util.PageData;

import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service("orderTestService")
public class OrderTestService  implements OrderTestManager{
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int regmount(Order order) throws Exception {
        return (int)dao.save("OrderMapper.regmount", order);
    }

    @Override
    public int update(Order order) throws Exception {
        return (int)dao.update("OrderMapper.update", order);
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
