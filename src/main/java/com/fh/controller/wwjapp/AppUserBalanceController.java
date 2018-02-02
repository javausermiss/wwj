package com.fh.controller.wwjapp;

import com.fh.entity.system.*;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.ordertest.OrderTestManager;
import com.fh.service.system.paycard.PaycardManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.service.system.pond.PondManager;
import com.fh.util.PropertiesUtils;
import com.fh.util.wwjUtil.MyUUID;
import com.fh.util.wwjUtil.RedisUtil;
import com.fh.util.wwjUtil.RespStatus;
import com.fh.util.wwjUtil.TokenVerify;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/pay")
public class AppUserBalanceController {

    //  private final String ckey = "y3WfBKF1FY4=";
    @Resource(name = "appuserService")
    private AppuserManager appuserService;

    @Resource(name = "playBackService")
    private PlayBackManage playBackService;

    @Resource(name = "pondService")
    private PondManager pondService;

    @Resource(name = "playDetailService")
    private PlayDetailManage playDetailService;

    @Resource(name = "dollService")
    private DollManager dollService;

    @Resource(name = "paymentService")
    private PaymentManager paymentService;

    @Resource(name = "orderTestService")
    private OrderTestManager orderTestService;

    @Resource(name = "paycardService")
    private PaycardManager paycardService;


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
     * 个人信息
     *
     * @param userId
     * @return
     */

    public JSONObject getAppUserInfoById(String userId) {
        try {
            AppUser appUser = appuserService.getUserByID(userId);
            return JSONObject.fromObject(appUser);
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * 订单信息
     *
     * @param id
     * @return
     */

    public JSONObject getOrderInfo(String id) {
        try {
            Order o = orderTestService.getOrderById(id);
            return JSONObject.fromObject(o);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 获取动态充值卡信息
     *
     * @return
     */
    @RequestMapping(value = "/getPaycard", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getPaycard() {
        try {
            List<Paycard> list = paycardService.getPayCard();
            Map<String, Object> map = new HashMap<>();
            map.put("paycard", list);
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }

    /**
     * 获取订单信息
     *
     * @param userId
     * @param accessToken
     * @param amounr      金额
     * @return
     */
    @RequestMapping(value = "/getTradeOrder", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getTradeOrder(
            @RequestParam("userId") String userId,
            @RequestParam("accessToken") String accessToken,
            @RequestParam("amount") String amounr
    ) {
        try {

            AppUser appUser = appuserService.getUserByID(userId);
            if (appUser == null) {
                return null;
            }
            String datetime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            boolean a = RedisUtil.getRu().exists("tradeOrder");
            if (a) {
                String tradeOrder = RedisUtil.getRu().get("tradeOrder");
                String x = tradeOrder.substring(0, 8);//取前八位进行判断
                if (datetime.substring(0, 8).equals(x)) {
                    String six = tradeOrder.substring(tradeOrder.length() - 6, tradeOrder.length());
                    String newsix = String.format("%06d", (Integer.valueOf(six) + 1));
                    String newOrder = datetime + newsix;//新的订单编号
                    RedisUtil.getRu().set("tradeOrder", newOrder);
                    Order order = new Order();
                    order.setUSER_ID(userId);
                    order.setREC_ID(MyUUID.getUUID32());
                    order.setREGAMOUNT(amounr);
                    order.setORDER_ID(newOrder);
                    orderTestService.regmount(order);
                    Map<String, Object> map = new HashMap<>();
                    map.put("Order", getOrderInfo(order.getORDER_ID()));
                    return RespStatus.successs().element("data", map);
                } else {
                    String newOrder = datetime + "000001";//新的订单编号
                    RedisUtil.getRu().set("tradeOrder", newOrder);
                    Order order = new Order();
                    order.setUSER_ID(userId);
                    order.setREC_ID(MyUUID.getUUID32());
                    order.setREGAMOUNT(amounr);
                    order.setORDER_ID(newOrder);
                    orderTestService.regmount(order);
                    Map<String, Object> map = new HashMap<>();
                    map.put("Order", getOrderInfo(order.getORDER_ID()));
                    return RespStatus.successs().element("data", map);
                }
            } else {
                String newOrder = datetime + "000001";//新的订单编号
                RedisUtil.getRu().set("tradeOrder", newOrder);
                Order order = new Order();
                order.setUSER_ID(userId);
                order.setREC_ID(MyUUID.getUUID32());
                order.setREGAMOUNT(amounr);
                order.setORDER_ID(newOrder);
                orderTestService.regmount(order);
                Map<String, Object> map = new HashMap<>();
                map.put("Order", getOrderInfo(order.getORDER_ID()));
                return RespStatus.successs().element("data", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }

    /**
     * 支付回调接口
     *
     * @param chid         201609081254001(由CP自己分配相应渠道号；SDK只负责相应的渠道统计；并不做逻辑判断)
     * @param order_no     201609081254001(平台生成的订单号)
     * @param subject      test(订单主题，长度不要超过100位)
     * @param cid          ff8080814f81f904014f820178390004
     * @param amount       消费-总计金额
     * @param money        消费-支付金额
     * @param balance      消费-账户金额
     * @param vmoney       消费-活动券金额
     * @param time         1442475160430(时间戳)
     * @param user_id      2c90803b560b3c7a01560b3c7aa60000
     * @param out_trade_no 201609081254001(外部订单号, CP游戏的订单号)，长度不要超过32位；
     * @param trade_status 支付成功“SUCCESS”，支付失败“FAILURE”
     * @param payment_id   支付渠道,目前支持如下，后续扩展：
     *                     [Android]
     *                     alipay:支付宝,
     *                     bank:银联支付,
     *                     gf_wechat:微信,
     *                     huapay:易联支付
     *                     wallet:余额支付
     *                     [IOS]
     *                     applepay;苹果支付
     *                     wallet:余额支付
     * @param extra        透传参数，申请支付、消费时传入的参数，原样返回, 长度不要超过100位；
     * @param sign_type    固定值”MD5”
     * @param sign         参数签名，见签名方式
     * @return
     */

    @RequestMapping(value = "/orderCallBack", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String orderCallBack(
            @RequestParam("chid") String chid,
            @RequestParam("order_no") String order_no,
            @RequestParam("subject") String subject,
            @RequestParam("cid") String cid,
            @RequestParam("amount") int amount,
            @RequestParam("money") int money,
            @RequestParam("balance") int balance,
            @RequestParam("vmoney") int vmoney,
            @RequestParam("time") String time,
            @RequestParam("user_id") String user_id,
            @RequestParam("out_trade_no") String out_trade_no,
            @RequestParam("trade_status") String trade_status,
            @RequestParam("payment_id") String payment_id,
            @RequestParam("extra") String extra,
            @RequestParam("sign_type") String sign_type,
            @RequestParam("sign") String sign
    ) {
        try {
            Order o = orderTestService.getOrderById(out_trade_no);
            String ckey = PropertiesUtils.getCurrProperty("api.app.sdk.ckey");
            if (o == null) {
                return "there is no order";
            }
            if (trade_status.equals("FAILURE")) {
                o.setSTATUS("-1");
                orderTestService.update(o);
                return "SUCCESS";
            }
            o.setORDER_NO(order_no);
            String decodeStr = URLDecoder.decode(extra, "utf-8");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("chid", chid);
            map.put("order_no", order_no);
            map.put("subject", subject);
            map.put("cid", cid);
            map.put("amount", amount);
            map.put("money", money);
            map.put("balance", balance);
            map.put("vmoney", vmoney);
            map.put("time", time);
            map.put("user_id", user_id);
            map.put("out_trade_no", out_trade_no);
            map.put("trade_status", trade_status);
            map.put("payment_id", payment_id);
            map.put("extra", decodeStr);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if (entry.getValue() == null || entry.getValue().toString().length() == 0) {
                    map.put(key, "null");
                }
            }

            String tb_amount = o.getREGAMOUNT();
            if (!tb_amount.equals(String.valueOf(amount))) {
                return "充值金额不匹配";
            }
            Map<String, Object> sortedParams = new TreeMap<String, Object>(map);
            Set<Map.Entry<String, Object>> entrys = sortedParams.entrySet();
            // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
            StringBuilder basestring = new StringBuilder();
            for (Map.Entry<String, Object> param : entrys) {
                basestring.append(param.getKey()).append('=').append(param.getValue()).append('&');
            }
            basestring.delete(basestring.length() - 1, basestring.length()).append(ckey);
            String ss = TokenVerify.md5(basestring.toString());
            if (!ss.equals(sign)) {
                return "SIGN IS ERROR";
            }
            if (trade_status.equals("SUCCESS")) {
                if (o.getSTATUS().equals("1")) {
                    return "SUCCESS";
                }
                Paycard paycard = paycardService.getGold(String.valueOf(amount / 100));
                if (paycard == null) {
                    AppUser appUser = appuserService.getUserByID(o.getUSER_ID());
                    int reggold = Integer.valueOf(o.getREGAMOUNT()) / 10;
                    int a = Integer.valueOf(appUser.getBALANCE()) + reggold;
                    appUser.setBALANCE(String.valueOf(a));
                    appuserService.updateAppUserBalanceById(appUser);
                    Payment payment = new Payment();
                    payment.setGOLD(String.valueOf(reggold));
                    payment.setUSERID(o.getUSER_ID());
                    payment.setDOLLID(null);
                    payment.setCOST_TYPE("5");
                    payment.setREMARK("充值测试");
                    paymentService.reg(payment);
                    o.setORDER_NO(order_no);
                    o.setREGGOLD(String.valueOf(reggold));
                    o.setSTATUS("1");
                    orderTestService.update(o);
                    return "SUCCESS";
                }
                int gold = Integer.valueOf(paycard.getGOLD());
                String award = "";
                String rechare = "";
                switch (gold) {
                    case 65:
                        rechare = "60";
                        award = "5";
                        break;
                    case 335:
                        rechare = "300";
                        award = "35";
                        break;
                    case 800:
                        rechare = "680";
                        award = "120";
                        break;
                    case 1600:
                        rechare = "1280";
                        award = "320";
                        break;
                    case 4375:
                        rechare = "3280";
                        award = "1095";
                        break;
                    case 9260:
                        rechare = "6480";
                        award = "2780";
                        break;
                }
                AppUser appUser = appuserService.getUserByID(o.getUSER_ID());
                int a = Integer.valueOf(appUser.getBALANCE()) + gold;
                appUser.setBALANCE(String.valueOf(a));
                appuserService.updateAppUserBalanceById(appUser);
                //更新收支表
                Payment payment = new Payment();
                payment.setGOLD("+" + rechare);
                payment.setUSERID(o.getUSER_ID());
                payment.setDOLLID(null);
                payment.setCOST_TYPE("5");
                payment.setREMARK("充值" + rechare);
                paymentService.reg(payment);
                //奖励记录
                Payment payment1 = new Payment();
                payment1.setGOLD("+" + award);
                payment1.setUSERID(o.getUSER_ID());
                payment1.setDOLLID(null);
                payment1.setCOST_TYPE("9");
                payment1.setREMARK("奖励" + award);
                paymentService.reg(payment1);
                o.setREGGOLD(String.valueOf(gold));
                o.setORDER_NO(order_no);
                o.setSTATUS("1");
                orderTestService.update(o);
            } else {
                o.setSTATUS("-1");//支付失败
                orderTestService.update(o);
            }
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "SYSTEM ERROR";
        }
    }



    /**
     * ios h5 支付回调接口
     *
     * @param chid         201609081254001(由CP自己分配相应渠道号；SDK只负责相应的渠道统计；并不做逻辑判断)
     * @param order_no     201609081254001(平台生成的订单号)
     * @param subject      test(订单主题，长度不要超过100位)
     * @param cid          ff8080814f81f904014f820178390004
     * @param amount       消费-总计金额
     * @param money        消费-支付金额
     * @param balance      消费-账户金额
     * @param vmoney       消费-活动券金额
     * @param time         1442475160430(时间戳)
     * @param user_id      2c90803b560b3c7a01560b3c7aa60000
     * @param out_trade_no 201609081254001(外部订单号, CP游戏的订单号)，长度不要超过32位；
     * @param trade_status 支付成功“SUCCESS”，支付失败“FAILURE”
     * @param payment_id   支付渠道,目前支持如下，后续扩展：
     *                     [Android]
     *                     alipay:支付宝,
     *                     bank:银联支付,
     *                     gf_wechat:微信,
     *                     huapay:易联支付
     *                     wallet:余额支付
     *                     [IOS]
     *                     applepay;苹果支付
     *                     wallet:余额支付
     * @param extra        透传参数，申请支付、消费时传入的参数，原样返回, 长度不要超过100位；
     * @param sign_type    固定值”MD5”
     * @param sign         参数签名，见签名方式
     * @return
     */

    @RequestMapping(value = "/i5OrderCallBack", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String i5OrderCallBack(
            @RequestParam("chid") String chid,
            @RequestParam("order_no") String order_no,
            @RequestParam("subject") String subject,
            @RequestParam("cid") String cid,
            @RequestParam("amount") int amount,
            @RequestParam("money") int money,
            @RequestParam("balance") int balance,
            @RequestParam("vmoney") int vmoney,
            @RequestParam("time") String time,
            @RequestParam("user_id") String user_id,
            @RequestParam("out_trade_no") String out_trade_no,
            @RequestParam("trade_status") String trade_status,
            @RequestParam("payment_id") String payment_id,
            @RequestParam("extra") String extra,
            @RequestParam("sign_type") String sign_type,
            @RequestParam("sign") String sign
    ) {
        try {
            Order o = orderTestService.getOrderById(out_trade_no);
            String ckey = PropertiesUtils.getCurrProperty("api.i5.sdk.ckey");
            if (o == null) {
                return "there is no order";
            }
            if (trade_status.equals("FAILURE")) {
                o.setSTATUS("-1");
                orderTestService.update(o);
                return "SUCCESS";
            }
            o.setORDER_NO(order_no);
            String decodeStr = URLDecoder.decode(extra, "utf-8");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("chid", chid);
            map.put("order_no", order_no);
            map.put("subject", subject);
            map.put("cid", cid);
            map.put("amount", amount);
            map.put("money", money);
            map.put("balance", balance);
            map.put("vmoney", vmoney);
            map.put("time", time);
            map.put("user_id", user_id);
            map.put("out_trade_no", out_trade_no);
            map.put("trade_status", trade_status);
            map.put("payment_id", payment_id);
            map.put("extra", decodeStr);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                if (entry.getValue() == null || entry.getValue().toString().length() == 0) {
                    map.put(key, "null");
                }
            }

            String tb_amount = o.getREGAMOUNT();
            if (!tb_amount.equals(String.valueOf(amount))) {
                return "充值金额不匹配";
            }
            Map<String, Object> sortedParams = new TreeMap<String, Object>(map);
            Set<Map.Entry<String, Object>> entrys = sortedParams.entrySet();
            // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
            StringBuilder basestring = new StringBuilder();
            for (Map.Entry<String, Object> param : entrys) {
                basestring.append(param.getKey()).append('=').append(param.getValue()).append('&');
            }
            basestring.delete(basestring.length() - 1, basestring.length()).append(ckey);
            String ss = TokenVerify.md5(basestring.toString());
            if (!ss.equals(sign)) {
                return "SIGN IS ERROR";
            }
            if (trade_status.equals("SUCCESS")) {
                if (o.getSTATUS().equals("1")) {
                    return "SUCCESS";
                }
                Paycard paycard = paycardService.getGold(String.valueOf(amount / 100));
                if (paycard == null) {
                    AppUser appUser = appuserService.getUserByID(o.getUSER_ID());
                    int reggold = Integer.valueOf(o.getREGAMOUNT()) / 10;
                    int a = Integer.valueOf(appUser.getBALANCE()) + reggold;
                    appUser.setBALANCE(String.valueOf(a));
                    appuserService.updateAppUserBalanceById(appUser);
                    Payment payment = new Payment();
                    payment.setGOLD(String.valueOf(reggold));
                    payment.setUSERID(o.getUSER_ID());
                    payment.setDOLLID(null);
                    payment.setCOST_TYPE("5");
                    payment.setREMARK("充值测试");
                    paymentService.reg(payment);
                    o.setORDER_NO(order_no);
                    o.setREGGOLD(String.valueOf(reggold));
                    o.setSTATUS("1");
                    orderTestService.update(o);
                    return "SUCCESS";
                }
                int gold = Integer.valueOf(paycard.getGOLD());
                String award = "";
                String rechare = "";
                switch (gold) {
                    case 65:
                        rechare = "60";
                        award = "5";
                        break;
                    case 335:
                        rechare = "300";
                        award = "35";
                        break;
                    case 800:
                        rechare = "680";
                        award = "120";
                        break;
                    case 1600:
                        rechare = "1280";
                        award = "320";
                        break;
                    case 4375:
                        rechare = "3280";
                        award = "1095";
                        break;
                    case 9260:
                        rechare = "6480";
                        award = "2780";
                        break;
                }
                AppUser appUser = appuserService.getUserByID(o.getUSER_ID());
                int a = Integer.valueOf(appUser.getBALANCE()) + gold;
                appUser.setBALANCE(String.valueOf(a));
                appuserService.updateAppUserBalanceById(appUser);
                //更新收支表
                Payment payment = new Payment();
                payment.setGOLD("+" + rechare);
                payment.setUSERID(o.getUSER_ID());
                payment.setDOLLID(null);
                payment.setCOST_TYPE("5");
                payment.setREMARK("充值" + rechare);
                paymentService.reg(payment);
                //奖励记录
                Payment payment1 = new Payment();
                payment1.setGOLD("+" + award);
                payment1.setUSERID(o.getUSER_ID());
                payment1.setDOLLID(null);
                payment1.setCOST_TYPE("9");
                payment1.setREMARK("奖励" + award);
                paymentService.reg(payment1);
                o.setREGGOLD(String.valueOf(gold));
                o.setORDER_NO(order_no);
                o.setSTATUS("1");
                orderTestService.update(o);
            } else {
                o.setSTATUS("-1");//支付失败
                orderTestService.update(o);
            }
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "SYSTEM ERROR";
        }
    }


}
