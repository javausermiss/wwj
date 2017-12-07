package com.fh.controller.wwjapp;

import com.fh.entity.system.AppUser;
import com.fh.entity.system.PlayBack;
import com.fh.entity.system.Pond;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.playback.PlayBackManage;
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
     * @param aPhone
     * @param money
     * @return
     */
    @RequestMapping(value = "/balance", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject appUserPay(@RequestParam("phone") String aPhone, @RequestParam("money") String money) {
        try {
            String phone = new String(Base64Util.decryptBASE64(aPhone));
            AppUser appUser = appuserService.getUserByPhone(phone);
            if (appUser != null) {
                String balance = appUser.getBALANCE();
                int bal = Integer.parseInt(balance);
                int mon = Integer.parseInt(money);
                appUser.setBALANCE(String.valueOf(bal + mon));
                int n = appuserService.updateAppUserBalanceByPhone(appUser);
                if (n != 0) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    map.put("appUser", getAppUserInfo(phone));
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
     * 点击开始按钮 创建视频和奖池列表
     * @param username
     * @param dollname
     * @return
     */
    @RequestMapping(value = "/creatPlayList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody

    public JSONObject creatPlayList(@RequestParam("nickName") String username,
                                    @RequestParam("dollName") String dollname) {
        try {
            PlayBack playBack = new PlayBack();
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = format.format(date);
            playBack.setUSERNAME(username);
            playBack.setDOLLNAME(dollname);
            playBack.setGUESS_TIME(time);
            int regPlaydx = playBackService.regPlaydx(playBack);
            if (regPlaydx == 0) {
                return RespStatus.fail("创建记录表失败");
            }
            int playBackID = playBack.getID();
            Pond pond = new Pond(playBackID, null);
            int regPond = pondService.regPond(pond);
            if (regPond == 0) {
                return RespStatus.fail("创建奖池表失败");
            }
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("playBack", getPlayBackInfo(playBackID));
            return RespStatus.successs().element("data",map);
        } catch (Exception e) {
            e.printStackTrace();
            return RespStatus.fail();
        }

    }


    /**
     *  消费金币接口
     *
     * @param aPhone
     * @param cost
     * @return
     */
    @RequestMapping(value = "/costBalance", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public JSONObject costBalance(@RequestParam("phone") String aPhone,
                                  @RequestParam("gold") String cost
    ) {
        try {
            String phone = new String(Base64Util.decryptBASE64(aPhone));
            AppUser appUser = appuserService.getUserByPhone(phone);
            if (appUser != null) {
                String balance = appUser.getBALANCE();
                int a = Integer.parseInt(balance);
                int b = Integer.parseInt(cost);
                if (a < b) {
                    return RespStatus.fail("余额不足");
                } else {
                    appUser.setBALANCE(String.valueOf(a - b));
                    int c = appuserService.updateAppUserBalanceByPhone(appUser);
                    if (c != 0) {
                        Map<String, Object> map = new LinkedHashMap<>();
                        map.put("appUser", getAppUserInfo(phone));
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
