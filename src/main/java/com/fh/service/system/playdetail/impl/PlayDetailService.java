package com.fh.service.system.playdetail.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.PlayDetail;
import com.fh.service.system.playdetail.PlayDetailManage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("playDetailService")
public class PlayDetailService implements PlayDetailManage {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int reg(PlayDetail playDetail) throws Exception {
        return (int)dao.save("playDetailMapper.reg",playDetail);
    }

    @Override
    public PlayDetail getPlayDetailByGuessID(String guessId) throws Exception {
        return (PlayDetail)dao.findForObject("playDetailMapper.getPlayDetailByGuessID",guessId);
    }

    @Override
    public PlayDetail getPlayDetailLast(PlayDetail playDetail) throws Exception {
        return (PlayDetail)dao.findForObject("playDetailMapper.getPlayDetailLast",playDetail);
    }

    @Override
    public PlayDetail getPlayIdForPeople(String dollname) throws Exception {
        return (PlayDetail)dao.findForObject("playDetailMapper.getPlayIdForPeople",dollname);
    }

    @Override
    public PlayDetail getPlayDetailByRoomId(String roomId) throws Exception {
        return (PlayDetail)dao.findForObject("playDetailMapper.getPlayDetailByRoomId",roomId);
    }

    @Override
    public int updatePlayDetailStopFlag(PlayDetail playDetail) throws Exception {
        return (int)dao.update("playDetailMapper.updatePlayDetailStopFlag",playDetail);
    }

    @Override
    public int updatePlayDetailState(PlayDetail playDetail) throws Exception {
        return (int)dao.update("playDetailMapper.updatePlayDetailState",playDetail);
    }
}
