package com.fh.service.system.playdetail;

import com.fh.entity.system.PlayDetail;

public interface PlayDetailManage {
    /**
     * 新建场次
     * @param playDetail
     * @return
     * @throws Exception
     */
    public int reg(PlayDetail playDetail)throws Exception;

    /**
     * 根据场次ID查询相关信息
     * @param guessId
     * @return
     * @throws Exception
     */
    public PlayDetail getPlayDetailByGuessID (String guessId) throws Exception;

    /**
     * 查询最近的场次
     * @param playDetail
     * @return
     * @throws Exception
     */
    public PlayDetail getPlayDetailLast(PlayDetail playDetail)throws Exception;

    /**
     * 查询出最新的场次分发给围观群众
     * @param dollname
     * @return
     * @throws Exception
     */
    public PlayDetail getPlayIdForPeople(String dollname)throws Exception;


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


}
