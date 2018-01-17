package com.fh.controller.wwjapp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.service.system.doll.DollManager;
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
            /* Random index = new Random();
             List<Integer> indexList = new ArrayList<>();
             List<DollVo> newList = new ArrayList<>();
             for(int i=0,j;i<10;i++){
                 j = index.nextInt(dollList.size());
                 //判断是否重复
                 if(!indexList.contains(j)){
                     //获取元素
                     indexList.add(j);
                     newList.add(dollList.get(j));
                 }else{
                     i--;//如果重复再来一次
                 }
             }
         	return RespStatus.successs().element("dollList", newList);*/
             return RespStatus.successs().element("dollList", dollList);
         } catch (Exception e) {
         	logger.error(e.getLocalizedMessage());
             return RespStatus.exception();
         }
    }
}
