package com.fh.service.system.coinfactory.impl;

import java.util.List;
import javax.annotation.Resource;

import com.fh.entity.system.CoinFactory;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.system.coinfactory.CoinFactoryManager;

/** 
 * 说明： 推币机设备管理
 * 创建人：weijunyong
 * 创建时间：2018-05-10
 * @version
 */
@Service("coinfactoryService")
public class CoinFactoryService implements CoinFactoryManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void save(PageData pd)throws Exception{
		dao.save("CoinFactoryMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void delete(PageData pd)throws Exception{
		dao.delete("CoinFactoryMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void edit(PageData pd)throws Exception{
		dao.update("CoinFactoryMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CoinFactoryMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CoinFactoryMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CoinFactoryMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	@Override
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CoinFactoryMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public int insert(CoinFactory coinFactory)throws Exception {
		return (int)dao.save("CoinFactoryMapper.insert",coinFactory);
	}

	@Override
	public CoinFactory getCoinFactoryBySn(String sn)throws Exception {
		return (CoinFactory)dao.findForObject("CoinFactoryMapper.getCoinFactoryBySn",sn);
	}
}

