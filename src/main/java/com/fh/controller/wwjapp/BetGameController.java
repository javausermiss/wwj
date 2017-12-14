package com.fh.controller.wwjapp;

import com.fh.entity.system.*;
import com.fh.rpc.LotteryWebServiceImpl;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.betgame.BetGameManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.gateway.GatewayManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.service.system.pond.PondManager;
import com.fh.util.wwjUtil.RespStatus;
import com.iot.game.pooh.web.rpc.interfaces.LotteryWebRpcService;
import com.sun.org.apache.regexp.internal.RE;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.iot.game.pooh.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 竞猜接口
 */
@Controller
@RequestMapping("/app")
public class BetGameController {

    @Resource(name = "betGameService")
    private BetGameManager betGameService;
    @Resource(name = "appuserService")
    private AppuserManager appuserService;
    @Resource(name = "dollService")
    private DollManager dollService;
    @Resource(name = "playBackService")
    private PlayBackManage playBackService;
    @Resource(name = "pondService")
    private PondManager pondService;
    @Resource(name = "playDetailService")
    private PlayDetailManage playDetailService;
    @Resource(name = "paymentService")
    private PaymentManager paymentService;


    /**
     * 奖池信息
     *
     * @param id
     * @return
     */

    public JSONObject getPondInfo(Integer id) {
        try {
            Pond pond = pondService.getPondById(id);
            return JSONObject.fromObject(pond);
        } catch (Exception e) {
            return null;
        }

    }

    public JSONObject getAppUserInfo(String id) {
        try {
            AppUser appUser = appuserService.getUserByID(id);
            return JSONObject.fromObject(appUser);
        } catch (Exception e) {
            return null;
        }

    }

    public JSONObject getPlayBackInfo(Integer id) {
        try {
            PlayBack playBack = playBackService.getPlayBackById(id);
            return JSONObject.fromObject(playBack);
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 游戏记录信息
     *
     * @param dollID
     * @return
     */
    public JSONObject getPlayDetailInfo(String dollID) {
        try {
            PlayDetail p = playDetailService.getPlayIdForPeople(dollService.getDollByID(dollID).getDOLL_NAME());
            return JSONObject.fromObject(p);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 给围观群众分发场次ID
     *
     * @return
     */
    @RequestMapping(value = "/getPlayId", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPlayId(@RequestParam("dollId") String dollId) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("playDetail", getPlayDetailInfo(dollId));
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }


    }

    /**
     * 任意时刻进行竞猜 2017/12/11
     *
     * @param userId
     * @param dollId
     * @param gold
     * @param guessKey
     * @return
     */
    @RequestMapping(value = "/newbets1", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject newbets1(
            @RequestParam("userId") String userId,
            @RequestParam("dollId") String dollId,
            @RequestParam("gold") String gold,
            @RequestParam("guessKey") String guessKey
    ) {
        try {
            //对用户的余额进行修改
            AppUser appUser = appuserService.getUserByID(userId);
            String balance = appUser.getBALANCE();
            if (Integer.parseInt(balance) > Integer.parseInt(gold)) {
                int n = Integer.parseInt(balance) - Integer.parseInt(gold);
                appUser.setBALANCE(String.valueOf(n));
                appuserService.updateAppUserBalanceById(appUser);
            } else {
                return RespStatus.fail("余额不足无法竞猜");
            }
            PlayDetail playDetail = new PlayDetail();
            playDetail.setDOLLNAME(dollService.getDollByID(dollId).getDOLL_NAME());
            playDetail.setNICKNAME(appuserService.getUserByID(userId).getNICKNAME());
            PlayDetail playDetail1ast = playDetailService.getPlayDetailLast(playDetail);//根据条件取得相应对象
            if (playDetail1ast != null) {
                String a = playDetail1ast.getSTATE();
                //已结束
                if (a.equals("0") || a.equals("1")) {
                    String guessid = playDetail.getGUESS_ID();//获取到场次ID 201712100001
                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    String dateString = formatter.format(currentTime);
                    String x = guessid.substring(0, 8);//取前八位进行判断
                    if (x.equals(dateString)) {
                        Long newId = Long.parseLong(guessid) + 1;//获取新的场次ID
                        PlayDetail p = new PlayDetail();
                        p.setGUESS_ID(String.valueOf(newId));
                        // p.setDOLL_ID(dollId);
                        playDetailService.reg(p);
                        Pond pond = pondService.getPondByPlayId(p.getGUESS_ID());
                        if (guessKey.trim().equals("1")) {
                            pond.setGUESS_Y(pond.getGUESS_Y() + 1);
                            pond.setGUESS_GOLD(Integer.valueOf(pond.getGUESS_GOLD() + gold));
                            int n = pondService.updatePondY(pond);
                            if (n == 0) {
                                return RespStatus.fail("奖池更新失败Y");
                            }
                        }
                        if (guessKey.trim().equals("0")) {
                            pond.setGUESS_N(pond.getGUESS_N() + 1);
                            pond.setGUESS_GOLD(Integer.valueOf(pond.getGUESS_GOLD() + gold));
                            int n = pondService.updatePondN(pond);
                            if (n == 0) {
                                return RespStatus.fail("奖池更新失败N");
                            }
                        }

                    } else {
                        Date currentTime1 = new Date();
                        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
                        String dateString1 = formatter1.format(currentTime1);
                        String sb = dateString1 + "0001";
                        PlayDetail newPlayDetai2 = new PlayDetail();
                        newPlayDetai2.setGUESS_ID(sb);
                        int n = playDetailService.reg(newPlayDetai2);
                        if (n == 0) {
                            return RespStatus.fail("增加隔天场次失败");
                        }
                        GuessDetail guessDetail = new GuessDetail();
                        guessDetail.setGUESS_KEY(guessKey);
                        guessDetail.setDOLL_ID(dollId);
                        guessDetail.setGUESS_GOLD(Integer.parseInt(gold));
                        guessDetail.setAPP_USER_ID(userId);
                        guessDetail.setPLAYBACK_ID(sb);
                        betGameService.regGuessDetail(guessDetail);
                        Pond pond = new Pond();
                        if (guessKey.trim().equals("1")) {
                            pond.setGUESS_Y(1);
                            pond.setGUESS_ID(sb);
                            pond.setGUESS_GOLD(Integer.parseInt(gold));
                            pondService.regPond(pond);
                        }
                        if (guessKey.trim().equals("0")) {
                            pond.setGUESS_N(1);
                            pond.setGUESS_ID(sb);
                            pond.setGUESS_GOLD(Integer.parseInt(gold));
                            pondService.regPond(pond);
                        }
                    }
                    //对局还未结束
                } else {
                    GuessDetail guessDetail = new GuessDetail();
                    guessDetail.setGUESS_KEY(guessKey);
                    guessDetail.setGUESS_GOLD(Integer.parseInt(gold));
                    guessDetail.setAPP_USER_ID(userId);
                    guessDetail.setPLAYBACK_ID(playDetail1ast.getGUESS_ID());
                    betGameService.regGuessDetail(guessDetail);
                    Pond pond = pondService.getPondByPlayId(playDetail1ast.getGUESS_ID());
                    if (guessKey.trim().equals("1")) {
                        pond.setGUESS_Y(pond.getGUESS_Y() + 1);
                        pond.setGUESS_GOLD(Integer.valueOf(pond.getGUESS_GOLD() + gold));
                        int n = pondService.updatePondY(pond);
                        if (n == 0) {
                            return RespStatus.fail("奖池更新失败Y");
                        }
                    }
                    if (guessKey.trim().equals("0")) {
                        pond.setGUESS_N(pond.getGUESS_N() + 1);
                        pond.setGUESS_GOLD(Integer.valueOf(pond.getGUESS_GOLD() + gold));
                        int n = pondService.updatePondN(pond);
                        if (n == 0) {
                            return RespStatus.fail("奖池更新失败N");
                        }
                    }
                }
            } else {
                Date currentTime1 = new Date();
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
                String dateString1 = formatter1.format(currentTime1);
                String num = "0001";
                String guessId = dateString1 + num;
                PlayDetail newPlayDetail = new PlayDetail();
                newPlayDetail.setGUESS_ID(guessId);
                int n = playDetailService.reg(newPlayDetail);
                if (n == 0) {
                    return RespStatus.fail("增加隔天场次失败");
                }
                GuessDetail guessDetail = new GuessDetail();
                guessDetail.setGUESS_KEY(guessKey);
                guessDetail.setDOLL_ID(dollId);
                guessDetail.setGUESS_GOLD(Integer.parseInt(gold));
                guessDetail.setAPP_USER_ID(userId);
                guessDetail.setPLAYBACK_ID(guessId);
                betGameService.regGuessDetail(guessDetail);
                Pond pond = new Pond();
                if (guessKey.trim().equals("1")) {
                    pond.setGUESS_Y(1);
                    pond.setGUESS_ID(guessId);
                    pond.setGUESS_GOLD(Integer.parseInt(gold));
                    pondService.regPond(pond);
                }
                if (guessKey.trim().equals("0")) {
                    pond.setGUESS_N(1);
                    pond.setGUESS_ID(guessId);
                    pond.setGUESS_GOLD(Integer.parseInt(gold));
                    pondService.regPond(pond);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }
        return RespStatus.successs();
    }

    /**
     * 2017/12/12
     * 玩家点击竞猜，然后围观群众进行投注
     *
     * @param userId   参与竞猜用户ID
     * @param dollId   娃娃机房间ID
     * @param wager    投注金额
     * @param guessId  场次ID
     * @param guessKey 竞猜 中 或者 不中
     * @return
     */
    @RequestMapping(value = "/bets", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject bets(
            @RequestParam("userId") String userId,
            @RequestParam("dollId") String dollId,
            @RequestParam("wager") int wager,
            @RequestParam("guessId") String guessId,
            @RequestParam("guessKey") String guessKey) {

        try {
            PlayDetail p = playDetailService.getPlayDetailByGuessID(guessId);
            String s = p.getSTOP_FLAG();
            if (!s.equals("1")) {
                return RespStatus.fail("禁止投注！");
            }
            AppUser appUser = appuserService.getUserByID(userId);
            String balance = appUser.getBALANCE();
            if (Integer.parseInt(balance) > wager) {
                int n = Integer.parseInt(balance) - wager;
                appUser.setBALANCE(String.valueOf(n));
                appuserService.updateAppUserBalanceById(appUser);
            } else {
                return RespStatus.fail("余额不足无法竞猜");
            }
            //增加消费记录
            Payment payment = new Payment();
            payment.setCOST_TYPE("1");
            payment.setDOLLID(dollId);
            payment.setUSERID(userId);
            payment.setGOLD(String.valueOf(wager));
            paymentService.reg(payment);
            //增加竞猜记录
            GuessDetail guessDetail = new GuessDetail(userId, dollId, guessKey, wager, guessId);
            betGameService.regGuessDetail(guessDetail);
            //更新奖池信息
            Pond pond = pondService.getPondByPlayId(guessId);
            if (guessKey.trim().equals("1")) {
                int people = pond.getGUESS_Y();
                int money = pond.getGUESS_GOLD();
                pond.setGUESS_GOLD(money + wager);
                pond.setGUESS_Y(people + 1);
                int cy = pondService.updatePondY(pond);
                if (cy == 0) {
                    return RespStatus.fail("更新失败Y");
                }
            }
            if (guessKey.trim().equals("0")) {
                int people = pond.getGUESS_N();
                int money = pond.getGUESS_GOLD();
                pond.setGUESS_GOLD(money + wager);
                pond.setGUESS_N(people + 1);
                int cn = pondService.updatePondN(pond);
                if (cn == 0) {
                    return RespStatus.fail("更新失败N");
                }
            }
            Map<String, Object> map = new HashMap<>();
            map.put("pond", getPondInfo(pond.getPOND_ID()));
            map.put("appUser", getAppUserInfo(appUser.getUSER_ID()));
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }


    }


    /**
     * 围观群众进行投注竞猜
     *
     * @param userId     用户ID
     * @param wager      下注金额
     * @param guessKey   竞猜是否押中1 0
     * @param playBackId 视频列表id 场次ID
     * @param dollId     娃娃机主键
     * @return
     */
   /* @RequestMapping(value = "/bets", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject bets(
            @RequestParam("userId") String userId,//用户ID
            @RequestParam("wager") Integer wager,   //下注金额
            @RequestParam("guessKey") String guessKey, //竞猜是否押中1 0
            @RequestParam("playBackId") String playBackId,//视频列表id 场次ID
            @RequestParam("dollId") String dollId //娃娃机主键

    ) {
        try {
            GuessDetail guessDetail = new GuessDetail(userId, dollId, guessKey, wager, playBackId);
            int n = betGameService.regGuessDetail(guessDetail);
            if (n != 0) {
                AppUser appUser = appuserService.getUserByID(userId);
                if (appUser == null) {
                    return RespStatus.fail("用户不存在");
                }
                Pond pond = pondService.getPondByPlayId(playBackId);
                if (pond == null) {
                    return RespStatus.fail("奖池不存在");
                }
                String balance = appUser.getBALANCE();
                if (Integer.parseInt(balance) > wager) {
                    int newBalance = Integer.parseInt(balance) - wager;
                    appUser.setBALANCE(String.valueOf(newBalance));
                    int change = appuserService.updateAppUserBalanceById(appUser);
                    if (change == 0) {
                        return RespStatus.fail("下注扣款失败");
                    }
                    if (guessKey.trim().equals("1")) {
                        int people = pond.getGUESS_Y();
                        int money = pond.getGUESS_GOLD();
                        pond.setGUESS_GOLD(money + wager);
                        pond.setGUESS_Y(people + 1);
                        int cy = pondService.updatePondY(pond);
                        if (cy == 0) {
                            return RespStatus.fail("更新失败Y");
                        }

                    }
                    if (guessKey.trim().equals("0")) {
                        int people = pond.getGUESS_N();
                        int money = pond.getGUESS_GOLD();
                        pond.setGUESS_GOLD(money + wager);
                        pond.setGUESS_N(people + 1);
                        int cn = pondService.updatePondN(pond);
                        if (cn == 0) {
                            return RespStatus.fail("更新失败N");
                        }
                    }
                } else {
                    return RespStatus.fail("余额不足");
                }
                Map<String, Object> map = new HashMap<>();
                map.put("pond", getPondInfo(pond.getPOND_ID()));
                map.put("appUser", getAppUserInfo(appUser.getUSER_ID()));
                return RespStatus.successs().element("data", map);
            } else {
                return RespStatus.fail("下注失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }


    }
*/

    /**
     * 获取下注人数对比的接口
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPond", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPond(@RequestParam("playId") String id) {

        try {
            Pond pond = pondService.getPondByPlayId(id);
            if (pond == null) {
                return RespStatus.fail("该奖池不存在");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("pond", getPondInfo(pond.getPOND_ID()));
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }


    }

}
