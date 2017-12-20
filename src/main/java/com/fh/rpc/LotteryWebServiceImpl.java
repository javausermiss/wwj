package com.fh.rpc;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.fh.entity.system.*;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.betgame.BetGameManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.service.system.pond.PondManager;
import com.iot.game.pooh.server.entity.json.enums.PoohAbnormalStatus;
import com.iot.game.pooh.server.entity.json.enums.PoohNormalStatus;
import com.iot.game.pooh.server.rpc.interfaces.LotteryServerRpcService;
import com.iot.game.pooh.server.rpc.interfaces.bean.RpcCommandResult;
import com.iot.game.pooh.server.rpc.interfaces.bean.RpcReturnCode;
import com.iot.game.pooh.web.rpc.interfaces.LotteryWebRpcService;
import com.iot.game.pooh.web.rpc.interfaces.entity.GuessDetail;
import lombok.extern.slf4j.Slf4j;


import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class LotteryWebServiceImpl implements LotteryWebRpcService {
    @Resource(name = "betGameService")
    private BetGameManager betGameService;
    @Resource(name = "appuserService")
    private AppuserManager appuserService;
    @Resource(name = "pondService")
    private PondManager pondService;
    @Resource(name = "playDetailService")
    private PlayDetailManage playDetailService;
    @Resource(name = "paymentService")
    private PaymentManager paymentService;
    @Resource(name = "dollService")
    private DollManager dollService;

    @Reference(version = "1.0", check = false)
    private LotteryServerRpcService lotteryServerRpcService;

    /**
     * 开始竞猜(点击开始)
     */
    @Override
    public RpcCommandResult startLottery(String dollId, String userId) {
        try {
            AppUser appUser = appuserService.getUserByID(userId);
            String b1 = appUser.getBALANCE();
            int old = Integer.valueOf(b1);
            int b2 = dollService.getDollByID(dollId).getDOLL_GOLD();
            if (Integer.valueOf(b1) < b2) {
                return null;
            }
            Payment payment = new Payment();
            payment.setCOST_TYPE("0");
            payment.setDOLLID(dollId);
            payment.setUSERID(userId);
            payment.setGOLD(String.valueOf(dollService.getDollByID(dollId).getDOLL_GOLD()));
            paymentService.reg(payment);
            appUser.setBALANCE(String.valueOf(old - b2));
            appuserService.updateAppUserBalanceById(appUser);
            PlayDetail playDetail = new PlayDetail();
            playDetail.setDOLLID(dollId);
            PlayDetail p = playDetailService.getPlayDetailLast(playDetail);//获取最新的场次
            String conversionGold = "";
            int gold = dollService.getDollByID(dollId).getDOLL_GOLD();
            switch (gold) {
                case 19:
                    conversionGold = "80";
                    break;
                case 29:
                    conversionGold = "120";
                    break;
                default:
                    conversionGold = "180";
            }
            if (p == null) {
                Date currentTime1 = new Date();
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyyMMdd");
                String dateString1 = formatter1.format(currentTime1);
                String num = "0001";
                String guessId = dateString1 + num;
                PlayDetail newPlayDetail = new PlayDetail();
                newPlayDetail.setGUESS_ID(guessId);
                newPlayDetail.setDOLLID(dollId);
                newPlayDetail.setUSERID(userId);
                newPlayDetail.setCONVERSIONGOLD(conversionGold);
                newPlayDetail.setGOLD(String.valueOf(dollService.getDollByID(dollId).getDOLL_GOLD()));
                playDetailService.reg(newPlayDetail);
                Pond pond = new Pond(newPlayDetail.getGUESS_ID(), null);
                pondService.regPond(pond);
                RpcCommandResult rpcCommandResult = new RpcCommandResult();
                rpcCommandResult.setRpcReturnCode(RpcReturnCode.SUCCESS);
                rpcCommandResult.setInfo(guessId); ///这里写期号
                return rpcCommandResult;
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
                    newp.setUSERID(userId);
                    newp.setDOLLID(dollId);
                    newp.setCONVERSIONGOLD(conversionGold);
                    newp.setGOLD(String.valueOf(dollService.getDollByID(dollId).getDOLL_GOLD()));
                    playDetailService.reg(newp);
                    Pond pond = new Pond(newp.getGUESS_ID(), null);
                    pondService.regPond(pond);
                    RpcCommandResult rpcCommandResult = new RpcCommandResult();
                    rpcCommandResult.setRpcReturnCode(RpcReturnCode.SUCCESS);
                    rpcCommandResult.setInfo(newGuessId); ///这里写期号
                    return rpcCommandResult;
                } else {
                    Date current = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    String date = format.format(current);
                    String newGuessID = date + "0001";
                    PlayDetail playDetail1 = new PlayDetail();
                    playDetail1.setDOLLID(dollId);
                    playDetail1.setUSERID(userId);
                    playDetail1.setCONVERSIONGOLD(conversionGold);
                    playDetail1.setGUESS_ID(newGuessID);
                    playDetail1.setGOLD(String.valueOf(dollService.getDollByID(dollId).getDOLL_GOLD()));
                    playDetailService.reg(playDetail1);
                    Pond pond = new Pond(playDetail1.getGUESS_ID(), null);
                    pondService.regPond(pond);
                    RpcCommandResult rpcCommandResult = new RpcCommandResult();
                    rpcCommandResult.setRpcReturnCode(RpcReturnCode.SUCCESS);
                    rpcCommandResult.setInfo(newGuessID); ///这里写期号
                    return rpcCommandResult;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 结束竞猜(机器下爪)
     */
    @Override
    public RpcCommandResult endLottery(String roomId, String userName) {
        try {
            PlayDetail playDetail = playDetailService.getPlayIdForPeople(roomId);
            playDetail.setSTOP_FLAG("-1");
            playDetailService.updatePlayDetailStopFlag(playDetail);
            RpcCommandResult rpcCommandResult = new RpcCommandResult();
            rpcCommandResult.setRpcReturnCode(RpcReturnCode.SUCCESS);
            rpcCommandResult.setInfo("结束下抓，禁止竞猜");
            return rpcCommandResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 开始算奖(机器复位)
     */
    @Override
    public RpcCommandResult drawLottery(String roomId, Integer gifinumber) {
        try {
            PlayDetail playDetail = playDetailService.getPlayIdForPeople(roomId);
            playDetail.setSTATE(String.valueOf(gifinumber));
            playDetailService.updatePlayDetailState(playDetail);
            Pond pond = pondService.getPondByPlayId(playDetail.getGUESS_ID());
            if (pond.getPOND_FLAG().equals("0")) {
                return null;
            }
            pond.setPOND_FLAG("0");//此标签代表禁止再次结算
            pondService.updatePondFlag(pond);//更新标签
            int allGold = pond.getGUESS_GOLD();
            int y = pond.getGUESS_Y();//猜1
            int cn = pond.getGUESS_N();//猜0
            int avg; //结算获胜者的平均金额
            String s = playDetail.getSTATE();//取得压中的状态
            String file = "";//失败状态
            String userid = playDetail.getUSERID();
            if (String.valueOf(gifinumber).equals("1")) {
                //用户抓中，更新抓中总数
                AppUser appUser = appuserService.getUserByID(userid);
                int oldCount = appUser.getDOLLTOTAL();
                appUser.setDOLLTOTAL(oldCount + 1);
                appuserService.updateAppUserDollTotalById(appUser);
                avg = (int) Math.floor(allGold / y);
                file = "0";
            } else {
                avg = (int) Math.floor(allGold / cn);
                file = "1";
            }

            List<com.fh.entity.system.GuessDetail> filler = betGameService.getFailer(new com.fh.entity.system.GuessDetail(file, playDetail.getGUESS_ID()));//获取失败者
            if (filler.size() != 0) {
                for (int k = 0; k < filler.size(); k++) {
                    com.fh.entity.system.GuessDetail filePerson = filler.get(k);
                    filePerson.setSettlementFlag("Y");
                    filePerson.setGuessType("0");
                    betGameService.updateGuessDetail(filePerson);
                }
            }
            List<com.fh.entity.system.GuessDetail> winner = betGameService.getWinner(new com.fh.entity.system.GuessDetail(s, playDetail.getGUESS_ID()));//获取成功者
            List<GuessDetail> win = new LinkedList<>();
            for (int i = 0; i < winner.size(); i++) {
                com.fh.entity.system.GuessDetail guessDetail = winner.get(i);
                GuessDetail guessDetail1 = new GuessDetail();
                guessDetail1.setAppUserId(guessDetail.getDollId());
                guessDetail1.setDollId(guessDetail.getDollId());
                guessDetail1.setPlaybackId(guessDetail.getPlaybackId());
                win.add(guessDetail1);
            }
            if (winner.size() != 0) {
                for (int f = 0; f < winner.size(); f++) {
                    com.fh.entity.system.GuessDetail winPerson = winner.get(f);
                    String userId = winPerson.getAppUserId();
                    AppUser appUser1 = appuserService.getUserByID(userId);
                    int balance = Integer.parseInt(appUser1.getBALANCE());
                    appUser1.setBALANCE(String.valueOf(balance + avg));
                    appuserService.updateAppUserBalanceById(appUser1);
                    winPerson.setSettlementFlag("Y");
                    winPerson.setGuessType("1");
                    betGameService.updateGuessDetail(winPerson);
                    //更新收支表
                    Payment payment = new Payment();
                    payment.setGOLD(String.valueOf(avg));
                    payment.setUSERID(userId);
                    payment.setDOLLID(roomId);
                    payment.setCOST_TYPE("3");
                    paymentService.reg(payment);
                }
            }
            RpcCommandResult rpcCommandResult = new RpcCommandResult();
            RpcReturnCode result = lotteryServerRpcService.noticeDrawLottery(roomId, playDetail.getGUESS_ID(), win);
            if (RpcReturnCode.SUCCESS == rpcCommandResult.getRpcReturnCode()) {
                log.info("通知成功");
            } else {
                log.info("通知失败");
            }
            rpcCommandResult.setRpcReturnCode(result);
            return rpcCommandResult;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public String gatewayException(String roomId, PoohNormalStatus poohNormalStatus, PoohAbnormalStatus poohAbnormalStatus) {
        try {
            //更新本次游戏记录状态为异常-1
            PlayDetail playDetail = playDetailService.getPlayIdForPeople(roomId);
            playDetail.setSTATE("-1");
            playDetailService.updatePlayDetailState(playDetail);
            //给玩家退回该场游戏金币
            String balance = appuserService.getUserByID(playDetail.getUSERID()).getBALANCE() + playDetail.getGOLD();
            AppUser appUser = appuserService.getUserByID(playDetail.getUSERID());
            appUser.setBALANCE(balance);
            appuserService.updateAppUserBalanceById(appUser);
            //给竞猜用户退回本场竞猜金币,更新本次竞猜记录为异常-1
            List<com.fh.entity.system.GuessDetail> list = betGameService.getAllGuesser(playDetail.getGUESS_ID());
            for (int i = 0; i < list.size(); i++) {
                com.fh.entity.system.GuessDetail guessDetail = list.get(i);
                guessDetail.setGuessType("-1");
                guessDetail.setSettlementFlag("Y");
                betGameService.updateGuessDetail(guessDetail);
                AppUser appUser1 = appuserService.getUserByID(guessDetail.getAppUserId());
                String nb = appUser1.getBALANCE() + playDetail.getGOLD();
                appUser1.setBALANCE(nb);
                appuserService.updateAppUserBalanceById(appUser1);
                //更新收支表
                Payment payment = new Payment();
                payment.setGOLD(String.valueOf(playDetail.getGOLD()));
                payment.setUSERID(guessDetail.getAppUserId());
                payment.setDOLLID(roomId);
                payment.setCOST_TYPE("2");
                paymentService.reg(payment);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return null;
    }

}
