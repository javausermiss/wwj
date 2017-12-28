package com.fh.controller.system.camera;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
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
import com.iot.game.pooh.admin.srs.core.entity.httpback.SrsConnectModel;
import com.iot.game.pooh.admin.srs.core.util.SrsConstants;
import com.iot.game.pooh.admin.srs.core.util.SrsSignUtil;

import antlr.Token;

import com.fh.util.Jurisdiction;
import com.fh.service.system.camera.CameraManager;
import com.fh.service.system.doll.DollManager;

/** 
 * 说明：摄像头处理类
 * 创建人：FH Q313596790
 * 创建时间：2017-12-25
 */
@Controller
@RequestMapping(value="/camera")
public class CameraController extends BaseController {
	
	String menuUrl = "camera/list.do"; //菜单地址(权限用)
	@Resource(name="cameraService")
	private CameraManager cameraService;
	
	@Resource(name="dollService")
	private DollManager dollService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Camera");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CAMERA_ID", this.get32UUID());	//主键
		pd.put("DEVICE_STATE", "2");
		cameraService.save(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"删除Camera");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		cameraService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Camera");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		cameraService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表Camera");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = cameraService.list(page);	//列出Camera列表
		
		mv.setViewName("system/camera/camera_list");
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
		mv.setViewName("system/camera/camera_edit");
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
		pd = cameraService.findById(pd);	//根据ID读取
		mv.setViewName("system/camera/camera_edit");
		StringBuffer sbf=CameraToken();
		mv.addObject("sbf",sbf);
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
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Camera");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			cameraService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, Jurisdiction.getUsername()+"导出Camera到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("娃娃机主键ID");	//1
		titles.add("设备名称");	//2
		titles.add("设备编号");	//3
		titles.add("推流地址");	//4
		titles.add("推流服务名称");	//5
		titles.add("流媒体名称");	//6
		titles.add("流媒体进程ID");	//7
		titles.add("创建时间");	//8
		titles.add("最后修改时间");	//9
		titles.add("乐观锁版本号");	//10
		dataMap.put("titles", titles);
		List<PageData> varOList = cameraService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("DOLL_ID"));	    //1
			vpd.put("var2", varOList.get(i).getString("CAMERA_NAME"));	    //2
			vpd.put("var3", varOList.get(i).getString("CAMERA_NUM"));	    //3
			vpd.put("var4", varOList.get(i).getString("RTMP_URL"));	    //4
			vpd.put("var5", varOList.get(i).getString("SERVER_NAME"));	    //5
			vpd.put("var6", varOList.get(i).getString("LIVESTREAM"));	    //6
			vpd.put("var7", varOList.get(i).getString("SERVER_ID"));	    //7
			vpd.put("var8", varOList.get(i).getString("CREATE_DATE"));	    //8
			vpd.put("var9", varOList.get(i).getString("UPDATE_DATE"));	    //9
			vpd.put("var10", varOList.get(i).get("LOCK_VERSION").toString());	//10
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
	
	public StringBuffer  CameraToken()throws Exception
	{
		SrsConnectModel sc=new SrsConnectModel();
		long time=System.currentTimeMillis();
		sc.setType("C");
		sc.setTid("num-0005-M");
		sc.setExpire(0);
		sc.setTime(0);
		sc.setToken(SrsSignUtil.genSign(sc,SrsConstants.SRS_CONNECT_KEY));
		StringBuffer sbf=new StringBuffer
				("?expire=0&time=0&type=C&tid=").
				append("num-0005-M").append("&token=").append(sc.getToken());
		
		return sbf;
	}

}	
