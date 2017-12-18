package com.fh.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.fh.entity.system.*;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.betgame.BetGameManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.service.system.pond.PondManager;
import com.fh.util.wwjUtil.RespStatus;
import com.iot.game.pooh.server.entity.json.enums.PoohAbnormalStatus;
import com.iot.game.pooh.server.entity.json.enums.PoohNormalStatus;
import com.iot.game.pooh.server.entity.rpc.RpcCommandResult;
import com.iot.game.pooh.web.rpc.interfaces.LotteryWebRpcService;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 开始竞猜(点击开始)
     */
    @Override
    public RpcCommandResult startLottery(String roomId, String userName) {
        return null;
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            if (pond.getPOND_FLAG().equals("0")){
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
            String nickname = playDetail.getNICKNAME();
            if (String.valueOf(gifinumber).equals("1")) {
                //用户抓中，更新抓中总数
                AppUser appUser = appuserService.getAppUserByNickName(nickname);
                int oldCount = appUser.getDOLLTOTAL();
                appUser.setDOLLTOTAL(oldCount + 1);
                appuserService.updateAppUserDollTotalByName(appUser);
                avg = (int) Math.floor(allGold / y);
                file = "0";
            } else {
                avg = (int) Math.floor(allGold / cn);
                file = "1";
            }
            List<GuessDetail> filler = betGameService.getFailer(new GuessDetail(file, playDetail.getGUESS_ID()));//获取失败者
            if (filler.size() != 0) {
                for (int k = 0; k < filler.size(); k++) {
                    GuessDetail filePerson = filler.get(k);
                    filePerson.setSETTLEMENT_FLAG("Y");
                    filePerson.setGUESS_TYPE("0");
                    betGameService.updateGuessDetail(filePerson);
                }
            }
            List<GuessDetail> winner = betGameService.getWinner(new GuessDetail(s, playDetail.getGUESS_ID()));//获取成功者
            if (winner.size() != 0) {
                for (int f = 0; f < winner.size(); f++) {
                    GuessDetail winPerson = winner.get(f);
                    String userId = winPerson.getAPP_USER_ID();
                    AppUser appUser1 = appuserService.getUserByID(userId);
                    int balance = Integer.parseInt(appUser1.getBALANCE());
                    appUser1.setBALANCE(String.valueOf(balance + avg));
                    appuserService.updateAppUserBalanceById(appUser1);
                    winPerson.setSETTLEMENT_FLAG("Y");
                    winPerson.setGUESS_TYPE("1");
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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String gatewayException(String roomId, PoohNormalStatus poohNormalStatus, PoohAbnormalStatus poohAbnormalStatus) {
        try {
            //更新本次游戏记录状态为异常-1
            PlayDetail playDetail = playDetailService.getPlayIdForPeople(roomId);
            playDetail.setSTATE("-1");
            playDetailService.updatePlayDetailState(playDetail);
            //给玩家退回该场游戏金币
            String balance = appuserService.getAppUserByNickName(playDetail.getNICKNAME()).getBALANCE() + playDetail.getGOLD();
            AppUser appUser = appuserService.getAppUserByNickName(playDetail.getNICKNAME());
            appUser.setBALANCE(balance);
            appuserService.updateAppUserBalanceById(appUser);
            //给竞猜用户退回本场竞猜金币,更新本次竞猜记录为异常-1
            List<GuessDetail> list = betGameService.getAllGuesser(playDetail.getGUESS_ID());
            for (int i = 0; i < list.size(); i++) {
                GuessDetail guessDetail = list.get(i);
                guessDetail.setGUESS_TYPE("-1");
                guessDetail.setSETTLEMENT_FLAG("Y");
                betGameService.updateGuessDetail(guessDetail);
                AppUser appUser1 = appuserService.getUserByID(guessDetail.getAPP_USER_ID());
                String nb = appUser1.getBALANCE() + playDetail.getGOLD();
                appUser1.setBALANCE(nb);
                appuserService.updateAppUserBalanceById(appUser1);
                //更新收支表
                Payment payment = new Payment();
                payment.setGOLD(String.valueOf(playDetail.getGOLD()));
                payment.setUSERID(guessDetail.getAPP_USER_ID());
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
