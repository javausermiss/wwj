package com.fh.controller.wwjapp;

import com.fh.entity.system.*;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.betgame.BetGameManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.playback.impl.PlayBackService;
import com.fh.service.system.pond.PondManager;
import com.fh.util.wwjUtil.RedisUtil;
import com.fh.util.wwjUtil.RespStatus;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频回放记录存储&游戏记录&竞猜结算
 */
@Controller
@RequestMapping(value = "/play")
public class PlayBackController {

    @Resource(name = "appuserService")
    private AppuserManager appuserService;
    @Resource(name = "dollService")
    private DollManager dollService;
    @Resource(name = "playBackService")
    private PlayBackManage playBackService;
    @Resource(name = "pondService")
    private PondManager pondService;
    @Resource(name = "betGameService")
    private BetGameManager betGameService;

  /*  */

    /**
     * 视频记录存储&抓取成功记录增加
     * 竞猜结算
     * (无论成功失败均存储)
     *
     * @param username
     * @param time
     * @param id
     * @return
     *//*
    @RequestMapping(value = "/regPlayBack", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject regPlayBack(
            @RequestParam("id") Integer id,//视频列表ID
            @RequestParam("time") String time,
            @RequestParam("nickName") String username,
            @RequestParam("state") String state,//1 成功 0 失败
            @RequestParam("dollName") String dollname
    ) {
        try {
            AppUser appUser = appuserService.getAppUserByNickName(username);
            if (appUser != null) {
                int oldCount = appUser.getDOLLTOTAL();
                if (state.trim().equals("1")) {
                    appUser.setDOLLTOTAL(oldCount + 1);
                    int n = appuserService.updateAppUserDollTotalByName(appUser);
                    if (n == 0) {
                        return RespStatus.fail("更新抓娃娃总数失败");
                    }
                }
            } else {
                return RespStatus.fail("无此用户");
            }
            Doll doll = dollService.getDollByDollName(dollname);
            if (doll != null) {
                return RespStatus.fail("此娃娃机不存在");
            } else {
                int gold = doll.getDOLL_GOLD();//抓娃娃所需金币数
                int conversionGold;//可兑换金币数
                switch (gold) {
                    case 19:
                        conversionGold = 80;
                        break;
                    case 29:
                        conversionGold = 120;
                        break;
                    default:
                        conversionGold = 180;
                }
                PlayBack playBack = playBackService.getPlayBackById(id);
                playBack.setSTATE(state);
                playBack.setCREATETIME(time);
                playBack.setCONVERSIONGOLD(conversionGold);
                int n = playBackService.updatePlayBack(playBack);
                if (n != 0) {
                    Pond pond = pondService.getPondByPlayId(id);
                    int allGold = pond.getGUESS_GOLD();
                    int y = pond.getGUESS_Y();//猜1
                    int cn = pond.getGUESS_N();//猜0
                    int avg; //结算获胜者的平均金额
                    String s = playBack.getSTATE();//取得压中的状态
                    String file = "";//失败状态
                    if (s.equals("1")) {
                        avg = (int) Math.round(allGold * 1.0 / y);
                        file = "0";
                    } else {
                        avg = (int) Math.round(allGold * 1.0 / cn);
                        file = "1";
                    }
                    List<GuessDetail> filler = betGameService.getFailer(new GuessDetail(file, id));//获取失败者
                    if (filler.size() != 0) {
                        for (int k = 0; k < filler.size(); k++) {
                            GuessDetail filePerson = filler.get(k);
                            filePerson.setSETTLEMENT_FLAG("Y");
                            int filenum = betGameService.updateGuessDetail(filePerson);
                            if (filenum == 0) {
                                return RespStatus.fail("更新结算标签失败");
                            }
                        }
                    }
                    List<GuessDetail> winner = betGameService.getWinner(new GuessDetail(s, id));//获取成功者
                    if (winner.size() != 0) {
                        for (int f = 0; f < winner.size(); f++) {
                            GuessDetail winPerson = winner.get(f);
                            String userId = winPerson.getAPP_USER_ID();
                            AppUser appUser1 = appuserService.getUserByID(userId);
                            int balance = Integer.parseInt(appUser1.getBALANCE());
                            appUser1.setBALANCE(String.valueOf(balance + avg));
                            int cb = appuserService.updateAppUserBalanceById(appUser1);
                            if (cb == 0) {
                                return RespStatus.fail("返奖失败");
                            }
                            winPerson.setSETTLEMENT_FLAG("Y");
                            int fc = betGameService.updateGuessDetail(winPerson);
                            if (fc == 0) {
                                return RespStatus.fail("更新结算标签失败");
                            }
                        }
                    }

                } else {
                    return RespStatus.fail("增加记录失败");
                }
                return RespStatus.successs();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }
    }*/

    /**
     *视频上传
     * @param
     * @param time
     * @param username
     * @param state
     * @param dollname
     * @return
     */
    @RequestMapping(value = "/regPlayBack", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject regPlayBack(
            @RequestParam("time") String time,
            @RequestParam("nickName") String username,
            @RequestParam("state") String state,//1 成功 0 失败
            @RequestParam("dollName") String dollname
    ) {
        try {
            Doll doll = dollService.getDollByDollName(dollname);
            int gold = doll.getDOLL_GOLD();//抓娃娃所需金币数
            int conversionGold;//可兑换金币数
            switch (gold) {
                case 19:
                    conversionGold = 80;
                    break;
                case 29:
                    conversionGold = 120;
                    break;
                default:
                    conversionGold = 180;
            }
            //增加视频列表
            PlayBack playBack = new PlayBack();
            playBack.setDOLLNAME(dollname);
            playBack.setUSERNAME(username);
            playBack.setCONVERSIONGOLD(conversionGold);
            playBack.setSTATE(state);
            playBack.setCREATETIME(time);
            playBackService.reg(playBack);
            return RespStatus.successs();

        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }


    /**
     * 获取最近的存储的10条抓娃娃记录
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/getPlayRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getPlayRecord(
            @RequestParam("nickName") String username
    ) {
        try {
            List<PlayBack> p = playBackService.getDollCount(username);
            List<PlayBack> n = playBackService.getPlayRecord(username);
            Map<String, Object> map = new HashMap<>();
            map.put("playback", n);
            map.put("dollCount", p);
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }
    }


    /**
     * 获取抓娃娃记录
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/getAllPlayRecord", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getAllPlayRecord(
            @RequestParam("nickName") String username
    ) {
        try {
            List<PlayBack> n = playBackService.getAllPlayRecord(username);
            Map<String, Object> map = new HashMap<>();
            map.put("playback", n);
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }
    }

    /**
     * 查询最新的10人抓取成功记录
     *
     * @return
     */
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject getUserList() {
        try {
            List<PlayBack> list = playBackService.getPlayRecordPM();
            Map<String, Object> map = new HashMap<>();
            map.put("playback", list);
            return RespStatus.successs().element("data", map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }

    public static void main(String[] strings) {
        String sendGoods = "wqe,sda, ";
        String[] s = sendGoods.split("\\,");
        String name = s[0];
        String phone = s[1];
        String address = s[2];
        System.out.println(name);
        System.out.println(phone);
        System.out.println("空格" + address);

        int a = 38, b = 7;
        double b1 = (a * 1.0 / b);
        Math.round(38 * 1.0 / 7);
        Math.floor(b1);
        System.out.println((int) Math.floor(34 / 7));
    }


}
