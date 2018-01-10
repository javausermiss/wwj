package com.fh.service.system.doll;

import java.util.List;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.vo.system.DollToyVo;

/** 
 * 说明： SYS_APP_DOLL_TOY接口
 * 创建人：FH Q313596790
 * 创建时间：2017-12-29
 * @version
 */
public interface DollToyManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;

	/**
	 *通过ID查询玩具信息
	 * @param toyId
	 * @return
	 * @throws Exception
	 */
	public DollToyVo getDollToyByToyId(String toyId)throws Exception;

	/**
	 * 根据娃娃名称获取相关娃娃信息
	 * @param toyName
	 * @return
	 * @throws Exception
	 */
	public DollToyVo getDollToyByToyName(String toyName)throws Exception;

	/**
	 * 更新娃娃数量
	 * @param dollToyVo
	 * @return
	 * @throws Exception
	 */
	public int updateToyNum(DollToyVo dollToyVo)throws Exception;
	
}

