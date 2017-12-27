package com.fh.util.resp;

/**
 * 交易返回
 * @author zhuqiuyou
 *
 */
public class TxnResp {

	private boolean respCode=false; //请求状态
	
	private String resultCode;//结果返回状态
	
	private String msg; //错误信息

	public boolean isRespCode() {
		return respCode;
	}

	public void setRespCode(boolean respCode) {
		this.respCode = respCode;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	
}
