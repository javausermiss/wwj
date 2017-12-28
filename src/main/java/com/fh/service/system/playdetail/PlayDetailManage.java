package com.fh.service.system.playdetail;

import com.fh.entity.system.PlayDetail;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

public interface PlayDetailManage {
    /**
     * 新建场次
     * @param playDetail
     * @return
     * @throws Exception
     */
    public int reg(PlayDetail playDetail)throws Exception;

    /**
     *
     * @param playDetail
     * @return
     * @throws Exception
     */
    public PlayDetail getPlayDetailByGuessID (PlayDetail playDetail) throws Exception;

    /**
     * 查询最近的场次
     * @param playDetail
     * @return
     * @throws Exception
     */
    public PlayDetail getPlayDetailLast(PlayDetail playDetail)throws Exception;

    /**
     * 查询出最新的场次分发给围观群众
     * @param dollId
     * @return
     * @throws Exception
     */
    public PlayDetail getPlayIdForPeople(String dollId)throws Exception;


    /**
     * 通过ID查询场次信息
     * @param roomId
     * @return
     * @throws Exception
     */
    public PlayDetail getPlayDetailByRoomId(String roomId)throws Exception;

    /**
     * 更改标签
     * @param playDetail
     * @return
     * @throws Exception
     */
    public int  updatePlayDetailStopFlag(PlayDetail playDetail)throws Exception;

    /**
     * 更新抓取状态STATE
     * @param playDetail
     * @return
     * @throws Exception
     */
    public int updatePlayDetailState(PlayDetail playDetail)throws Exception;

    /**
     * 查询出相应的记录
     * @param playDetail
     * @return
     * @throws Exception
     */
    public PlayDetail getPlayDetailForCamera(PlayDetail playDetail)throws Exception;

    /**
     * 增加视频记录日期
     * @param playDetail
     * @return
     * @throws Exception
     */
    public int updatePlayDetailForCamera(PlayDetail playDetail)throws Exception;

    /**
     * 查询出用户的成功抓取记录
     * @param userId
     * @return
     * @throws Exception
     */
    public List<PlayDetail> getPlaylist(String userId) throws Exception;

    /**
     * 分组查询用户抓取每种娃娃的数量
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<PlayDetail> getDollCount(String userId) throws Exception;

    /**
     * 查询最新的10个人的抓取记录
     * @return
     * @throws Exception
     */
    public List<PlayDetail> getPlayRecordPM ()throws Exception;

    /**
     * 根基ID查询相应抓取记录
     * @param id
     * @return
     * @throws Exception
     */
    public PlayDetail getPlayDetailByID(Integer id)throws Exception;

    /**
     * 更新寄存状态 0寄存  1 寄出  2 兑换
     * @param playDetail
     * @return
     * @throws Exception
     */
    public int updatePostState(PlayDetail playDetail)throws Exception;

    /**
     * 更新兑换状态
     * @param playDetail
     * @return
     * @throws Exception
     */
    public int updatePostStateForCon (PlayDetail playDetail) throws Exception;



}
