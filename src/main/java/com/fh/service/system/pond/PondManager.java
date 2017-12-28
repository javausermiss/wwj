package com.fh.service.system.pond;

import com.fh.entity.system.Pond;

import java.util.List;

public interface PondManager {
    /**
     * 创建奖池表
     * @param pond
     * @return
     * @throws Exception
     */
    public int regPond(Pond pond)throws Exception;

    /**
     * 修改奖池表压中
     * @param pond
     * @return
     * @throws Exception
     */
    public int updatePondY(Pond pond)throws Exception;

    /**
     * 修改奖池表压不中
     * @param pond
     * @return
     * @throws Exception
     */
    public int updatePondN(Pond pond)throws Exception;


    /**
     * 根据游戏记录ID获取对应奖池信息
     * @param pond
     * @return
     * @throws Exception
     */
    public Pond getPondByPlayId(Pond pond)throws Exception;


    /**
     * 根据idh获取奖池信息
     * @param id
     * @return
     * @throws Exception
     */
    public Pond getPondById(Integer id)throws Exception;

    /**
     * 修改结算标签
     * @param pond
     * @return
     * @throws Exception
     */
    public int updatePondFlag(Pond pond)throws Exception;

    /**
     * 查询最近的10场竞猜记录
     * @param dollId
     * @return
     * @throws Exception
     */
    public List<Pond> getGuessList (String dollId) throws Exception;


}
