package com.fh.service.system.sendgoods.impl;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.AppUser;
import com.fh.entity.system.Payment;
import com.fh.entity.system.PlayDetail;
import com.fh.entity.system.SendGoods;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.betgame.BetGameManager;
import com.fh.service.system.conversion.ConversionManager;
import com.fh.service.system.doll.DollManager;
import com.fh.service.system.doll.DollToyManager;
import com.fh.service.system.payment.PaymentManager;
import com.fh.service.system.playback.PlayBackManage;
import com.fh.service.system.playdetail.PlayDetailManage;
import com.fh.service.system.sendgoods.SendGoodsManager;
import com.fh.util.PageData;
import com.fh.util.wwjUtil.RespStatus;
import com.fh.vo.system.DollToyVo;

import net.sf.json.JSONObject;

@Service("sendGoodsService")
public class SendGoodsService implements SendGoodsManager{

	
    @Resource(name = "appuserService")
    private AppuserManager appuserService;
    
    @Resource(name = "playBackService")
    private PlayBackManage playBackService;
    
    @Resource(name = "conversionService")
    private ConversionManager conversionService;
    
    @Resource(name = "betGameService")
    private BetGameManager betGameService;
    
    @Resource(name = "playDetailService")
    private PlayDetailManage playDetailService;
    
    @Resource(name = "dollService")
    private DollManager dollService;
    
    @Resource(name = "paymentService")
    private PaymentManager paymentService;
    
    @Resource(name="dolltoyService")
    private DollToyManager dolltoyService;

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    /**新增
     * @param pd
     * @throws Exception
     */
    public void save(PageData pd)throws Exception{
        dao.save("SendGoodsMapper.save", pd);
    }

    /**删除
     * @param pd
     * @throws Exception
     */
    public void delete(PageData pd)throws Exception{
        dao.delete("SendGoodsMapper.delete", pd);
    }

    /**修改
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd)throws Exception{
        dao.update("SendGoodsMapper.edit", pd);
    }

    /**列表
     * @param page
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("SendGoodsMapper.datalistPage", page);
    }

    /**列表(全部)
     * @param pd
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("SendGoodsMapper.listAll", pd);
    }

    /**通过id获取数据
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("SendGoodsMapper.findById", pd);
    }

    /**批量删除
     * @param ArrayDATA_IDS
     * @throws Exception
     */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("SendGoodsMapper.deleteAll", ArrayDATA_IDS);
    }

    @Override
    public int regSendGoods(SendGoods sendGoods) throws Exception {
        return (int)dao.save("SendGoodsMapper.regSendGoods",sendGoods);
    }

    /**
     * 申请发货
     * @return
     * @throws Exception
     */
    public JSONObject doSendGoods(String playId,String number,String consignee,String remark,String userId,String mode) throws Exception{
    	 //增加发货记录
        SendGoods sendGoods = new SendGoods();
        //免邮
        if (mode.equals("0") ) {
            sendGoods.setMODE_DESPATCH("0");
        }
        //货到付款
        if (mode.equals("2")) {
            sendGoods.setMODE_DESPATCH("2");
        }
        //金币抵扣
        if (mode.equals("1")) {
            //满2包邮 ，一个需要付运费
            if (Integer.valueOf(number) < 2) {
                //判断用户是否有足够的余额支付邮寄费用
                AppUser appUser = appuserService.getUserByID(userId);
                String balance = appUser.getBALANCE();
                if (Integer.valueOf(balance) < 80) {
                    return RespStatus.fail("余额不足，请充值");
                }
                int newbalance = Integer.valueOf(appUser.getBALANCE()) - 80;
                appUser.setBALANCE(String.valueOf(newbalance));
                appuserService.updateAppUserBalanceById(appUser);
                Payment payment = new Payment();
                payment.setGOLD("-80");
                payment.setUSERID(userId);
                payment.setDOLLID(null);
                payment.setCOST_TYPE("6");
                payment.setREMARK("支付运费");
                paymentService.reg(payment);
                sendGoods.setMODE_DESPATCH("2");
            }
        }

        List<String> list = new LinkedList<>();
        List<PlayDetail> playDetails = new LinkedList<>();
        //获取需要发货的娃娃编号
        String[] pd = playId.split("\\,");
        for (int i = 0; i < pd.length; i++) {
            PlayDetail playDetail = playDetailService.getPlayDetailByID(Integer.parseInt(pd[i]));
            playDetails.add(playDetail);
            String dollId = playDetail.getDOLLID();
            list.add(dollId);
            if (playDetail.getPOST_STATE().equals("0")) {
                playDetail.setPOST_STATE("1");
                playDetail.setSENDGOODS(consignee + "," + remark);
                playDetailService.updatePostState(playDetail);
            } else {
                return RespStatus.fail("已经发货或者兑换");
            }
        }
        List<String> list1 = new LinkedList<>();
        for (int i = 0; i < list.size(); i++) {
            String toyId =  dollService.getDollByID(list.get(i)).getTOY_ID();
            DollToyVo dollToyVo =  dolltoyService.getDollToyByToyId(toyId);
            String toyName =  dollToyVo.getToy_name();
            list1.add(toyName);
        }
        List<String> list2 = new LinkedList<>();
        for (int i = 0; i <list1.size() ; i++) {
           int n  =  Collections.frequency(list1, list1.get(i));
           String toyName = list1.get(i);
           list2.add(toyName+"数量为："+n);
        }
        List<String> newList = new LinkedList(new TreeSet(list2));
        String[] s = consignee.split("\\,");
        String name = s[0];
        String phone = s[1];
        String address = s[2];
        sendGoods.setCNEE_NAME(name);
        sendGoods.setCNEE_ADDRESS(address);
        sendGoods.setCNEE_PHONE(phone);
        sendGoods.setPLAYBACK_ID(null);//抓取的娃娃编号
        sendGoods.setGOODS_NUM(number);
        sendGoods.setREMARK(remark);//留言
        sendGoods.setUSER_ID(userId);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < newList.size(); i++) {
            String ss =  newList.get(i).toString();
            String ss1 = ss.substring(ss.length()-1,ss.length());
            int num = Integer.valueOf(ss1);
            String toyname = ss.substring(0,ss.length()-5);
            DollToyVo dollToyVo =  dolltoyService.getDollToyByToyName(toyname);
            int toyNum = dollToyVo.getToy_num();
            dollToyVo.setToy_num(toyNum-num);
            dolltoyService.updateToyNum(dollToyVo);
            String d = newList.get(i);
            sb.append(d + "，");
        }
        sendGoods.setPOST_REMARK(sb.toString());
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        sendGoods.setCREATE_TIME(time);
        int sg = this.regSendGoods(sendGoods);
        if (sg == 0) {
            return RespStatus.fail("增加记录失败");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("playback", playDetails);
        return RespStatus.successs().element("data", map);
    }

}
