package com.fh.controller.wwjapp;

import com.fh.entity.system.AppUser;
import com.fh.entity.system.Doll;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.doll.DollManager;
import com.fh.util.wwjUtil.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
     *
     * @param userId
     * @param token
     * @param imageUrl
     * @param nickname
     * @return
     */
    @RequestMapping(value = "/tencentLogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject tencentLogin(
            @RequestParam("uid") String userId,
            @RequestParam("accessToken") String token,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("nickName") String nickname
    ) {
        try {
            AppUser appUser = appuserService.getUserByID(userId);
            String accessToken = "";
            if (appUser == null) {
                String code = TokenVerify.verify(token);
                if (code.equals("SUCCESS")) {
                    AppUser appUser1 = new AppUser();
                    if (imageUrl.equals("") || imageUrl == null) {
                        imageUrl = "/default.png";
                    }

                    if (RedisUtil.getRu().exists("accessToken")) {
                        accessToken = RedisUtil.getRu().get("accessToken");
                    } else {
                        accessToken = CameraUtils.getAccessToken();
                    }
                    appUser1.setNICKNAME(nickname);
                    appUser1.setIMAGE_URL(imageUrl);
                    appUser1.setUSER_ID(userId);
                    appuserService.regwx(appUser1);
                    String sessionID = MyUUID.createSessionId();
                    List<Doll> doll = dollService.getAllDoll();
                    RedisUtil.getRu().set("sessionId:appUser:" + userId, sessionID);
                    RedisUtil.getRu().set("tencentToken:" + userId, token);
                    Map<String, Object> map = new HashMap<>();
                    map.put("appUser", getAppUserInfo(userId));
                    map.put("sessionID", sessionID);
                    map.put("dollList", doll);
                    map.put("accessToken", accessToken);
                    return RespStatus.successs().element("data", map);
                } else {
                    return RespStatus.fail("token不合法");
                }
            } else {
                String code = TokenVerify.verify(token);
                if (code.equals("SUCCESS")) {
                    AppUser appUser1 = new AppUser();
                    if (imageUrl.equals("") || imageUrl == null) {
                        imageUrl = "/default.png";
                    }
                    if (RedisUtil.getRu().exists("accessToken")) {
                        accessToken = RedisUtil.getRu().get("accessToken");
                    } else {
                        accessToken = CameraUtils.getAccessToken();
                    }
                    appUser1.setNICKNAME(nickname);
                    appUser1.setIMAGE_URL(imageUrl);
                    appuserService.updateTencentUser(appUser1);
                    String sessionID = MyUUID.createSessionId();
                    List<Doll> doll = dollService.getAllDoll();
                    RedisUtil.getRu().set("tencentToken:" + userId, token);
                    RedisUtil.getRu().set("sessionId:appUser:" + userId, sessionID);
                    Map<String, Object> map = new HashMap<>();
                    map.put("appUser", getAppUserInfo(userId));
                    map.put("sessionID", sessionID);
                    map.put("dollList", doll);
                    map.put("accessToken", accessToken);
                    return RespStatus.successs().element("data", map);
                } else {
                    return RespStatus.fail("token不合法");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }

    /**
     * 自动登录
     * @param userId
     * @param accessToken
     * @return
     */
    @RequestMapping(value = "/tencentAutoLogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject tencentAutoLogin(
            @RequestParam("userId") String userId,
            @RequestParam("accessToken") String accessToken
    ) {
        try {
            String a = TokenVerify.verify(accessToken.trim());//请求sdk后台效验token是否合法
            System.out.println("------------------------------------"+accessToken+"--------------------------------------");
            if (a.equals("SUCCESS")) {
                if (appuserService.getUserByID(userId)==null){
                    return RespStatus.fail("用户不存在");
                }
                String sessionID = MyUUID.createSessionId();
                List<Doll> doll = dollService.getAllDoll();
                RedisUtil.getRu().set("tencentToken:" + userId, accessToken);
                RedisUtil.getRu().set("sessionId:appUser:" + userId, sessionID);
                Map<String, Object> map = new HashMap<>();
                map.put("appUser", getAppUserInfo(userId));
                map.put("sessionID", sessionID);
                map.put("dollList", doll);
                map.put("accessToken", accessToken);
                return RespStatus.successs().element("data", map);
            }else {
                return RespStatus.fail("token 失效");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }
    }


}
