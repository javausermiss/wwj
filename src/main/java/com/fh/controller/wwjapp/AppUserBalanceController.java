package com.fh.controller.wwjapp;

import com.fh.entity.system.*;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.service.system.pond.PondManager;
import com.fh.util.wwjUtil.Base64Util;
import com.fh.util.wwjUtil.RespStatus;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/pay")
public class AppUserBalanceController {

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
            if (Integer.valueOf(b1)<b2){
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
                    int c = appuserService.updateAppUserBalanceByPhone(appUser);
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
