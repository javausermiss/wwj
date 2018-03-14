package com.fh.controller.wwjapp;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.entity.system.BankCard;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.bankcard.BankCardManager;
import com.fh.util.Const;
import com.fh.util.wwjUtil.RedisUtil;
import com.fh.util.wwjUtil.RespStatus;

import net.sf.json.JSONObject;

/**
 * 短信接口类
 *
 * @author wjy
 */
@Controller
@RequestMapping("/app/common")
public class AppCommonController {

	@Resource(name="appuserService")
	private AppuserManager appuserService;
	
    /**
     * 获取短信验证码
     *
     * @param aPhone
     * @return
     */
    @RequestMapping(value = "/getPhoneCode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject doSendSmsByType(
    		HttpServletRequest request,
    		@RequestParam(value="userId" ,required = true) String userId,
    		@RequestParam(value="smsType" ,required = true) String smsType
    		) {
    	    	
        try {
        	


//            RedisUtil.getRu().setex("SMSCode:" + phone, code, 300);
            return RespStatus.successs();
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.exception();
        }
    }

}



