package com.fh.service.system.ordertest;

import com.fh.entity.system.Order;

public interface OrderTestManager {
    /**
     * 创建订单记录
     * @param order
     * @return
     * @throws Exception
     */
    public int regmount(Order order)throws Exception;

    /**
     * 修改支付状态
     * @param order
     * @return
     * @throws Exception
     */
    public int update(Order order)throws Exception;

    /**
     * 通过ID查询订单信息
     * @param id
     * @return
     * @throws Exception
     */
    public Order getOrderById(String id) throws Exception;

}
