package com.fh.controller.wwjapp;

import com.fh.entity.system.*;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.betgame.BetGameManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.service.system.pond.PondManager;
import com.fh.util.wwjUtil.RespStatus;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
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
            PlayDetail p = playDetailService.getPlayIdForPeople(dollID);
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
            PlayDetail p1 = new PlayDetail();
            p1.setDOLLID(dollId);
            p1.setGUESS_ID(guessId);
            PlayDetail p = playDetailService.getPlayDetailByGuessID(p1);
            String s = p.getSTOP_FLAG();
            //默认0可以投注，-1 机器已经下抓，禁止投注
            if (!s.equals("0")) {
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
            GuessDetailL guessDetailL = new GuessDetailL(userId, dollId, guessKey, wager, guessId);
            betGameService.regGuessDetail(guessDetailL);
            //更新奖池信息
            Pond pond1 = new Pond();
            pond1.setGUESS_ID(guessId);
            pond1.setDOLLID(dollId);
            Pond pond = pondService.getPondByPlayId(pond1);
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
     * 获取下注人数对比的接口
     *
     * @param guessid
     * @param dollId
     * @return
     */
    @RequestMapping(value = "/getPond", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPond(@RequestParam("playId") String guessid,
                              @RequestParam("dollId") String dollId
                              ) {

        try {
            Pond pond = new Pond();
            pond.setDOLLID(dollId);
            pond.setGUESS_ID(guessid);
            Pond pond1 =  pondService.getPondByPlayId(pond);
            if (pond1 == null) {
                return RespStatus.fail("该奖池不存在");
            }
            Map<String, Object> map = new HashMap<>();
            map.put("pond", getPondInfo(pond1.getPOND_ID()));
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }

}
