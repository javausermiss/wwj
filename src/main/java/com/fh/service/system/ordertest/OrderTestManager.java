package com.fh.service.system.ordertest;

import com.fh.entity.system.Ordertest;

public interface OrderTestManager {
    /**
     * 创建订单记录
     * @param ordertest
     * @return
     * @throws Exception
     */
    public int regmount(Ordertest ordertest)throws Exception;

    /**
     * 修改支付状态
     * @param ordertest
     * @return
     * @throws Exception
     */
    public int update(Ordertest ordertest)throws Exception;

    /**
     * 通过ID查询订单信息
     * @param id
     * @return
     * @throws Exception
     */
    public Ordertest getOrderById(String id) throws Exception;

}
