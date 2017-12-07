package com.fh.service.system.betgame.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.GuessDetail;
import com.fh.service.system.betgame.BetGameManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("betGameService")
public class BetGameService implements BetGameManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    /**
     * 增加竞猜记录
     * @param guessDetail
     * @return
     * @throws Exception
     */
    @Override
    public int regGuessDetail(GuessDetail guessDetail) throws Exception {
        return (int)dao.save("GuessDetailMapper.regGuessDetail",guessDetail);
    }
    /**
     * 修改竞猜记录
     * @param guessDetail
     * @return
     * @throws Exception
     */
    @Override
    public int updateGuessDetail(GuessDetail guessDetail) throws Exception {
        return (int)dao.update("GuessDetailMapper.updateGuessDetail",guessDetail);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetail> getWin(Integer playID) throws Exception {
        return (List<GuessDetail>) dao.findForList("GuessDetailMapper.getWin",playID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetail> getFailer(GuessDetail guessDetail) throws Exception {
        return (List<GuessDetail>) dao.findForList("GuessDetailMapper.getFailer",guessDetail);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetail> getWinner(GuessDetail guessDetail) throws Exception {
        return (List<GuessDetail>) dao.findForList("GuessDetailMapper.getWinner",guessDetail);
    }
}
