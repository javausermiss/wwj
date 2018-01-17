package com.fh.service.system.betgame.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.*;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.betgame.BetGameManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.service.system.pond.PondManager;
import com.fh.util.PageData;

import com.fh.util.wwjUtil.RespStatus;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("betGameService")
public class BetGameService implements BetGameManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

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

    /**列表
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("GuessDetailMapper.datalistPage", page);
    }

    /**列表(全部)
     * @param pd
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("GuessDetailMapper.listAll", pd);
    }

    /**通过id获取数据
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("GuessDetailMapper.findById", pd);
    }

    /**批量删除
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("GuessDetailMapper.deleteAll", ArrayDATA_IDS);
    }

    /**
     * 增加竞猜记录
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    @Override
    public int regGuessDetail(GuessDetailL guessDetailL) throws Exception {
        return (int)dao.save("GuessDetailMapper.regGuessDetail", guessDetailL);
    }
    /**
     * 修改竞猜记录
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    @Override
    public int updateGuessDetail(GuessDetailL guessDetailL) throws Exception {
        return (int)dao.update("GuessDetailMapper.updateGuessDetail", guessDetailL);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetailL> getWin(Integer playID) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getWin",playID);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetailL> getFailer(GuessDetailL guessDetailL) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getFailer", guessDetailL);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetailL> getWinner(GuessDetailL guessDetailL) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getWinner", guessDetailL);
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetailL> getAllGuesser(String guessid) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getAllGuesser",guessid);
    }

    @Override
    public int updateGuessDetailGuessType(GuessDetailL guessDetailL) throws Exception {
        return (int)dao.update("GuessDetailMapper.updateGuessDetailGuessType", guessDetailL);
    }

    @Override
    public GuessDetailL getGuessDetail(GuessDetailL guessDetailL) throws Exception {
        return (GuessDetailL) dao.findForObject("GuessDetailMapper.getGuessDetail", guessDetailL);
    }
    
    /**
     * 通过 userId 查询 最新10条竞猜记录
     * @param userId
     * @return
     * @throws Exception
     */
    public List<PageData> getGuessDetailTop10ByUserId(String userId) throws Exception{
    	 return (List<PageData>) dao.findForList("GuessDetailMapper.getGuessDetailTop10ByUserId",userId);
    }

    @Override
    public JSONObject doBet(String userId, String dollId, int wager, String guessId, String guessKey) throws Exception {
        AppUser appUser1 = appuserService.getAppUserRanklist(userId);
        if (appUser1==null){
            return null;
        }
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
        payment.setGOLD("-"+String.valueOf(wager));
        payment.setREMARK("竞猜"+dollService.getDollByID(dollId).getDOLL_NAME());
        paymentService.reg(payment);
        //增加竞猜记录
        GuessDetailL guessDetailL = new GuessDetailL(userId, dollId, guessKey, wager, guessId);
        this.regGuessDetail(guessDetailL);
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
    }
}
