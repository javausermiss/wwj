package com.fh.service.system.ordertest.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Ordertest;
import com.fh.service.system.ordertest.OrderTestManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("orderTestService")
public class OrderTestService  implements OrderTestManager{
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int regmount(Ordertest ordertest) throws Exception {
        return (int)dao.save("OrderMapper.regmount",ordertest);
    }

    @Override
    public int update(Ordertest ordertest) throws Exception {
        return (int)dao.update("OrderMapper.update",ordertest);
    }

    @Override
    public Ordertest getOrderById(String id) throws Exception {
        return (Ordertest)dao.findForObject("OrderMapper.getOrderById",id);
    }
}
