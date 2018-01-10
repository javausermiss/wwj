package com.fh.service.system.sendgoods.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.SendGoods;
import com.fh.service.system.sendgoods.SendGoodsManager;
import com.fh.util.PageData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("sendGoodsService")
public class SendGoodsService implements SendGoodsManager{


    @Resource(name = "daoSupport")
    private DaoSupport dao;


    /**新增
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd)throws Exception{
        dao.save("SendGoodsMapper.save", pd);
    }

    /**删除
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd)throws Exception{
        dao.delete("SendGoodsMapper.delete", pd);
    }

    /**修改
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd)throws Exception{
        dao.update("SendGoodsMapper.edit", pd);
    }

    /**列表
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("SendGoodsMapper.datalistPage", page);
    }

    /**列表(全部)
     * @param pd
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("SendGoodsMapper.listAll", pd);
    }

    /**通过id获取数据
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("SendGoodsMapper.findById", pd);
    }

    /**批量删除
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("SendGoodsMapper.deleteAll", ArrayDATA_IDS);
    }

    @Override
    public int regSendGoods(SendGoods sendGoods) throws Exception {
        return (int)dao.save("SendGoodsMapper.regSendGoods",sendGoods);
    }


}
