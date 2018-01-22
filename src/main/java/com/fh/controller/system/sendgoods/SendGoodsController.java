package com.fh.controller.system.sendgoods;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.fh.entity.system.PlayDetail;
import com.fh.entity.system.SendGoods;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.util.*;
import com.fh.util.wwjUtil.RespStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.sendgoods.SendGoodsManager;

/** 
 * 说明：发货管理
 * 创建人：FH Q313596790
 * 创建时间：2018-01-10
 */
@Controller
@RequestMapping(value="/sendgoods")
@Slf4j
public class SendGoodsController extends BaseController {
	
	String menuUrl = "sendgoods/list.do"; //菜单地址(权限用)
	@Resource(name="sendGoodsService")
	private SendGoodsManager sendgoodsService;

	@Resource(name = "playDetailService")
	private PlayDetailManage playDetailService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增SendGoods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SENDGOODS_ID", this.get32UUID());	//主键
		sendgoodsService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除SendGoods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		sendgoodsService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(HttpServletRequest httpServletRequest) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改SendGoods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		String id = httpServletRequest.getParameter("ID");
		SendGoods sendGoods =  sendgoodsService.getSendById(id);
		String playId =  sendGoods.getTOY_NUM();
		PageData pd = new PageData();
		pd.put("FMS_NAME",httpServletRequest.getParameter("FMS_NAME"));
		pd.put("FMS_ORDER_NO",httpServletRequest.getParameter("FMS_ORDER_NO"));
		pd.put("SENDBOOLEAN",httpServletRequest.getParameter("SENDBOOLEAN"));
		pd.put("ID",httpServletRequest.getParameter("ID"));
		String create_time = sendGoods.getCREATE_TIME();
		String sid =  DateUtil.getNumOrder(id,create_time);
		playDetailService.doSendPost(pd,playId,sid);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表SendGoods");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = sendgoodsService.list(page);	//列出SendGoods列表
		mv.setViewName("system/sendgoods/sendgoods_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/sendgoods/sendgoods_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = sendgoodsService.findById(pd);	//根据ID读取
		mv.setViewName("system/sendgoods/sendgoods_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除SendGoods");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			sendgoodsService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出SendGoods到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastLoginStart = pd.getString("lastLoginStart");
		String lastLoginEnd = pd.getString("lastLoginEnd");
		if(lastLoginStart != null && !"".equals(lastLoginStart)){
			pd.put("lastLoginStart", lastLoginStart+" 00:00:00");
		}
		if(lastLoginEnd != null && !"".equals(lastLoginEnd)){
			pd.put("lastLoginEnd", lastLoginEnd+" 00:00:00");
		}
		page.setPd(pd);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("主键ID");	//1
		titles.add("用户ID");	//3
		titles.add("发货数量");	//4
		titles.add("收货人名字");	//5
		titles.add("收货人地址");	//6
		titles.add("收货人手机号码");	//7
		titles.add("订单创建时间");	//8
		titles.add("付款方式");	//9
		titles.add("是否发货");	//10
		titles.add("发货备注");	//11
		titles.add("备注");	//12
		titles.add("发货时间");	//13
		titles.add("物流单号");	//14
		titles.add("物流名称");	//15
		titles.add("更新时间");	//16
		dataMap.put("titles", titles);
		List<PageData> varOList = sendgoodsService.listAll(page);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("USER_ID"));	    //3
			vpd.put("var3", varOList.get(i).getString("GOODS_NUM"));	    //4
			vpd.put("var4", varOList.get(i).getString("CNEE_NAME"));	    //5
			vpd.put("var5", varOList.get(i).getString("CNEE_ADDRESS"));	    //6
			vpd.put("var6", varOList.get(i).getString("CNEE_PHONE"));	    //7
			vpd.put("var7", varOList.get(i).getString("CREATE_TIME"));	    //8
			vpd.put("var8", varOList.get(i).getString("MODE_DESPATCH"));	    //9
			vpd.put("var9", varOList.get(i).getString("SENDBOOLEAN"));	    //10
			vpd.put("var10", varOList.get(i).getString("POST_REMARK"));	    //11
			vpd.put("var11", varOList.get(i).getString("REMARK"));	    //12
			if (varOList.get(i).get("FMS_TIME")==null){
				vpd.put("var12", null);
			}else {
				vpd.put("var12", varOList.get(i).get("FMS_TIME").toString());	    //13
			}
			vpd.put("var13", varOList.get(i).getString("FMS_ORDER_NO"));	    //14
			vpd.put("var14", varOList.get(i).getString("FMS_NAME"));	    //15
			if (varOList.get(i).get("UPDATE_TIME")==null){
				vpd.put("var15",null);
			}else {
				vpd.put("var15", varOList.get(i).get("UPDATE_TIME").toString());	  //16
			}
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
