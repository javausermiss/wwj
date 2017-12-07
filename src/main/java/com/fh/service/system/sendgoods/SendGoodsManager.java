package com.fh.service.system.sendgoods;

import com.fh.entity.system.SendGoods;

public interface SendGoodsManager {

    public int regSendGoods (SendGoods sendGoods)throws Exception;

    public SendGoods getSendGoodsInf(String id)throws Exception;

    public SendGoods getSendGoodsByPlayId(Integer playId)throws Exception;



}
