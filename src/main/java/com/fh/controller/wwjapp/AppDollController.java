package com.fh.controller.wwjapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.system.doll.DollManager;
import com.fh.util.NumberUtils;
import com.fh.util.PageData;
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

    /**
     * 所有娃娃机列表
     * @param req
     * @return
     */
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
    
    /**
     * 娃娃机分页表
     * @param req
     * @return
     */
    @RequestMapping(value = "/getDollPage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getDollPage(HttpServletRequest request) {
    	 try {
    		//获取当前页
    		Page page=new Page();
    		int currentPage= NumberUtils.parseInt(request.getParameter("nextPage"), 1);
    		page.setCurrentPage(currentPage); //当前页数
    		page.setShowCount(8*2);//
    		
    		//获取前端分类
    		PageData pd = new PageData();
    		pd = this.getPageData();
    		page.setPd(pd);
         	List<DollVo> dollList=dollService.getDollPage(page);
         	
         	
         	//分页标签的问题，情况分页Str
         	if(page !=null){
         		page.setPageStr("/");
         		page.setTotalResult(page.getTotalResult()/2);
         	}
         	
         	 Map<String, Object> map = new HashMap<>();
             map.put("dollList",dollList);
             
             //分页信息
             pd.put("currentPage", page.getCurrentPage());
             pd.put("showCount", (page.getShowCount()/2));
             pd.put("totalPage", (page.getTotalPage()));
             map.put("pd",pd); //搜索的顺序
            return RespStatus.successs().element("data", map);
            
         } catch (Exception e) {
         	logger.error(e.getLocalizedMessage());
             return RespStatus.exception();
         }
    }
}
