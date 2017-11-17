package com.fh.controller.wwjapp;

import com.fh.entity.system.AppUser;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.wwjUtil.Base64Image;
import com.fh.util.wwjUtil.Base64Util;
import com.fh.util.wwjUtil.RedisUtil;
import com.fh.util.wwjUtil.RespStatus;
import net.sf.json.JSONObject;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api")
public class AppUserController {

    @Resource(name = "appuserService")
    private AppuserManager appuserService;

    /**
     * 个人信息
     *
     * @param phone
     * @return
     */

    public JSONObject getAppUserInfo(String phone) {
        try {
            AppUser appUser = appuserService.getUserByPhone(phone);
            return JSONObject.fromObject(appUser);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 获取用户信息
     *
     * @param aPhone
     * @return
     */

    @RequestMapping(value = "/getAppUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getAppUser(@RequestParam("phone") String aPhone) {
        try {
            String phone = new String(Base64Util.decryptBASE64(aPhone));
            AppUser appUser = appuserService.getUserByPhone(phone);
            if (appUser != null) {
                appuserService.updateAppUserImage(appUser);
                return RespStatus.successs().element("appUser", getAppUserInfo(phone));
            } else {
                return RespStatus.fail("无此用户！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }

    /**
     * 更改用户头像信息
     *
     * @param aPhone
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject updateUser(@RequestParam("phone") String aPhone,
                                 @RequestParam("base64Image") String base64Image) {
        try {
            String phone = new String(Base64Util.decryptBASE64(aPhone));
            AppUser appUser = appuserService.getUserByPhone(phone);

            if (appUser != null) {
                if (!Base64Image.GenerateImage(base64Image, "/usr/local/tomcat/webapps/faceImage" + phone + ".png")) {
                    return RespStatus.fail("上传失败");
                }
                appUser.setIMAGE_URL("/"+phone+".png");
                appuserService.updateAppUserImage(appUser);
                return RespStatus.successs().element("appUser", getAppUserInfo(phone));

            } else {
                return RespStatus.fail("无此用户！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }


    }


}
