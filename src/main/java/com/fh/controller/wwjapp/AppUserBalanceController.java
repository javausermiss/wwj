package com.fh.controller.wwjapp;

import com.fh.entity.system.*;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.ordertest.OrderTestManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.service.system.pond.PondManager;
import com.fh.util.wwjUtil.MyUUID;
import com.fh.util.wwjUtil.RedisUtil;
import com.fh.util.wwjUtil.RespStatus;
import com.fh.util.wwjUtil.TokenVerify;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
    private final String ckey = "rcWhucD6efT=";

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
     * 游戏记录信息
     *
     * @param dollname
     * @return
     */
    public JSONObject getPlayDetailInfo(String dollname) {
        try {
            PlayDetail p = playDetailService.getPlayIdForPeople(dollname);
            return JSONObject.fromObject(p);
        } catch (Exception e) {
            return null;
        }
    }

    public JSONObject getPondInfo(String guessID) {
        try {
            Pond p = pondService.getPondByPlayId(guessID);
            return JSONObject.fromObject(p);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 游戏记录信息
     *
     * @param id
     * @return
     */

    public JSONObject getPlayBackInfo(int id) {
        try {
            PlayBack playBack = playBackService.getPlayBackById(id);
            return JSONObject.fromObject(playBack);
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
            Ordertest o = orderTestService.getOrderById(id);
            return JSONObject.fromObject(o);
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * 获取订单信息
     *
     * @param userId
     * @param accessToken
     * @param amounr
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
            String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            boolean a = RedisUtil.getRu().exists("tradeOrder");
            if (a) {
                String tradeOrder = RedisUtil.getRu().get("tradeOrder");
                String x = tradeOrder.substring(0, 8);//取前八位进行判断
                if (datetime.substring(0, 8).equals(x)) {
                    String six = tradeOrder.substring(tradeOrder.length() - 6, tradeOrder.length());
                    String newsix = String.format("%06d", (Integer.valueOf(six) + 1));
                    String newOrder = datetime + newsix;//新的订单编号
                    RedisUtil.getRu().set("tradeOrder", newOrder);
                    Ordertest ordertest = new Ordertest();
                    ordertest.setUSER_ID(userId);
                    ordertest.setREC_ID(MyUUID.getUUID32());
                    ordertest.setREGAMOUNT(amounr);
                    ordertest.setORDER_ID(newOrder);
                    orderTestService.regmount(ordertest);
                    Map<String, Object> map = new HashMap<>();
                    map.put("Order", getOrderInfo(ordertest.getORDER_ID()));
                    return RespStatus.successs().element("data", map);
                } else {
                    String newOrder = datetime + "000001";//新的订单编号
                    RedisUtil.getRu().set("tradeOrder", newOrder);
                    Ordertest ordertest = new Ordertest();
                    ordertest.setUSER_ID(userId);
                    ordertest.setREC_ID(MyUUID.getUUID32());
                    ordertest.setREGAMOUNT(amounr);
                    ordertest.setORDER_ID(newOrder);
                    orderTestService.regmount(ordertest);
                    Map<String, Object> map = new HashMap<>();
                    map.put("Order", getOrderInfo(ordertest.getORDER_ID()));
                    return RespStatus.successs().element("data", map);
                }
            } else {
                String newOrder = datetime + "000001";//新的订单编号
                RedisUtil.getRu().set("tradeOrder", newOrder);
                Ordertest ordertest = new Ordertest();
                ordertest.setUSER_ID(userId);
                ordertest.setREC_ID(MyUUID.getUUID32());
                ordertest.setREGAMOUNT(amounr);
                ordertest.setORDER_ID(newOrder);
                orderTestService.regmount(ordertest);
                Map<String, Object> map = new HashMap<>();
                map.put("Order", getOrderInfo(ordertest.getORDER_ID()));
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
            if (trade_status.equals("FAILURE")){
                return "SUCCESS";
            }
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
            Ordertest o = orderTestService.getOrderById(out_trade_no);
            if (o == null) {
                return "无此订单";
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
                o.setSTATUS("1");
                orderTestService.update(o);
                AppUser appUser = appuserService.getUserByID(o.getUSER_ID());
                int a = Integer.valueOf(appUser.getBALANCE()) + Integer.valueOf(o.getREGAMOUNT()) / 10;
                appUser.setBALANCE(String.valueOf(a));
                appuserService.updateAppUserBalanceById(appUser);
            }
            return "SUCCESS";
        } catch (Exception e) {
            e.printStackTrace();
            return "SYSTEM ERROR";
        }
    }

    /**
     * 充值充值接口
     *
     * @param userId
     * @param money
     * @return
     */
    @RequestMapping(value = "/balance", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject appUserPay(@RequestParam("userId") String userId, @RequestParam("money") String money) {
        try {
            AppUser appUser = appuserService.getUserByID(userId);
            if (appUser != null) {
                String balance = appUser.getBALANCE();
                int bal = Integer.parseInt(balance);
                int mon = Integer.parseInt(money);
                appUser.setBALANCE(String.valueOf(bal + mon));
                int n = appuserService.updateAppUserBalanceByPhone(appUser);
                if (n != 0) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("appUser", getAppUserInfoById(userId));
                    return RespStatus.successs().element("data", map);
                } else {
                    return RespStatus.fail("更新余额失败");
                }
            } else {
                return RespStatus.fail("此用户不存在");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }

    /**
     * 用户点击开始按钮创建游戏记录表和奖池表
     *
     * @param userId
     * @param dollId
     * @return
     */
    @RequestMapping(value = "/creatPlayList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody

    public JSONObject creatPlayList(@RequestParam("userId") String userId,
                                    @RequestParam("dollId") String dollId) {
        try {
            AppUser appUser = appuserService.getUserByID(userId);
            String b1 = appUser.getBALANCE();
            int b2 = dollService.getDollByID(dollId).getDOLL_GOLD();
            if (Integer.valueOf(b1) < b2) {
                return RespStatus.fail("余额不足");
            }
            PlayDetail playDetail = new PlayDetail();
            playDetail.setDOLLNAME(dollService.getDollByID(dollId).getDOLL_NAME());
            playDetail.setNICKNAME(appuserService.getUserByID(userId).getNICKNAME());
            PlayDetail p = playDetailService.getPlayDetailLast(playDetail);//获取最新的场次
            if (p == null) {
                Date currentTime1 = new Date();
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
                String dateString1 = formatter1.format(currentTime1);
                String num = "0001";
                String guessId = dateString1 + num;
                PlayDetail newPlayDetail = new PlayDetail();
                newPlayDetail.setGUESS_ID(guessId);
                newPlayDetail.setNICKNAME(appuserService.getUserByID(userId).getNICKNAME());
                newPlayDetail.setDOLLNAME(dollService.getDollByID(dollId).getDOLL_NAME());
                newPlayDetail.setGOLD(String.valueOf(dollService.getDollByID(dollId).getDOLL_GOLD()));
                newPlayDetail.setSTOP_FLAG("1");//允许竞猜
                int n = playDetailService.reg(newPlayDetail);
                if (n == 0) {
                    return RespStatus.fail("增加场次失败");
                }
                Pond pond = new Pond(newPlayDetail.getGUESS_ID(), null);
                pondService.regPond(pond);
            } else {
                String guessid = p.getGUESS_ID();//获取到场次ID 201712100001
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                String dateString = formatter.format(currentTime);
                String x = guessid.substring(0, 8);//取前八位进行判断
                if (x.equals(dateString)) {
                    String newGuessId = String.valueOf(Long.parseLong(guessid) + 1);
                    PlayDetail newp = new PlayDetail();
                    newp.setGUESS_ID(newGuessId);
                    newp.setDOLLNAME(dollService.getDollByID(dollId).getDOLL_NAME());
                    newp.setNICKNAME(appuserService.getUserByID(userId).getNICKNAME());
                    newp.setGOLD(String.valueOf(dollService.getDollByID(dollId).getDOLL_GOLD()));
                    newp.setSTOP_FLAG("1");
                    int c = playDetailService.reg(newp);
                    if (c == 0) {
                        return RespStatus.fail("当天增加游戏记录失败");
                    }
                    Pond pond = new Pond(newp.getGUESS_ID(), null);
                    pondService.regPond(pond);
                } else {
                    Date current = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    String date = format.format(current);
                    String newGuessID = date + "0001";
                    PlayDetail playDetail1 = new PlayDetail();
                    playDetail1.setDOLLNAME(dollService.getDollByID(dollId).getDOLL_NAME());
                    playDetail1.setNICKNAME(appuserService.getUserByID(userId).getNICKNAME());
                    playDetail1.setGUESS_ID(newGuessID);
                    playDetail1.setGOLD(String.valueOf(dollService.getDollByID(dollId).getDOLL_GOLD()));
                    playDetail1.setSTOP_FLAG("1");
                    int c1 = playDetailService.reg(playDetail1);
                    if (c1 == 0) {
                        return RespStatus.fail("隔天增加游戏记录失败");
                    }
                    Pond pond = new Pond(playDetail1.getGUESS_ID(), null);
                    pondService.regPond(pond);
                }
            }
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("playDetail", getPlayDetailInfo(dollService.getDollByID(dollId).getDOLL_NAME()));
            map.put("pond", getPondInfo(playDetailService.getPlayDetailLast(playDetail).getGUESS_ID()));
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }


    /**
     * 消费金币接口 2017/12/13
     *
     * @param userId
     * @param cost
     * @return
     */
    @RequestMapping(value = "/costBalance", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject costBalance(@RequestParam("userId") String userId,
                                  @RequestParam("gold") String cost,
                                  @RequestParam("dollId") String dollId
    ) {
        try {
            //String phone = new String(Base64Util.decryptBASE64(aPhone));
            //AppUser appUser = appuserService.getUserByPhone(phone);
            AppUser appUser = appuserService.getUserByID(userId);
            if (appUser != null) {
                String balance = appUser.getBALANCE();
                int a = Integer.parseInt(balance);
                int b = Integer.parseInt(cost);
                if (a < b) {
                    return RespStatus.fail("余额不足");
                } else {
                    Payment payment = new Payment();
                    payment.setCOST_TYPE("0");
                    payment.setDOLLID(dollId);
                    payment.setUSERID(userId);
                    payment.setGOLD(cost);
                    paymentService.reg(payment);
                    appUser.setBALANCE(String.valueOf(a - b));
                    int c = appuserService.updateAppUserBalanceById(appUser);
                    if (c != 0) {
                        Map<String, Object> map = new LinkedHashMap<>();
                        map.put("appUser", getAppUserInfoById(userId));
                        return RespStatus.successs().element("data", map);
                    } else {
                        return RespStatus.fail("扣款失败");
                    }

                }
            } else {
                return RespStatus.fail("无此用户");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();

        }

    }


}
