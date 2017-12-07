package com.fh.service.system.betgame;

import com.fh.entity.system.GuessDetail;

import java.util.List;

public interface BetGameManager {
    /**
     * 增加竞猜记录
     * @param guessDetail
     * @return
     * @throws Exception
     */
    public int regGuessDetail(GuessDetail guessDetail)throws Exception;

    /**
     * 修改竞猜记录
     * @param guessDetail
     * @return
     * @throws Exception
     */
    public int updateGuessDetail(GuessDetail guessDetail)throws Exception;

    /**
     * 查询获奖人数
     * @param playID
     * @return
     * @throws Exception
     */
    public List<GuessDetail> getWin(Integer playID)throws Exception;

    /**
     * 查询未获奖人数
     * @param guessDetail
     * @return
     * @throws Exception
     */
    public List<GuessDetail> getFailer(GuessDetail guessDetail)throws Exception;

    /**
     * 查询获奖人数
     * @param  guessDetail
     * @return
     * @throws Exception
     */

    public List<GuessDetail> getWinner(GuessDetail guessDetail)throws Exception;
}
