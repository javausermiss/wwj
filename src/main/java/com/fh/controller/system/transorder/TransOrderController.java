package com.fh.controller.system.transorder;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.trans.TransOrderManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/** 
 * 说明：分类
 * 创建人：FH Q313596790
 * 创建时间：2018-01-23
 */
@Controller
@RequestMapping(value="/trans/order")
public class TransOrderController extends BaseController {
	
	String menuUrl = "trans/order/list.do"; //菜单地址(权限用)
	
	
	@Resource(name="transOrderService")
	private TransOrderManager transOrderService;

	
	/** 提现申请，后台管理列表页面
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/queryWithdrawCashlist")
	public ModelAndView queryWithdrawCashlist(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表transorder");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = transOrderService.list(page);	//列出ToyType列表
		mv.setViewName("system/transorder/transorder_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}

}
