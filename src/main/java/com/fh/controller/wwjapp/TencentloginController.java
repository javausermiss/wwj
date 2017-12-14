package com.fh.controller.wwjapp;

import com.fh.entity.system.AppUser;
import com.fh.entity.system.Doll;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.doll.DollManager;
import com.fh.util.wwjUtil.RespStatus;
import com.fh.util.wwjUtil.TokenVerify;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/app")
public class TencentloginController {

    @Resource(name = "appuserService")
    private AppuserManager appuserService;

    @Resource(name = "dollService")
    private DollManager dollService;

    /**
     * 个人信息
     *
     * @param id
     * @return
     */

    public JSONObject getAppUserInfo(String id) {
        try {
            AppUser appUser = appuserService.getUserByID(id);
            return JSONObject.fromObject(appUser);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 娃娃机信息
     */
    public JSONObject getDollInfo(String id) {
        try {
            Doll doll = dollService.getDollByID(id);
            return JSONObject.fromObject(doll);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 微信 QQ登录接口
     * @param userId
     * @param token
     * @param imageUrl
     * @param nickname
     * @return
     */
    @RequestMapping(value = "/tencentLogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject tencentLogin(
            @RequestParam("uid ") String userId,
            @RequestParam("token") String token,
            @RequestParam("imageUrl ") String imageUrl,
            @RequestParam("nickName") String nickname
    ){
        try {
            String code =  TokenVerify.verify(token);
            if (code.equals("200")){
                AppUser appUser =  new AppUser();
                appUser.setNICKNAME(nickname);
                appUser.setIMAGE_URL(imageUrl);
                appUser.setUSER_ID(userId);
                appuserService.regwx(appUser);
                Map<String,Object> map = new HashMap<>();
                map.put("data",getAppUserInfo(userId));
                return RespStatus.successs();
            }else {
                return RespStatus.fail("token不合法");
            }

        }catch (Exception e){
            e.printStackTrace();
            return RespStatus.fail();
        }


    }




}
