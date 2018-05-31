package com.fh.controller.wwjapp;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.fh.entity.system.Payment;
import com.sun.istack.internal.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.AppUser;
import com.fh.entity.system.AppuserLogin;
import com.fh.entity.system.Doll;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.appuserlogininfo.AppuserLoginInfoManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.util.Const;
import com.fh.util.FastDFSClient;
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

    @Resource(name = "appuserlogininfoService")
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
     * @param nickname
     * @return
     */
    @RequestMapping(value = "/tencentLogin", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject tencentLogin(
            HttpServletRequest req,
            @RequestParam("uid") String userId,
            @RequestParam("accessToken") String token,
            @RequestParam("nickName") String nickname) {
        try {
            String ctype = req.getParameter("ctype"); //SDK
            String channel = req.getParameter("channel");//渠道
            String imageUrl = req.getParameter("imageUrl");//头像

            logger.info("tencentLogin--> userId=" + userId + ",accessToken=" + token + ",imageUrl=" + imageUrl + ",nickName" + nickname + ",ctype-->" + ctype + ",channel-->" + channel);
            //验证token 是否合法
            String code = "fail";
            if (Const.SDKMenuType.YSDK.getValue().equals(ctype) || (ctype == null || "".equals(ctype))) {
                code = TokenVerify.verify(token); //应用宝SDK验证
                logger.info("应用宝SDK验证code---------->"+code);
            } else if (Const.SDKMenuType.W8SDK.getValue().equals(ctype)) {

                //token 验证
                SortedMap<String, String> paramsMap = new TreeMap<String, String>();
                paramsMap.put("uid", userId);
                paramsMap.put("nickName", nickname);
                paramsMap.put("imageUrl", "");
                paramsMap.put("ctype", ctype);
                paramsMap.put("channel", channel);
                String sign = TokenVerify.verifyForW8sdk(paramsMap); //w8SDK
                if (sign.equals(token)) {
                    code = "SUCCESS";
                }
            }else if (Const.SDKMenuType.SSDK.getValue().equals(ctype)){
                code = TokenVerify.verifyForScan(token);//二维码验证

            }else {
                code = TokenVerify.verifyForALL(token); //官方验证
            }

            if (!"SUCCESS".equals(code)) {
                return RespStatus.fail("token不合法");
            }

            //判断用户是否存在
            AppUser appUser = appuserService.getUserByID(userId);
            String newFace = "";
            if (appUser == null) {
                appUser = new AppUser();
                if (imageUrl == null || imageUrl.equals("")) {
                    newFace = PropertiesUtils.getCurrProperty("user.default.header.url"); //默认头像
                } else {
                    if (newFace==null){
                        newFace = PropertiesUtils.getCurrProperty("user.default.header.url");
                    }
                    newFace = FaceImageUtil.downloadImage(imageUrl);

                }
                //扫码用户注册赠送10金币
                if (Const.SDKMenuType.SSDK.getValue().equals(ctype)){
                    appUser.setNICKNAME(nickname);
                    appUser.setIMAGE_URL(newFace);
                    appUser.setUSER_ID(userId);
                    appUser.setBALANCE("10");
                    appuserService.regwx(appUser);
                    Payment payment = new Payment();
                    payment.setREMARK(Const.PlayMentCostType.cost_type16.getName());
                    payment.setGOLD("+10");
                    payment.setCOST_TYPE(Const.PlayMentCostType.cost_type16.getValue());
                    payment.setUSERID(userId);
                    paymentService.reg(payment);
                }else {
                   //手机端注册用户赠送3金币
                    appUser.setNICKNAME(nickname);
                    appUser.setIMAGE_URL(newFace);
                    appUser.setUSER_ID(userId);
                    appUser.setBALANCE("3");
                    appuserService.regwx(appUser);

                    logger.info("tencentLogin--> userId=" + userId + ",首次登陆，注册赠送金币...");
                    //增加赠送金币明细
                    Payment payment = new Payment();
                    payment.setREMARK(Const.PlayMentCostType.cost_type14.getName());
                    payment.setGOLD("+3");
                    payment.setCOST_TYPE(Const.PlayMentCostType.cost_type14.getValue());
                    payment.setUSERID(userId);
                    paymentService.reg(payment);
                }
            } else {
                if (imageUrl == null || imageUrl.equals("")) {
                    newFace = PropertiesUtils.getCurrProperty("user.default.header.url"); //默认头像
                } else {
                    newFace = FaceImageUtil.downloadImage(imageUrl);
                }

                //如果当前用户图像不是默认头像，则先删除，再上传
                if (newFace != null && appUser.getIMAGE_URL() != null) {
                    String defaultUrl = PropertiesUtils.getCurrProperty("user.default.header.url"); //获取默认头像Id
                    if (!defaultUrl.equals(appUser.getIMAGE_URL())) {
                        FastDFSClient.deleteFile(appUser.getIMAGE_URL());
                    }
                }

                appUser.setNICKNAME(nickname);
                appUser.setIMAGE_URL(newFace);
                appuserService.updateTencentUser(appUser); //已注册用户 更新用户昵称和头像
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
            RedisUtil.getRu().set(Const.REDIS_APPUSER_SESSIONID + userId, sessionID);
            RedisUtil.getRu().set(Const.REDIS_APPUSER_LOGIN_TENCENTTOKEN + userId, token);

            //用户登陆存储redis log
            logger.info("用户 appUser is null tencentLogin 登陆accessToken sessionID:");
            logger.info("redis " + Const.REDIS_APPUSER_LOGIN_TENCENTTOKEN + userId + "-->" + token);
            logger.info("redis " + Const.REDIS_APPUSER_SESSIONID + userId + "-->" + sessionID);

            Map<String, Object> map = new HashMap<>();


            map.put("appUser", getAppUserInfo(userId));//重新查询用户信息
            map.put("sessionID", sessionID);
            map.put("accessToken", token);
            map.put("srsToken", sc);
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return RespStatus.fail("程序异常");
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
            HttpServletRequest req,
            @RequestParam("userId") String userId,
            @RequestParam("accessToken") String accessToken
    ) {
        String ctype = req.getParameter("ctype"); //SDK
        String channel = req.getParameter("channel");//渠道

        logger.info("tencentAutoLogin--> userId=" + userId + ",accessToken=" + accessToken + ",ctype-->" + ctype + ",channel-->" + channel);
        try {
            String code = "";
            if (Const.SDKMenuType.YSDK.getValue().equals(ctype) || (ctype == null || "".equals(ctype))) {
                code = TokenVerify.verify(accessToken); //应用宝SDK验证
                logger.info("应用宝SDK验证code---------->"+code);
            } else if (Const.SDKMenuType.W8SDK.getValue().equals(ctype)) {
                //token 验证
                SortedMap<String, String> paramsMap = new TreeMap<String, String>();
                paramsMap.put("userId", userId);
                paramsMap.put("ctype", ctype);
                paramsMap.put("channel", channel);
                String sign = TokenVerify.verifyForW8sdk(paramsMap); //w8SDK

                if (sign.equals(accessToken)) {
                    code = "SUCCESS";
                }
            } else {
                code = TokenVerify.verifyForALL(accessToken); //官方验证
            }
            if (code.equals("SUCCESS")) {
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
                logger.info("redis" + Const.REDIS_APPUSER_LOGIN_TENCENTTOKEN + userId + "-->" + accessToken);
                logger.info("redis " + Const.REDIS_APPUSER_SESSIONID + userId + "-->" + sessionID);

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
