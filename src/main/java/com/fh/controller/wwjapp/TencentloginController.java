package com.fh.controller.wwjapp;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.AppUser;
import com.fh.entity.system.AppuserLogin;
import com.fh.entity.system.Doll;
import com.fh.entity.system.Payment;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.appuserlogininfo.AppuserLoginInfoManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.util.Const;
import com.fh.util.PropertiesUtils;
import com.fh.util.wwjUtil.FaceImageUtil;
import com.fh.util.wwjUtil.MyUUID;
import com.fh.util.wwjUtil.RedisUtil;
import com.fh.util.wwjUtil.RespStatus;
import com.fh.util.wwjUtil.TokenVerify;
import com.iot.game.pooh.admin.srs.core.entity.httpback.SrsConnectModel;
import com.iot.game.pooh.admin.srs.core.util.SrsConstants;
import com.iot.game.pooh.admin.srs.core.util.SrsSignUtil;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class TencentloginController extends BaseController {

    @Resource(name = "appuserService")
    private AppuserManager appuserService;

    @Resource(name = "dollService")
    private DollManager dollService;

    @Resource(name = "paymentService")
    private PaymentManager paymentService;

    @Resource(name="appuserlogininfoService")
    private AppuserLoginInfoManager appuserlogininfoService;

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
            if (appUser == null) {
                String code = TokenVerify.verify(token);
                if (code.equals("SUCCESS")) {
                    AppUser appUser1 = new AppUser();
                    if (imageUrl == null || imageUrl.equals("")) {
                        imageUrl = PropertiesUtils.getCurrProperty("user.default.header.url"); //默认头像
                    }
                    String newFace = FaceImageUtil.downloadImage(imageUrl);
                    appUser1.setNICKNAME(nickname);
                    appUser1.setIMAGE_URL(newFace);
                    appUser1.setUSER_ID(userId);
                    appuserService.regwx(appUser1);
                    //增加赠送金币明细
                    Payment payment = new Payment();
                    payment.setREMARK("注册赠送");
                    payment.setGOLD("+60");
                    payment.setCOST_TYPE("9");
                    payment.setUSERID(userId);
                    paymentService.reg(payment);
                    //登录日志
                    AppuserLogin appuserLogin = new AppuserLogin();
                    appuserLogin.setAPPUSERLOGININFO_ID(MyUUID.getUUID32());
                    appuserLogin.setUSER_ID(userId);
                    appuserlogininfoService.insertLoginLog(appuserLogin);
                    //SRS推流
                    SrsConnectModel sc = new SrsConnectModel();
                    long time = System.currentTimeMillis();
                    sc.setType("U");
                    sc.setTid(userId);
                    sc.setExpire(3600 * 24);
                    sc.setTime(time);
                    sc.setToken(SrsSignUtil.genSign(sc, SrsConstants.SRS_CONNECT_KEY));
                    String sessionID = MyUUID.createSessionId();
                    RedisUtil.getRu().set(Const.REDIS_APPUSER_SESSIONID  + userId, sessionID);
                    RedisUtil.getRu().set(Const.REDIS_APPUSER_LOGIN_TENCENTTOKEN  + userId, token);
                    
                    //用户登陆存储redis log
                    logger.info("用户 appUser is null tencentLogin 登陆accessToken sessionID:");
                    logger.info("redis " +Const.REDIS_APPUSER_LOGIN_TENCENTTOKEN + userId+"-->"+token);
                    logger.info("redis "+Const.REDIS_APPUSER_SESSIONID + userId+"-->"+sessionID);
                    
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("appUser", getAppUserInfo(userId));
                    map1.put("sessionID", sessionID);
                    map1.put("accessToken", token);
                    map1.put("srsToken", sc);
                    return RespStatus.successs().element("data", map1);
                } else {
                    return RespStatus.fail("token不合法");
                }
            } else {
                String code = TokenVerify.verify(token);
                if (code.equals("SUCCESS")) {
                    if (imageUrl == null || imageUrl.equals("")) {
                        imageUrl = PropertiesUtils.getCurrProperty("user.default.header.url"); //默认头像
                    }
                    String newFace = FaceImageUtil.downloadImage(imageUrl);
                    appUser.setNICKNAME(nickname);
                    appUser.setIMAGE_URL(newFace);
                    appuserService.updateTencentUser(appUser);
                    //登录日志
                    AppuserLogin appuserLogin = new AppuserLogin();
                    appuserLogin.setAPPUSERLOGININFO_ID(MyUUID.getUUID32());
                    appuserLogin.setUSER_ID(userId);
                    appuserlogininfoService.insertLoginLog(appuserLogin);
                    //SRS推流
                    SrsConnectModel sc = new SrsConnectModel();
                    long time = System.currentTimeMillis();
                    sc.setType("U");
                    sc.setTid(userId);
                    sc.setExpire(3600 * 24);
                    sc.setTime(time);
                    sc.setToken(SrsSignUtil.genSign(sc, SrsConstants.SRS_CONNECT_KEY));
                    String sessionID = MyUUID.createSessionId();
                    RedisUtil.getRu().set(Const.REDIS_APPUSER_SESSIONID  + userId, sessionID);
                    RedisUtil.getRu().set(Const.REDIS_APPUSER_LOGIN_TENCENTTOKEN  + userId, token);
                    
                    //用户登陆存储redis log 
                    logger.info("用户 appUser not null tencentLogin 登陆accessToken sessionID:");
                    logger.info("redis " +Const.REDIS_APPUSER_LOGIN_TENCENTTOKEN + userId+"-->"+token);
                    logger.info("redis "+Const.REDIS_APPUSER_SESSIONID + userId+"-->"+sessionID);
                    
                    Map<String, Object> map1 = new HashMap<>();
                    map1.put("appUser", getAppUserInfo(userId));
                    map1.put("sessionID", sessionID);
                    map1.put("accessToken", token);
                    map1.put("srsToken", sc);

                    return RespStatus.successs().element("data", map1);
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
     *
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
            if (a.equals("SUCCESS")) {
                if (appuserService.getUserByID(userId) == null) {
                    return RespStatus.fail("用户不存在");
                }
                //登录日志
                AppuserLogin appuserLogin = new AppuserLogin();
                appuserLogin.setAPPUSERLOGININFO_ID(MyUUID.getUUID32());
                appuserLogin.setUSER_ID(userId);
                appuserlogininfoService.insertLoginLog(appuserLogin);
                //SRS推流
                SrsConnectModel sc = new SrsConnectModel();
                long time = System.currentTimeMillis();
                sc.setType("U");
                sc.setTid(userId);
                sc.setExpire(3600 * 24);
                sc.setTime(time);
                sc.setToken(SrsSignUtil.genSign(sc, SrsConstants.SRS_CONNECT_KEY));
                String sessionID = MyUUID.createSessionId();
                RedisUtil.getRu().set(Const.REDIS_APPUSER_LOGIN_TENCENTTOKEN + userId, accessToken);
                RedisUtil.getRu().set(Const.REDIS_APPUSER_SESSIONID + userId, sessionID);
                
                
                //用户登陆存储redis log 
                logger.info("用户 tencentAutoLogin accessToken sessionID:");
                logger.info("redis" +Const.REDIS_APPUSER_LOGIN_TENCENTTOKEN + userId+"-->"+accessToken);
                logger.info("redis "+Const.REDIS_APPUSER_SESSIONID + userId+"-->"+sessionID);
                
                Map<String, Object> map1 = new HashMap<>();
                map1.put("appUser", getAppUserInfo(userId));
                map1.put("sessionID", sessionID);
                map1.put("accessToken", accessToken);
                map1.put("srsToken", sc);
                return RespStatus.successs().element("data", map1);
            } else {
                return RespStatus.fail("token 失效");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }
    }


}
