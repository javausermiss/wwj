package com.fh.service.system.doll.impl;

import java.util.List;
import javax.annotation.Resource;

import com.fh.vo.system.DollToyVo;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.system.doll.DollToyManager;
/** 
 * 说明： SYS_APP_DOLL_TOY
 * 创建人：FH Q313596790
 * 创建时间：2017-12-29
 * @version
 */
@Service("dolltoyService")
public class DollToyService implements DollToyManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DollToyMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DollToyMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DollToyMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DollToyMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DollToyMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DollToyMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DollToyMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public DollToyVo getDollToyByToyId(String toyId) throws Exception {
		return (DollToyVo)dao.findForObject("DollToyMapper.getDollToyByToyId",toyId);
	}

	@Override
	public DollToyVo getDollToyByToyName(String toyName) throws Exception {
		return (DollToyVo)dao.findForObject("DollToyMapper.getDollToyByToyName",toyName);
	}

	@Override
	public int updateToyNum(DollToyVo dollToyVo) throws Exception {
		return (int)dao.update("DollToyMapper.updateToyNum",dollToyVo);
	}

	@Override
	public int updateToyType(DollToyVo dollToyVo) throws Exception {
		return (int)dao.update("DollToyMapper.updateToyType",dollToyVo);
	}
}

