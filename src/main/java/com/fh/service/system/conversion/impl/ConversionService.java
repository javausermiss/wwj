package com.fh.service.system.conversion.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Conversion;
import com.fh.service.system.conversion.ConversionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("conversionService")
public class ConversionService implements ConversionManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public int reg(Conversion conversion) throws Exception {
        return (int) dao.save("ConversionMapper.reg", conversion);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Conversion> getList(Conversion conversion) throws Exception {
        return (List<Conversion>) dao.findForList("ConversionMapper.getList", conversion);
    }
}
