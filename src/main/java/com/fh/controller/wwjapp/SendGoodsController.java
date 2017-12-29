package com.fh.controller.wwjapp;

import com.fh.entity.system.*;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.betgame.BetGameManager;
import com.fh.service.system.conversion.ConversionManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.service.system.playdetail.impl.PlayDetailService;
import com.fh.service.system.pond.PondManager;
import com.fh.service.system.sendgoods.SendGoodsManager;
import com.fh.util.wwjUtil.RespStatus;
import com.sun.jdi.IntegerValue;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.net.URL;
import java.util.*;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Slf4j
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
    @Resource(name = "playDetailService")
    private PlayDetailManage playDetailService;
    @Resource(name = "dollService")
    private DollManager dollService;
    @Resource(name = "paymentService")
    private PaymentManager paymentService;


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

    public JSONObject getPlayDetailInfo(Integer playId) {
        try {
            PlayDetail playBack = playDetailService.getPlayDetailByID(playId);
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
            @RequestParam("id") String playId,//抓取编号
            @RequestParam("number") String number,
            @RequestParam("consignee") String consignee,
            @RequestParam("remark") String remark,
            @RequestParam("userId") String userId,
            @RequestParam("mode") String mode
    ) {
        try {
            //增加发货记录
            SendGoods sendGoods = new SendGoods();
            //免邮
            if (mode.equals("0") && Integer.valueOf(number) >= 2) {
                sendGoods.setMODE_DESPATCH("0");
            } else {
                return RespStatus.fail("error!");
            }
            //货到付款
            if (mode.equals("2")) {
                sendGoods.setMODE_DESPATCH("2");
            }
            //金币抵扣
            if (mode.equals("1")) {
                //满2包邮 ，一个需要付运费
                if (Integer.valueOf(number) < 2) {
                    //判断用户是否有足够的余额支付邮寄费用
                    AppUser appUser = appuserService.getUserByID(userId);
                    String balance = appUser.getBALANCE();
                    if (Integer.valueOf(balance) < 80) {
                        return RespStatus.fail("余额不足，请充值");
                    }
                    int newbalance = Integer.valueOf(appUser.getBALANCE()) - 80;
                    appUser.setBALANCE(String.valueOf(newbalance));
                    appuserService.updateAppUserBalanceById(appUser);
                    Payment payment = new Payment();
                    payment.setGOLD("-80");
                    payment.setUSERID(userId);
                    payment.setDOLLID(null);
                    payment.setCOST_TYPE("6");
                    paymentService.reg(payment);
                    sendGoods.setMODE_DESPATCH("2");
                }
            }

            List<String> list = new LinkedList<>();
            List<PlayDetail> playDetails = new LinkedList<>();
            //获取需要发货的娃娃编号
            String[] pd = playId.split("\\,");
            for (int i = 0; i < pd.length; i++) {
                PlayDetail playDetail = playDetailService.getPlayDetailByID(Integer.parseInt(pd[i]));
                playDetails.add(playDetail);
                String dollId = playDetail.getDOLLID();
                list.add(dollId);
                if (playDetail.getPOST_STATE().equals("0")) {
                    playDetail.setPOST_STATE("1");
                    playDetail.setSENDGOODS(consignee + "," + remark);
                    playDetailService.updatePostState(playDetail);
                } else {
                    return RespStatus.fail("已经发货或者兑换");
                }
            }
            String[] s = consignee.split("\\,");
            String name = s[0];
            String phone = s[1];
            String address = s[2];
            sendGoods.setCNEE_NAME(name);
            sendGoods.setCNEE_ADDRESS(address);
            sendGoods.setCNEE_PHONE(phone);
            sendGoods.setPLAYBACK_ID(null);//抓取的娃娃编号
            sendGoods.setGOODS_NUM(number);
            sendGoods.setREMARK(remark);//留言
            sendGoods.setUSER_ID(userId);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                String d = list.get(i);
                sb.append(d + "，");
            }
            sendGoods.setPOST_REMARK(sb.toString());
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            sendGoods.setCREATE_TIME(time);
            int sg = sendGoodsService.regSendGoods(sendGoods);
            if (sg == 0) {
                return RespStatus.fail("增加记录失败");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("playBack", playDetails);
            return RespStatus.successs().element("data", map);

        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }
    }

    /**
     * 兑换娃娃币
     *
     * @param id
     * @param dollId
     * @param number
     * @param userId
     * @return
     */
    @RequestMapping(value = "/conversionGoods", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject conversionGoods(
            @RequestParam("id") String id,
            @RequestParam("dollId") String dollId,
            @RequestParam("number") String number,
            @RequestParam("userId") String userId
    ) {

        try {
            String[] pd = id.split("\\,");//获取需要兑换的抓中娃娃编号
            for (int i = 0; i < pd.length; i++) {
                String pid = pd[i];
                PlayDetail playBack = playDetailService.getPlayDetailByID(Integer.parseInt(pid));
                if (playBack.getPOST_STATE().equals("0")) {
                    AppUser appUser = appuserService.getUserByID(userId);
                    int balance = Integer.parseInt(appUser.getBALANCE());
                    String c =  playBack.getCONVERSIONGOLD();
                    int m =  Integer.valueOf(c);
                    String newBalance = String.valueOf(balance + m);
                    appUser.setBALANCE(newBalance);
                    playBack.setPOST_STATE("2");//兑换
                    playDetailService.updatePostStateForCon(playBack);
                } else {
                    return RespStatus.fail("该娃娃已经被兑换过或者寄出");
                }
                Conversion conversion = new Conversion();
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = format.format(date);
                conversion.setCREATETIME(time);
                conversion.setDOLLNAME(dollService.getDollByID(playDetailService.getPlayDetailByID(Integer.valueOf(pid)).getDOLLID()).getDOLL_NAME());
                conversion.setNUMBER("1");
                conversion.setUSERID(userId);
                conversion.setPLAYID(pid);
                conversion.setCONMONEY(String.valueOf(playBack.getCONVERSIONGOLD()));
                conversionService.reg(conversion);
                Payment payment = new Payment();
                payment.setGOLD("+"+String.valueOf(playBack.getCONVERSIONGOLD()));
                payment.setUSERID(userId);
                payment.setDOLLID(playBack.getDOLLID());
                payment.setCOST_TYPE("7");
                paymentService.reg(payment);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("appUser", getAppUserInfo(userId));
            return RespStatus.successs().element("data", map);

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

    public static void main(String[] ad) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String dateString = formatter.format(currentTime);
        long l = Long.parseLong(dateString);
        System.out.println(dateString);


    }


}
