package com.fh.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.iot.game.pooh.web.rpc.interfaces.LotteryWebRpcService;

@Service
public class LotteryWebServiceImpl implements LotteryWebRpcService {

	/**
	 * 开始竞猜
	 */
	@Override
	public String startLottery(String roomId) {
		
		//
		return null;
	}

	/**
	 * 结束竞猜
	 */
	@Override
	public String endLottery(String roomId) {
		
		return null;
	}

	/**
	 * 开始算奖
	 */
	@Override
	public String drawLottery(String roomId) {
		
		return null;
	}
}
