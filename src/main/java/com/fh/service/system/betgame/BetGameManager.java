package com.fh.service.system.betgame;

import com.fh.entity.system.GuessDetailL;
import com.fh.util.PageData;

import java.util.List;

public interface BetGameManager {
    /**
     * 增加竞猜记录
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    public int regGuessDetail(GuessDetailL guessDetailL)throws Exception;

    /**
     * 修改竞猜记录
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    public int updateGuessDetail(GuessDetailL guessDetailL)throws Exception;

    /**
     * 查询获奖人数
     * @param playID
     * @return
     * @throws Exception
     */
    public List<GuessDetailL> getWin(Integer playID)throws Exception;

    /**
     * 查询未获奖人数
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    public List<GuessDetailL> getFailer(GuessDetailL guessDetailL)throws Exception;

    /**
     * 查询获奖人数
     * @param  guessDetailL
     * @return
     * @throws Exception
     */

    public List<GuessDetailL> getWinner(GuessDetailL guessDetailL)throws Exception;


    /**
     * 获取所有参与竞猜的人
     * @param guessid
     * @return
     * @throws Exception
     */
    public List<GuessDetailL> getAllGuesser(String guessid)throws Exception;

    /**
     * 更新竞猜结果
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    public int updateGuessDetailGuessType(GuessDetailL guessDetailL)throws Exception;

    /**
     * 通过playId userId 查询
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    public GuessDetailL getGuessDetail(GuessDetailL guessDetailL) throws Exception;
    
    /**
     * 通过 userId 查询 最新10条竞猜记录
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    public List<PageData> getGuessDetailTop10ByUserId(String userId) throws Exception;
    

}
