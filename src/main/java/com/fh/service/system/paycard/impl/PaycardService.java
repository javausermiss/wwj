package com.fh.service.system.paycard.impl;


import com.fh.dao.DaoSupport;
import com.fh.entity.system.Paycard;
import com.fh.service.system.paycard.PaycardManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("paycardService")
public class PaycardService implements PaycardManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<Paycard> getPayCard() throws Exception {
        return (List<Paycard>)dao.findForList("paycardMapper.getPayCard",null);
    }

    @Override
    public Paycard getGold(String amount) throws Exception {
        return (Paycard)dao.findForObject("paycardMapper.getGold",amount);
    }
}
