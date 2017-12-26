package com.fh.service.system.playdetail.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.PlayDetail;
import com.fh.service.system.playdetail.PlayDetailManage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("playDetailService")
public class PlayDetailService implements PlayDetailManage {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int reg(PlayDetail playDetail) throws Exception {
        return (int)dao.save("playDetailMapper.reg",playDetail);
    }

    @Override
    public PlayDetail getPlayDetailByGuessID(PlayDetail playDetail) throws Exception {
        return (PlayDetail)dao.findForObject("playDetailMapper.getPlayDetailByGuessID",playDetail);
    }

    @Override
    public PlayDetail getPlayDetailLast(PlayDetail playDetail) throws Exception {
        return (PlayDetail)dao.findForObject("playDetailMapper.getPlayDetailLast",playDetail);
    }

    @Override
    public PlayDetail getPlayIdForPeople(String dollId) throws Exception {
        return (PlayDetail)dao.findForObject("playDetailMapper.getPlayIdForPeople",dollId);
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

    @Override
    public PlayDetail getPlayDetailForCamera(PlayDetail playDetail) throws Exception {
        return (PlayDetail) dao.findForObject("playDetailMapper.getPlayDetailForCamera",playDetail);
    }

    @Override
    public int updatePlayDetailForCamera(PlayDetail playDetail) throws Exception {
        return (int)dao.update("playDetailMapper.updatePlayDetailForCamera",playDetail);
    }

    @Override
    public List<PlayDetail> getPlaylist(String userId) throws Exception {
        return (List<PlayDetail>)dao.findForList("playDetailMapper.getPlaylist",userId);
    }

    @Override
    public List<PlayDetail> getDollCount(String userId) throws Exception {
        return (List<PlayDetail>)dao.findForList("playDetailMapper.getDollCount",userId);
    }

    @Override
    public List<PlayDetail> getPlayRecordPM() throws Exception {
        return (List<PlayDetail>)dao.findForList("playDetailMapper.getPlayRecordPM",null);
    }

    @Override
    public PlayDetail getPlayDetailByID(Integer id) throws Exception {
        return (PlayDetail)dao.findForObject("playDetailMapper.getPlayDetailById",id);
    }

    @Override
    public int updatePostState(PlayDetail playDetail) throws Exception {
        return (int)dao.update("playDetailMapper.updatePostState",playDetail);
    }

    @Override
    public int updatePostStateForCon(PlayDetail playDetail) throws Exception {
        return (int)dao.update("playDetailMapper.updatePostStateForCon",playDetail);
    }
}
