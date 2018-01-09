package com.fh.service.system.paycard.impl;


import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Paycard;
import com.fh.service.system.paycard.PaycardManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("paycardService")
public class PaycardService implements PaycardManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**新增
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd)throws Exception{
        dao.save("PaycardMapper.save", pd);
    }

    /**删除
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd)throws Exception{
        dao.delete("PaycardMapper.delete", pd);
    }

    /**修改
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd)throws Exception{
        dao.update("PaycardMapper.edit", pd);
    }

    /**列表
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("PaycardMapper.datalistPage", page);
    }

    /**列表(全部)
     * @param pd
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("PaycardMapper.listAll", pd);
    }

    /**通过id获取数据
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("PaycardMapper.findById", pd);
    }

    /**批量删除
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("PaycardMapper.deleteAll", ArrayDATA_IDS);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Paycard> getPayCard() throws Exception {
        return (List<Paycard>)dao.findForList("PaycardMapper.getPayCard",null);
    }

    @Override
    public Paycard getGold(String amount) throws Exception {
        return (Paycard)dao.findForObject("PaycardMapper.getGold",amount);
    }

    @Override
    public Paycard getPayCardById(String id) throws Exception {
        return (Paycard)dao.findForObject("PaycardMapper.getPayCardById",id);
    }
}
