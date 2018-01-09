package com.fh.controller.system.paycard;

import java.io.File;
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

import com.fh.entity.system.Paycard;
import com.fh.util.*;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.paycard.PaycardManager;

/** 
 * 说明：充值图片管理
 * 创建人：FH Q313596790
 * 创建时间：2018-01-09
 */
@Controller
@RequestMapping(value="/paycard")
public class PaycardController extends BaseController {
	
	String menuUrl = "paycard/list.do"; //菜单地址(权限用)
	@Resource(name="paycardService")
	private PaycardManager paycardService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(
			HttpServletRequest req,
			@RequestParam(value = "PAYCARD_FILE", required = false)CommonsMultipartFile multipartFile
	) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Paycard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		//文件上传
		String fileId="";
		try{
			String newFilename=multipartFile.getOriginalFilename();
			DiskFileItem fi = (DiskFileItem) multipartFile.getFileItem();
			File file = fi.getStoreLocation();
			fileId = FastDFSClient.uploadFile(file, newFilename);
			logger.info("---------fileId-------------"+fileId);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		pd.put("IMAGEURL",fileId);
		pd.put("AMOUNT",req.getParameter("AMOUNT"));
		pd.put("GOLD",req.getParameter("GOLD"));
		pd.put("DISCOUNT",req.getParameter("DISCOUNT"));
		paycardService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Paycard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		paycardService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(
			HttpServletRequest req,
			@RequestParam(value = "PAYCARD_FILE", required = false)CommonsMultipartFile multipartFile
	) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Paycard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		Paycard paycard =  paycardService.getPayCardById(req.getParameter("ID"));
		//上传的文件
		String newFilename=multipartFile.getOriginalFilename();
		DiskFileItem fi = (DiskFileItem) multipartFile.getFileItem();
		File file = fi.getStoreLocation();
		//文件上传，编辑操作
		String fileId="";
		if (paycard !=null && paycard.getIMAGEURL()==null){
			try{
				fileId = FastDFSClient.uploadFile(file, newFilename);
				logger.info("---------fileId-------------"+fileId);
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}else{
			//判断当前文件是否为空
			if(file !=null && !multipartFile.isEmpty() && multipartFile.getSize() >0){
				fileId = FastDFSClient.modifyFile(paycard.getIMAGEURL(), file, newFilename);
			}else{
				//
				fileId=paycard.getIMAGEURL();
			}
		}
		pd.put("ID",req.getParameter("ID"));
		pd.put("IMAGEURL",fileId);
		pd.put("AMOUNT",req.getParameter("AMOUNT"));
		pd.put("GOLD",req.getParameter("GOLD"));
		pd.put("DISCOUNT",req.getParameter("DISCOUNT"));
		paycardService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Paycard");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = paycardService.list(page);	//列出Paycard列表
		mv.setViewName("system/paycard/paycard_list");
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
		mv.setViewName("system/paycard/paycard_edit");
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
		pd = paycardService.findById(pd);	//根据ID读取
		mv.setViewName("system/paycard/paycard_edit");
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Paycard");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			paycardService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Paycard到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("主键");	//1
		titles.add("AMOUNT");	//2
		titles.add("GOLD");	//3
		titles.add("DISCOUNT");	//4
		titles.add("IMAGEURL");	//5
		dataMap.put("titles", titles);
		List<PageData> varOList = paycardService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("ID").toString());	//1
			vpd.put("var2", varOList.get(i).getString("AMOUNT"));	    //2
			vpd.put("var3", varOList.get(i).getString("GOLD"));	    //3
			vpd.put("var4", varOList.get(i).getString("DISCOUNT"));	    //4
			vpd.put("var5", varOList.get(i).getString("IMAGEURL"));	    //5
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
