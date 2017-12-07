package com.fh.service.system.playback.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.PlayBack;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.util.wwjUtil.MyUUID;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("playBackService")
public class PlayBackService implements PlayBackManage {
    @Resource(name = "daoSupport")
    private DaoSupport dao;


    @Override
    public int regPlayBack(String dollname, String username, String time, int gold, String state) throws Exception {
        return (int) dao.save("PlayBackMapper.regPlayBack", new PlayBack(dollname, username, time, gold, state));
    }

    @Override
    public int regPlayBackBalance(PlayBack playBack) throws Exception {
        return (int) dao.save("PlayBackMapper.regPlayBackBalance", playBack);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlayBack> getPlayRecord(String username) throws Exception {
        return (List<PlayBack>) dao.findForList("PlayBackMapper.getPlayRecord", username);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlayBack> getPlayRecordPM() throws Exception {
        return (List<PlayBack>) dao.findForList("PlayBackMapper.getPlayRecordPM", null);
    }

    @Override
    public int regPlaydx(PlayBack playBack) throws Exception {
        return (int) dao.save("PlayBackMapper.regPlaydx", playBack);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlayBack> getAllPlayRecord(String username) throws Exception {
        return (List<PlayBack>) dao.findForList("PlayBackMapper.getAllPlayRecord", username);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlayBack> getDollCount(String username) throws Exception {
        return (List<PlayBack>) dao.findForList("PlayBackMapper.getDollCount", username);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PlayBack> getPlayBackByUserName(String username) throws Exception {
        return (List<PlayBack>) dao.findForList("PlayBackMapper.getPlayBackByUserName", username);
    }

    @Override
    public int updatePlayBack(PlayBack playBack) throws Exception {
        return (int) dao.update("PlayBackMapper.updatePlayBack", playBack);
    }

    @Override
    public int updatePostState(PlayBack playBack) throws Exception {
        return (int) dao.update("PlayBackMapper.updatePostState", playBack);

    }

    @Override
    public int updatePlayBackSG(PlayBack playBack) throws Exception {
        return (int) dao.update("PlayBackMapper.updatePlayBackSG", playBack);
    }

    @Override
    public PlayBack getPlayBackById(int id) throws Exception {
        return (PlayBack) dao.findForObject("PlayBackMapper.getPlayBackById", id);
    }

    @Override
    public PlayBack getPlayBackLatest(String dollName) throws Exception {
        return (PlayBack) dao.findForObject("PlayBackMapper.getPlayBackLatest", dollName);
    }

    @Override
    public PlayBack getPlayBackSGById(int id) throws Exception {
        return (PlayBack) dao.findForObject("PlayBackMapper.getPlayBackSGById",id);
    }
}
