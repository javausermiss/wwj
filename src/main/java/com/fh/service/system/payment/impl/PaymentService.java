package com.fh.service.system.payment.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Payment;
import com.fh.service.system.payment.PaymentManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("paymentService")
public class PaymentService implements PaymentManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int reg(Payment payment) throws Exception {
        return (int)dao.save("paymentMapper.reg",payment);
    }
}
