package com.fh.service.system.sign.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Sign;
import com.fh.service.system.sign.SignManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("signService")
public class SignService implements SignManager{

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int insertSign(Sign sign) throws Exception {
        return (int) dao.save("SignMapper.insertSign",sign);
    }

    @Override
    public Sign getSignLastByUserId(String userId) throws Exception {
        return (Sign)dao.findForObject("SignMapper.getSignLastByUserId",userId);
    }

}
