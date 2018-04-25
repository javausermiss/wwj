package com.fh.controller.wwjapp;

import com.fh.entity.system.Doll;
import com.fh.service.system.doll.DollManager;
import com.fh.util.wwjUtil.RespStatus;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/app/scancode")
public class ScanCodeController {

    @Resource(name = "dollService")
    private DollManager dollService;

    /**
     * 扫码接口，提供对应房间金币
     * @return
     */
    @RequestMapping(value = "/getDollGold", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getDollGold(HttpServletRequest httpServletRequest) {
        try {
            String roomId = httpServletRequest.getParameter("roomId");
            Doll doll =  dollService.getDollByID(roomId);
            if (doll==null){
                return RespStatus.fail("无此房间");
            }
            int gold =  doll.getDOLL_GOLD();
            Map<String, Object> map = new HashMap<>();
            map.put("dollGold",String.valueOf(gold));
            return RespStatus.successs().element("data",map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }
    }


}
