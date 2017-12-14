package com.fh.service.system.pond;

import com.fh.entity.system.Pond;

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
     * @param guessId
     * @return
     * @throws Exception
     */
    public Pond getPondByPlayId(String guessId)throws Exception;


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
}
