package com.fh.controller.wwjapp;

import com.fh.entity.system.*;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.betgame.BetGameManager;
import com.fh.service.system.conversion.ConversionManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.pond.PondManager;
import com.fh.service.system.sendgoods.SendGoodsManager;
import com.fh.util.wwjUtil.RespStatus;
import net.sf.json.JSONObject;

import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@RequestMapping("/app")
@Controller
public class SendGoodsController {
    @Resource(name = "appuserService")
    private AppuserManager appuserService;
    @Resource(name = "playBackService")
    private PlayBackManage playBackService;
    @Resource(name = "sendGoodsService")
    private SendGoodsManager sendGoodsService;
    @Resource(name = "conversionService")
    private ConversionManager conversionService;
    @Resource(name = "betGameService")
    private BetGameManager betGameService;


    public JSONObject getSendGoodsInfo(String playId) {
        try {
            SendGoods sendGoods = sendGoodsService.getSendGoodsByPlayId(Integer.parseInt(playId));
            return JSONObject.fromObject(sendGoods);
        } catch (Exception e) {
            return null;
        }

    }


    public JSONObject getPlayBackInfo(String playId) {
        try {
            PlayBack playBack = playBackService.getPlayBackSGById(Integer.parseInt(playId));
            return JSONObject.fromObject(playBack);
        } catch (Exception e) {
            return null;
        }

    }

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
     * 更改收货人信息接口
     *
     * @return
     */
    @RequestMapping(value = "/cnsignee", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject cnsignee(
            @RequestParam("name") String name,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("userId") String id

    ) {
        try {
            AppUser appUser = appuserService.getUserByID(id);
            appUser.setCNEE_NAME(name);
            appUser.setCNEE_ADDRESS(address);
            appUser.setCNEE_PHONE(phone);
            int n = appuserService.updateAppUserCnee(appUser);
            if (n == 0) {
                return RespStatus.fail("更新失败");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("appUser", getAppUserInfo(id));
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }


    }


    /**
     * 发货接口
     *
     * @param playId
     * @param number
     * @param consignee
     * @param remark
     * @param userId
     * @return
     */
    @RequestMapping(value = "/sendGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject sendGoods(
            @RequestParam("id") String playId,
            @RequestParam("number") String number,
            @RequestParam("consignee") String consignee,
            @RequestParam("remark") String remark,
            @RequestParam("userId") String userId
    ) {
        try {
            PlayBack playBack = playBackService.getPlayBackById(Integer.parseInt(playId));
            if (playBack.getPOSTSTATE().equals("0")) {
                SendGoods sendGoods = new SendGoods();
                String[] s = consignee.split("\\,");
                String name = s[0];
                String phone = s[1];
                String address = s[2];
                sendGoods.setCNEE_NAME(name);
                sendGoods.setCNEE_ADDRESS(address);
                sendGoods.setCNEE_PHONE(phone);
                sendGoods.setPLAYBACK_ID(Integer.parseInt(playId));
                sendGoods.setGOODS_NUM(number);
                sendGoods.setREMARK(remark);
                sendGoods.setUSER_ID(userId);
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                sendGoods.setCREATE_TIME(time);
                int sg = sendGoodsService.regSendGoods(sendGoods);
                if (sg == 0) {
                    return RespStatus.fail("增加记录失败");
                }
                playBack.setPOSTSTATE("1");
                playBack.setSENDGOODS(consignee + "," + remark);
                int p = playBackService.updatePostState(playBack);
                if (p == 0) {
                    return RespStatus.fail("更新寄存状态失败");
                }
                Map<String, Object> map = new HashMap<>();
                map.put("playBack", getPlayBackInfo(playId));
                return RespStatus.successs().element("data", map);
            } else {
                return RespStatus.fail("该娃娃已被兑换或者发货");
            }


        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }
    }

    /**
     * 兑换娃娃币
     *
     * @param id
     * @param dollName
     * @param number
     * @param userId
     * @return
     */
    @RequestMapping(value = "/conversionGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject conversionGoods(
            @RequestParam("id") String id,
            @RequestParam("dollName") String dollName,
            @RequestParam("number") String number,
            @RequestParam("userId") String userId
    ) {

        try {
            PlayBack playBack = playBackService.getPlayBackById(Integer.parseInt(id));
            if (playBack.getPOSTSTATE().equals("0")) {
                AppUser appUser = appuserService.getUserByID(userId);
                int balance = Integer.parseInt(appUser.getBALANCE());
                int money = playBack.getCONVERSIONGOLD() * Integer.parseInt(number);
                String newBalance = String.valueOf(balance + money);
                playBack.setPOSTSTATE("2");
                appUser.setBALANCE(newBalance);
                appuserService.updateAppUserBalanceById(appUser);
                playBackService.updatePostState(playBack);
                Conversion conversion = new Conversion();
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                conversion.setCREATETIME(time);
                conversion.setDOLLNAME(dollName);
                conversion.setNUMBER(number);
                conversion.setUSERID(userId);
                conversion.setPLAYID(id);
                conversion.setCONMONEY(String.valueOf(money));
                conversionService.reg(conversion);
                Map<String, Object> map = new HashMap<>();
                map.put("appUser", getAppUserInfo(userId));
                return RespStatus.successs().element("data", map);
            } else {
                return RespStatus.fail("该娃娃已经被兑换过或者寄出");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }

    /**
     * 获取兑换列表
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getConList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getConList(
            @RequestParam("userId") String userId
    ) {
        try {
            List<Conversion> conversionList = conversionService.getList(new Conversion(userId));
            Map<String, Object> map = new HashMap<>();
            map.put("conversionList", conversionList);
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }
    }

    public static void main(String[] ad){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(currentTime);
        long l = Long.parseLong(dateString);
        System.out.println(1/10);






    }


}
