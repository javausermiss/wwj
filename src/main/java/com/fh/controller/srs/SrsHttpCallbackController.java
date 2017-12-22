package com.fh.controller.srs;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.iot.game.pooh.admin.srs.core.entity.api.Client;
import com.iot.game.pooh.admin.srs.core.entity.httpback.HttpOnClose;
import com.iot.game.pooh.admin.srs.core.entity.httpback.HttpOnConnect;
import com.iot.game.pooh.admin.srs.core.entity.httpback.HttpOnDvr;
import com.iot.game.pooh.admin.srs.core.entity.httpback.HttpOnPlay;
import com.iot.game.pooh.admin.srs.core.entity.httpback.HttpOnPublish;
import com.iot.game.pooh.admin.srs.core.entity.httpback.HttpOnStop;
import com.iot.game.pooh.admin.srs.core.entity.httpback.HttpOnUnpublish;
import com.iot.game.pooh.admin.srs.core.vaild.SrsHttpCallbackValid;
import com.iot.game.pooh.admin.srs.core.vo.ClientVo;
import com.iot.game.pooh.admin.srs.interfaces.SrsServerService;



/**
  * SRS 回调签权
  * on_connect	  当客户端连接到指定的vhost和app时
  * on_close	  当客户端关闭连接，或者SRS主动关闭连接时
  * on_publish	  当客户端发布流时，譬如flash/FMLE方式推流到服务器
  * on_unpublish 当客户端停止发布流时 
  * on_play      当客户端开始播放流时
  * on_stop      当客户端停止播放时。备注：停止播放可能不会关闭连接，还能再继续播放。
  * on_dvr       当DVR录制关闭一个flv文件时
  * 
  * @author zhuqiuyou
 **/
@Controller
@RequestMapping(value="/srs/httpback")
public class SrsHttpCallbackController extends BaseController{

	
    @Autowired
    private SrsServerService srsServerService;

    /**
     * on_connect 当客户端连接到指定的vhost和app时
     * @param req
     * @param httpOnConnect
     * @return
     */
	@RequestMapping(value="/onConnect")
	@ResponseBody
	public String onConnect(HttpServletRequest req,@RequestBody HttpOnConnect httpOnConnect){
//		logger.info("srs on_connect events params is "+JSONObject.toJSONString(httpOnConnect));
//		boolean valid=SrsHttpCallbackValid.onConnectVaild(httpOnConnect);
//		logger.info("srs onConnect valid response val is "+valid);
//		if(valid){
//			return "0";
//		}else{
//			return "9999";
//		}
		
		return "0";
	}
	
	/**
	 * on_close	  当客户端关闭连接，或者SRS主动关闭连接时
	 * @param req
	 * @param httpOnClose
	 * @return
	 */
	@RequestMapping(value="/onClose")
	@ResponseBody
	public String onClose(HttpServletRequest req,@RequestBody HttpOnClose httpOnClose){
		
		logger.info(JSONObject.toJSON(httpOnClose).toString());
		
		return "0";
	}
	
	/**
	 * on_publish	  当客户端发布流时，譬如flash/FMLE方式推流到服务器
	 * @param req
	 * @param httpOnPublish
	 * @return
	 */
	@RequestMapping(value="/onPublish")
	@ResponseBody
	public String onPublish(HttpServletRequest req,@RequestBody HttpOnPublish httpOnPublish){
		
//		logger.info(JSONObject.toJSON(httpOnPublish).toString());
		boolean valid=true;
//		try{
//			String trmpUrl=httpOnPublish.getTcUrl().split("\\?")[0];
//			int operNum=trmpUrl.lastIndexOf("/");
//			String addrUrl=trmpUrl.substring(7,operNum);
////			
//			Client clt=srsServerService.getClientByClientId(addrUrl+":1985",httpOnPublish.getClient_id());
//			ClientVo clt=srsServerService.getSrsClients(addrUrl, "1985");
////			TODO 更新摄像头表server_id
//			logger.info(JSONObject.toJSONString(clt));
//			valid=true;
//		}catch(Exception ex){
//			logger.info(ex.getLocalizedMessage(), ex);
//			valid=false;
//		}
		
		if(valid){
			return "0";
		}else{
			return "9999";
		}
	}
	
	/**
	 * on_unpublish 当客户端停止发布流时 
	 * @param req
	 * @param httpOnUnpublish
	 * @return
	 */
	@RequestMapping(value="/onUnpublish")
	@ResponseBody
	public String onUnpublish(HttpServletRequest req,@RequestBody HttpOnUnpublish httpOnUnpublish){
		
		logger.info(JSONObject.toJSON(httpOnUnpublish).toString());
		
		return "0";
	}
	
	/**
	 * on_play      当客户端开始播放流时
	 * @param req
	 * @param httpOnPlay
	 * @return
	 */
	@RequestMapping(value="/onPlay")
	@ResponseBody
	public String onPlay(HttpServletRequest req,@RequestBody HttpOnPlay httpOnPlay){
		
		logger.info(JSONObject.toJSON(httpOnPlay).toString());
		
		return "0";
	}
	
	/**
	 * on_stop      当客户端停止播放时。备注：停止播放可能不会关闭连接，还能再继续播放。
	 * @param req
	 * @param httponStop
	 * @return
	 */
	@RequestMapping(value="/onStop")
	@ResponseBody
	public String onStop(HttpServletRequest req,@RequestBody HttpOnStop httponStop){
		
		logger.info(JSONObject.toJSON(httponStop).toString());
		
		return "0";
	}
	
	/**
	 * on_dvr       当DVR录制关闭一个flv文件时
	 * @param req
	 * @param httpOnDvr
	 * @return
	 */
	@RequestMapping(value="/onDvr")
	@ResponseBody
	public String onDvr(HttpServletRequest req,@RequestBody HttpOnDvr httpOnDvr){
		
		logger.info(JSONObject.toJSON(httpOnDvr).toString());
		
		return "0";
	}
}
	
 