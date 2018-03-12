package com.fh.controller.wwjapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.promotemanage.PromoteManageManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
/**
 * 	查询渠道推广
 *
 */
@Controller
@RequestMapping("/app/promomote")
public class AppPromoteManageController extends BaseController {

	@Resource(name="promotemanageService")
	private PromoteManageManager promotemanageService;
	
	@RequestMapping("/getpromomoteManage")
	@ResponseBody
	public Map<String,Object> list(){
		logBefore(logger, Jurisdiction.getUsername()+"所有数据promote");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code",0);
		map.put("msg","success");
		try{
			List<PageData>	varList = promotemanageService.listAll();	//列出PromoteManage列表
			map.put("data", varList);
		}catch (Exception e) {
			e.printStackTrace();	
			map.put("code",-1);
			map.put("msg","fail");
		}
		return map;
	}
}
