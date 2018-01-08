package com.fh.controller.system.guessdetail;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.fh.service.system.betgame.BetGameManager;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
/** 
 * 说明：玩家竞猜
 * 创建人：FH Q313596790
 * 创建时间：2018-01-03
 */
@Controller
@RequestMapping(value="/guessdetail")
public class GuessDetailController extends BaseController {
	
	String menuUrl = "guessdetail/list.do"; //菜单地址(权限用)
	@Resource(name="betGameService")
	private BetGameManager betGameService;
	

	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表GuessDetail");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = betGameService.list(page);	//列出GuessDetail列表
		mv.setViewName("system/guessdetail/guessdetail_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除GuessDetail");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			betGameService.deleteAll(ArrayDATA_IDS);
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
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出GuessDetail到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("主键ID");	//1
		titles.add("用户ID");	//2
		titles.add("娃娃机房间ID");	//3
		titles.add("用户实际抓取的结果");	//4
		titles.add("竞猜用户竞猜秘钥");	//5
		titles.add("竞猜金额");	//6
		titles.add("创建日期");	//7
		titles.add("场次ID");	//8
		titles.add("竞猜赢的金币");	//9
		titles.add("结算标签");	//10
		titles.add("结算时间");	//11
		dataMap.put("titles", titles);
		List<PageData> varOList = betGameService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("GUESS_ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("APP_USER_ID"));	    //2
			vpd.put("var3", varOList.get(i).getString("DOLL_ID"));	    //3
			vpd.put("var4", varOList.get(i).getString("GUESS_TYPE"));	    //4
			vpd.put("var5", varOList.get(i).getString("GUESS_KEY"));	    //5
			vpd.put("var6", varOList.get(i).getString("GUESS_GOLD"));	    //6
			vpd.put("var7", varOList.get(i).getString("CREATE_DATE"));	    //7
			vpd.put("var8", varOList.get(i).get("PLAYBACK_ID").toString());	//8
			vpd.put("var9", varOList.get(i).get("SETTLEMENT_GOLD").toString());	//9
			vpd.put("var10", varOList.get(i).getString("SETTLEMENT_FLAG"));	    //10
			vpd.put("var11", varOList.get(i).getString("SETTLEMENT_DATE"));	    //11
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
