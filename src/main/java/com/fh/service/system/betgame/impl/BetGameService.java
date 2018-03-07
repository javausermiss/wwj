package com.fh.service.system.betgame.impl;

import com.fh.controller.base.BaseController;
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
import com.fh.util.DateUtil;
import com.fh.util.PageData;

import com.fh.util.wwjUtil.RedisUtil;
import com.fh.util.wwjUtil.RespStatus;
import com.iot.game.pooh.server.rpc.interfaces.LotteryServerRpcService;
import com.iot.game.pooh.server.rpc.interfaces.bean.RpcCommandResult;
import com.iot.game.pooh.server.rpc.interfaces.bean.RpcReturnCode;
import com.iot.game.pooh.web.rpc.interfaces.entity.GuessDetail;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service("betGameService")
@Slf4j
public class BetGameService extends BaseController implements BetGameManager {

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
    @Resource
    private LotteryServerRpcService lotteryServerRpcService;


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

    /**
     * 列表
     *
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("GuessDetailMapper.datalistPage", page);
    }

    /**
     * 列表(全部)
     *
     * @param pd
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("GuessDetailMapper.listAll", pd);
    }

    /**
     * 通过id获取数据
     *
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("GuessDetailMapper.findById", pd);
    }

    /**
     * 批量删除
     *
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("GuessDetailMapper.deleteAll", ArrayDATA_IDS);
    }

    /**
     * 增加竞猜记录
     *
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    @Override
    public int regGuessDetail(GuessDetailL guessDetailL) throws Exception {
        return (int) dao.save("GuessDetailMapper.regGuessDetail", guessDetailL);
    }

    /**
     * 修改竞猜记录
     *
     * @param guessDetailL
     * @return
     * @throws Exception
     */
    @Override
    public int updateGuessDetail(GuessDetailL guessDetailL) throws Exception {
        return (int) dao.update("GuessDetailMapper.updateGuessDetail", guessDetailL);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<GuessDetailL> getWin(Integer playID) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getWin", playID);
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
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getAllGuesser", guessid);
    }

    @Override
    public int updateGuessDetailGuessType(GuessDetailL guessDetailL) throws Exception {
        return (int) dao.update("GuessDetailMapper.updateGuessDetailGuessType", guessDetailL);
    }

    @Override
    public GuessDetailL getGuessDetail(GuessDetailL guessDetailL) throws Exception {
        return (GuessDetailL) dao.findForObject("GuessDetailMapper.getGuessDetail", guessDetailL);
    }

    /**
     * 通过 userId 查询 最新10条竞猜记录
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public List<PageData> getGuessDetailTop10ByUserId(String userId) throws Exception {
        return (List<PageData>) dao.findForList("GuessDetailMapper.getGuessDetailTop10ByUserId", userId);
    }

    @Override
    public JSONObject doBet(String userId, String dollId, int wager, String guessId, String guessKey) throws Exception {
        //判断用户是否竞猜过
        GuessDetailL gs = new GuessDetailL();
        gs.setAPP_USER_ID(userId);
        gs.setPLAYBACK_ID(guessId);
        gs.setDOLL_ID(dollId);
        GuessDetailL guessDetailL1 = this.getGuessDetail(gs);
        if (guessDetailL1 != null) {
            return RespStatus.fail("该用户已经竞猜过");
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
        if (appUser == null) {
            return null;
        }

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
        payment.setGOLD("-" + String.valueOf(wager));
        payment.setREMARK("竞猜" + dollService.getDollByID(dollId).getDOLL_NAME());
        paymentService.reg(payment);
        //增加竞猜记录
        GuessDetailL guessDetailL = new GuessDetailL(userId, dollId, guessKey, wager, guessId);
        this.regGuessDetail(guessDetailL);
        //更新奖池信息
        Pond pond1 = new Pond();
        pond1.setGUESS_ID(guessId);
        pond1.setDOLL_ID(dollId);
        Pond pond = pondService.getPondByPlayId(pond1);
        Map<String, Object> map = new HashMap<>();
        map.put("pond", getPondInfo(pond.getPOND_ID()));
        map.put("appUser", getAppUserInfo(appUser.getUSER_ID()));
        return RespStatus.successs().element("data", map);
    }

    @Override
    public List<GuessDetailL> getWinByNum(GuessDetailL num) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getWinByNum", num);
    }

    @Override
    public List<GuessDetailL> getFailerByNum(GuessDetailL num) throws Exception {
        return (List<GuessDetailL>) dao.findForList("GuessDetailMapper.getFailerByNum", num);
    }

    @Override
    public RpcCommandResult doFree(String dollId, Integer gifinumber) throws Exception {
        logger.info("机器复位，房间号为--->" + dollId);
        Doll doll = dollService.getDollByID(dollId);
        if (doll == null) {
            logger.info("机器复位，房间号为空");
            return null;
        }
        PlayDetail playDetail = playDetailService.getPlayIdForPeople(dollId);//根据房间ID取得最新的游戏记录
        if (playDetail == null) {
            return null;
        }

        logger.info("机器复位时间----------------------->" + DateUtil.getTime());

        if (gifinumber != 0) {
            gifinumber = 1;
        }


        String state = playDetail.getPOST_STATE();//获取娃娃发送状态
        //网关自检发送多次free进入此判断逻辑,POST_STATE初始值为"-1"，判断是否已经结算过
        //STOP_FLAG 初始值为"0",下抓后为"-1",判断流程是否走完
        if (playDetail.getSTOP_FLAG().equals("0") //流程未走完
                || state.equals("0") //结算过
                || state.equals("1") //待发货
                || state.equals("2") //已兑换
                || state.equals("3") //已发货
                ) {
            return null;
        }

        //更新玩家抓取记录
        playDetail.setSTATE(String.valueOf(gifinumber));//是否抓中
        playDetail.setPOST_STATE("0"); //初始化娃娃状态
        playDetail.setDOLLID(dollId);
        playDetailService.updatePlayDetailState(playDetail);
        String play_user_Id = playDetail.getUSERID();//获取玩家ID
        //更新用户的娃娃数量
        if (gifinumber == 1) {
            AppUser appUser = appuserService.getUserByID(play_user_Id);
            Integer new_dolltotal = appUser.getDOLLTOTAL() + 1;
            appUser.setDOLLTOTAL(new_dolltotal);
            appuserService.updateAppUserDollTotalById(appUser);
        }

        //给中奖用户结算奖金
        String reward_num = playDetail.getREWARD_NUM();
        GuessDetailL guessDetailL = new GuessDetailL();
        guessDetailL.setDOLL_ID(dollId);
        guessDetailL.setPLAYBACK_ID(playDetail.getGUESS_ID());
        guessDetailL.setGUESS_KEY(reward_num);
        List<GuessDetailL> list = this.getWinByNum(guessDetailL);

        List<GuessDetail> guessDetail_list = new LinkedList<>();

        StringBuilder sb = new StringBuilder();

        if (list.size() != 0) {
            logger.info("竞猜成功者数量--------------->" + list.size());
            for (int i = 0; i < list.size(); i++) {
                //更新竞猜记录消息
                GuessDetailL winPerson = list.get(i);

                //预期奖金
                int reword = winPerson.getGUESS_GOLD() * 5;

                winPerson.setSETTLEMENT_GOLD(reword);
                winPerson.setSETTLEMENT_FLAG("Y");
                winPerson.setGUESS_TYPE(playDetail.getREWARD_NUM());
                this.updateGuessDetail(winPerson);

                //更新用户余额及竞猜次数
                String guess_win_user = winPerson.getAPP_USER_ID();
                AppUser appUser = appuserService.getUserByID(guess_win_user);
                String old_balance = appUser.getBALANCE();
                Integer guessNum = appUser.getBET_NUM();
                Integer new_betnum = gifinumber + 1;
                String new_balance = String.valueOf(Integer.valueOf(old_balance) + reword);
                appUser.setBALANCE(new_balance);
                appUser.setBET_NUM(new_betnum);
                appuserService.updateAppUserBlAndBnById(appUser);

                logger.info("获奖用户ID------------->" + guess_win_user);

                //统计中奖用户昵称 ID
                String nickname = appUser.getNICKNAME();
                GuessDetail guessDetail = new GuessDetail();
                guessDetail.setNickname(nickname);
                guessDetail.setAppUserId(guess_win_user);
                guessDetail_list.add(guessDetail);

                //更新收支表
                Payment payment = new Payment();
                payment.setGOLD("+" + String.valueOf(reword));
                payment.setUSERID(winPerson.getAPP_USER_ID());
                payment.setDOLLID(dollId);
                payment.setCOST_TYPE("3");
                payment.setREMARK("竞猜成功");
                paymentService.reg(payment);

                sb.append(appUser.getNICKNAME()).append(",");

            }

            sb.deleteCharAt(sb.length() - 1);
            //获取每期中奖者的昵称
            Pond pond_n = new Pond();
            pond_n.setGUESS_ID(playDetail.getGUESS_ID());
            pond_n.setDOLL_ID(dollId);
            Pond pond = pondService.getPondByPlayId(pond_n);//查询对应奖池信息
            pond.setGUESSER_NAME(sb.toString());
            pondService.updatePondGuesser(pond);

        }
        //竞猜失败的用户
        List<GuessDetailL> failer = this.getFailerByNum(guessDetailL);

        if (failer.size() != 0) {
            logger.info("竞猜失败者数量--------------->" + failer.size());
            for (int i = 0; i < failer.size(); i++) {
                //更新竞猜记录消息
                GuessDetailL filePerson = failer.get(i);
                filePerson.setSETTLEMENT_GOLD(0);
                filePerson.setSETTLEMENT_FLAG("Y");
                filePerson.setGUESS_TYPE(playDetail.getREWARD_NUM());
                this.updateGuessDetail(filePerson);
            }
        }
        logger.info("前端展示的获胜者数量为-->" + guessDetail_list.size());

        RpcCommandResult rpcCommandResult = new RpcCommandResult();
        RpcReturnCode result = lotteryServerRpcService.noticeDrawLottery(dollId, playDetail.getGUESS_ID(), guessDetail_list);
        if (RpcReturnCode.SUCCESS == rpcCommandResult.getRpcReturnCode()) {
            log.info("通知成功");
        } else {
            log.info("通知失败");
        }
        rpcCommandResult.setRpcReturnCode(result);
        return rpcCommandResult;
    }


}
