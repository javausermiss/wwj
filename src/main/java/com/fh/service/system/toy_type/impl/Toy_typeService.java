package com.fh.service.system.toy_type.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Toy_type;
import com.fh.service.system.toy_type.Toy_typeManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("toy_typeService")
public class Toy_typeService implements Toy_typeManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public Toy_type getToy_Type(Toy_type toy_type) throws Exception {
        return (Toy_type)dao.findForObject("Toy_typeMapper.getToy_Type",toy_type);
    }

    @Override
    public int deleteToy_Type(Toy_type toy_type) throws Exception {
        return (int)dao.delete("Toy_typeMapper.deleteToy_Type",toy_type);
    }

    @Override
    public int regToy_Type(Toy_type toy_type) throws Exception {
        return (int)dao.save("Toy_typeMapper.regToy_Type",toy_type);
    }

    @Override
    public int deleteToy_TypeAll(Integer id) throws Exception {
        return (int)dao.save("Toy_typeMapper.deleteToy_TypeAll",id);
    }

    @Override
    public List<Toy_type> getToy_TypeList(Integer id) throws Exception {
        return (List<Toy_type>) dao.findForList("Toy_typeMapper.getToy_TypeList",id);
    }
}
