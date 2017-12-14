package com.fh.service.system.playback;


import com.fh.entity.system.PlayBack;

import java.util.List;

/**
 *
 */
public interface PlayBackManage {

    public int reg(PlayBack playBack)throws Exception;

    public int regPlayBack(String dollname, String username, String time, int gold, String state) throws Exception;

    public int regPlayBackBalance(PlayBack playBack) throws Exception;

    public int regPlaydx(PlayBack playBack) throws Exception;

    public List<PlayBack> getPlayRecord(String username) throws Exception;

    public List<PlayBack> getPlayRecordPM() throws Exception;

    public List<PlayBack> getAllPlayRecord(String username) throws Exception;

    public List<PlayBack> getPlayBackByUserName(String username) throws Exception;

    public int updatePlayBack(PlayBack playBack) throws Exception;

    public int updatePostState(PlayBack playBack) throws Exception;

    public int updatePlayBackSG(PlayBack playBack) throws Exception;

    public PlayBack getPlayBackById(int id) throws Exception;

    public PlayBack getPlayBackLatest(String dollName) throws Exception;

    public List<PlayBack> getDollCount(String username) throws Exception;

    public PlayBack getPlayBackSGById(int id)throws Exception;

}
