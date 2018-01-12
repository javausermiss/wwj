package com.fh.rpc;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.fh.entity.system.Doll;
import com.fh.service.system.doll.DollManager;
import com.iot.game.pooh.web.rpc.interfaces.DollWebRpcService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class DollWebServiceImpl implements DollWebRpcService {

    @Resource(name = "dollService")
    private DollManager dollService;

	@Override
	public String getRoomName(String roomId) throws Exception {
		Doll doll=dollService.getDollByID(roomId);
		if(doll !=null){
			return doll.getDOLL_NAME();
		}else{
			return "";
		}
	}
}
