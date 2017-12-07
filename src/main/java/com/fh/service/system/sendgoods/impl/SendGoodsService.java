package com.fh.service.system.sendgoods.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.SendGoods;
import com.fh.service.system.sendgoods.SendGoodsManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service("sendGoodsService")
public class SendGoodsService implements SendGoodsManager{

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public int regSendGoods(SendGoods sendGoods) throws Exception {
        return (int)dao.save("sendGoodsMapper.regSendGoods",sendGoods);
    }

    @Override
    public SendGoods getSendGoodsInf(String id) throws Exception {
        return null;
    }

    @Override
    public SendGoods getSendGoodsByPlayId(Integer playId) throws Exception {
        return (SendGoods) dao.findForObject("sendGoodsMapper.getSendGoodsByPlayId",playId);
    }
}
