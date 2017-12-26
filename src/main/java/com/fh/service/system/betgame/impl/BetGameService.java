package com.fh.service.system.betgame.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.GuessDetailL;
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
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    @Override
    public int regGuessDetail(GuessDetailL guessDetailL) throws Exception {
        return (int)dao.save("GuessDetailMapper.regGuessDetail", guessDetailL);
    }
    /**
     * 修改竞猜记录
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    @Override
    public int updateGuessDetail(GuessDetailL guessDetailL) throws Exception {
        return (int)dao.update("GuessDetailMapper.updateGuessDetail", guessDetailL);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetailL> getWin(Integer playID) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getWin",playID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetailL> getFailer(GuessDetailL guessDetailL) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getFailer", guessDetailL);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetailL> getWinner(GuessDetailL guessDetailL) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getWinner", guessDetailL);
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetailL> getAllGuesser(String guessid) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getAllGuesser",guessid);
    }

    @Override
    public int updateGuessDetailGuessType(GuessDetailL guessDetailL) throws Exception {
        return (int)dao.update("GuessDetailMapper.updateGuessDetailGuessType", guessDetailL);
    }

    @Override
    public GuessDetailL getGuessDetail(GuessDetailL guessDetailL) throws Exception {
        return (GuessDetailL) dao.findForObject("GuessDetailMapper.getGuessDetail", guessDetailL);
    }
}
