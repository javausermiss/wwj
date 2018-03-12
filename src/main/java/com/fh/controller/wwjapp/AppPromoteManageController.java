package com.fh.controller.wwjapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.promotemanage.PromoteManageManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.wwjUtil.RespStatus;

import net.sf.json.JSONObject;
/**
 * 	查询渠道推广
 *
 */
@Controller
@RequestMapping("/app/promomote")
public class AppPromoteManageController extends BaseController {

	@Resource(name="promotemanageService")
	private PromoteManageManager promotemanageService;
	

	/**
	 * 查询渠道推广
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/getpromomoteManage", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getpromomoteManage(HttpServletRequest request) {
		logBefore(logger, Jurisdiction.getUsername()+"所有数据promote");
	
		try{
			List<PageData>	varList = promotemanageService.listAll();	//列出PromoteManage列表
			Map<String,Object> dataMap = new HashMap<String,Object>();
			dataMap.put("varList", varList);
			return RespStatus.successs().element("data", dataMap);
		}catch (Exception e) {
			logger.info(e.getMessage());
			return RespStatus.fail();
		}
	}
}
