package com.fh.controller.wwjapp;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.Doll;
import com.fh.service.system.doll.DollManager;
import com.fh.util.wwjUtil.DollRegUtil;
import com.fh.util.wwjUtil.MyUUID;
import com.fh.util.wwjUtil.RedisUtil;
import com.fh.util.wwjUtil.RespStatus;
import com.fh.vo.system.DollVo;

import net.sf.json.JSONObject;

/**
 * APP DOLL ctrl
 * 
 * @author JAVA_DEV
 *
 */
@Controller
@RequestMapping(value = "/app/doll")
public class AppDollController extends BaseController{
	
    @Resource(name = "dollService")
    private DollManager dollService;

	
    @RequestMapping(value = "/getDollList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getDollLists(HttpServletRequest req) {
    	 try {
         	List<DollVo> dollList=dollService.getDollVoList();
         	return RespStatus.successs().element("dollList", dollList);
         } catch (Exception e) {
         	logger.error(e.getLocalizedMessage());
             return RespStatus.exception();
         }
    }
}
