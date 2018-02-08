package com.fh.service.system.pond.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Pond;
import com.fh.service.system.pond.PondManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("pondService")
public class PondService implements PondManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public int regPond(Pond pond) throws Exception {
        return (int)dao.save("PondMapper.regPond",pond);
    }

    @Override
    public int updatePondY(Pond pond) throws Exception {
        return (int)dao.update("PondMapper.updatePondY",pond);
    }

    @Override
    public int updatePondN(Pond pond) throws Exception {
        return (int)dao.update("PondMapper.updatePondN",pond);
    }

    @Override
    public Pond getPondByPlayId(Pond pond) throws Exception {
        return (Pond) dao.findForObject("PondMapper.getPondByPlayId",pond);
    }

    @Override
    public Pond getPondById(Integer id) throws Exception {
        return (Pond) dao.findForObject("PondMapper.getPondById",id);
    }

    @Override
    public int updatePondFlag(Pond pond) throws Exception {
        return (int)dao.update("PondMapper.updatePondFlag",pond);
    }

    @Override
    public List<Pond> getGuessList(String dollId) throws Exception {
        return (List<Pond>)dao.findForList("PondMapper.getGuessList",dollId);
    }

    @Override
    public int updatePondGuesser(Pond pond) throws Exception {
        return (int)dao.update("PondMapper.updatePondGuesser",pond);
    }

    @Override
    public List<Pond> getGuesserlast10() throws Exception {
        return (List<Pond>)dao.findForList("PondMapper.getGuesserlast10",null);
    }
}
