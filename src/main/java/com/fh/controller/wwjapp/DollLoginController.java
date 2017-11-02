package com.fh.controller.wwjapp;

import com.fh.entity.system.Doll;
import com.fh.service.system.doll.DollManager;
import com.fh.util.wwjUtil.RespStatus;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 娃娃机接口登陆
 */
@Controller
@RequestMapping(value = "/doll")
public class DollLoginController {
    @Resource(name = "dollService")
    private DollManager dollService;

    @RequestMapping(value = "/login" ,method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject dollLogin(@RequestParam("sn") String dollSN, HttpServletRequest httpServletRequest) {
        try {
            Doll doll = dollService.getDollBySN(dollSN);//通过SN号查询娃娃机的信息
            if (doll != null) {
                String SessionID = httpServletRequest.getSession().getId();
                String RoomID = doll.getROOM_ID();
                return RespStatus.successs().element("SessionID", SessionID).element("RoomID", RoomID);
            }else {
                return RespStatus.fail("无此娃娃机！");
            }
        }catch(Exception e) {
            e.printStackTrace();
            return RespStatus.exception();
        }

    }




}
