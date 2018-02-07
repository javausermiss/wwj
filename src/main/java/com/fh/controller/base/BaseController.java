package com.fh.controller.base;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.fh.entity.Page;
import com.fh.util.Logger;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;

/**
 * @author FH Q313596790
 *         修改时间：2015、12、11
 */
public class BaseController {

    protected Logger logger = Logger.getLogger(this.getClass());

    private static final long serialVersionUID = 6357869213649815390L;

    /**
     * new PageData对象
     *
     * @return
     */
    public PageData getPageData() {
        return new PageData(this.getRequest());
    }

    /**
     * 得到ModelAndView
     *
     * @return
     */
    public ModelAndView getModelAndView() {
        return new ModelAndView();
    }

    /**
     * 得到request对象
     *
     * @return
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * 得到32位的uuid
     *
     * @return
     */
    public String get32UUID() {
        return UuidUtil.get32UUID();
    }

    /**
     * 得到分页列表的信息
     *
     * @return
     */
    public Page getPage() {
        return new Page();
    }

    public static void logBefore(Logger logger, String interfaceName) {
        logger.info("");
        logger.info("start");
        logger.info(interfaceName);
    }

    public static void logAfter(Logger logger) {
        logger.info("end");
        logger.info("");
    }
    
    /**
     * 获取请求参数封装
     * @param request
     * @return
     */
    public  PageData getBaseParams(HttpServletRequest request) {
    	
    	PageData pd=new PageData();
    	String userId=request.getParameter("userId"); //用户ID
    	String ctype=request.getParameter("ctype"); //SDK
    	String channel=request.getParameter("channel");//渠道
    	String deviceType=request.getParameter("deviceType"); //SDK
    	String osVersion=request.getParameter("osVersion");//渠道
    	String appVersion=request.getParameter("appVersion"); //SDK
    	String sfId=request.getParameter("sfId");//渠道
    	
    	pd.put("userId", userId);
    	pd.put("ctype", ctype);
    	pd.put("channel", channel);
    	pd.put("deviceType", deviceType);
    	pd.put("osVersion", osVersion);
    	pd.put("appVersion", appVersion);
    	pd.put("sfId", sfId);
    	
    	return pd;
    }

}
