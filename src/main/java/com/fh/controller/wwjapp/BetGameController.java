package com.fh.controller.wwjapp;

import com.fh.entity.system.AppUser;
import com.fh.entity.system.GuessDetail;
import com.fh.entity.system.PlayBack;
import com.fh.entity.system.Pond;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.betgame.BetGameManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.gateway.GatewayManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.pond.PondManager;
import com.fh.util.wwjUtil.RespStatus;
import com.sun.org.apache.regexp.internal.RE;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
     * 给围观群众分发场次ID
     *
     * @return
     */
    @RequestMapping(value = "/getPlayId", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPlayId(@RequestParam("dollName") String dollName) {
        try {
            PlayBack playBack = playBackService.getPlayBackLatest(dollName);
            Map<String, Object> map = new HashMap<>();
            map.put("playBack", getPlayBackInfo(playBack.getID()));
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }


    }


    /**
     * 围观群众进行投注竞猜
     *
     * @param userId 用户ID
     * @param wager 下注金额
     * @param guessKey 竞猜是否押中1 0
     * @param playBackId 视频列表id 场次ID
     * @param dollId 娃娃机主键
     * @return
     */
    @RequestMapping(value = "/bets", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject bets(
            @RequestParam("userId") String userId,//用户ID
            @RequestParam("wager") Integer wager,   //下注金额
            @RequestParam("guessKey") String guessKey, //竞猜是否押中1 0
            @RequestParam("playBackId") Integer playBackId,//视频列表id 场次ID
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
                map.put("appUser",getAppUserInfo(appUser.getUSER_ID()));
                return RespStatus.successs().element("data", map);
            } else {
                return RespStatus.fail("下注失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }


    }

    /**
     * 获取下注人数对比的接口
     * @param id
     * @return
     */
    @RequestMapping(value = "/getPond", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPond(@RequestParam("playId") Integer id) {

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
