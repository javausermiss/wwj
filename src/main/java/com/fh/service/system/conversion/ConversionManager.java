package com.fh.service.system.conversion;

import com.fh.entity.system.Conversion;

import java.util.List;

public interface ConversionManager {
    /**
     * 增加兑换记录
     * @param conversion
     * @return
     * @throws Exception
     */
    public int reg(Conversion conversion) throws Exception;

    /**
     * 查询玩家兑换记录
     * @param conversion
     * @return
     * @throws Exception
     */
    public List<Conversion> getList(Conversion conversion) throws Exception;

}
