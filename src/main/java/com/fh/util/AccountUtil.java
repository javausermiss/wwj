package com.fh.util;

/**
 * 账户信息
 * @author
 *
 */
public class AccountUtil {

	/**
	 * 可用余额密文
	 * @param userId
	 * @param accBal
	 * @return
	 */
	public static String MD5SignAccBal(String userId,String accBal){
		return MD5Utils.getMD5Str(userId+accBal);
	}
	
	/**
	 * 可用余额密文
	 * @param userId 用户Id
	 * @param accBal 账户余额
	 * @param accBalCode 可用余额密文
	 * @return
	 */
	public static boolean accBalEqualsCode(String userId,String accBal,String accBalCode){
		boolean flag=false;
		String currMd5=MD5SignAccBal(userId,accBal);
		if(currMd5.equals(accBalCode)){
			flag=true;
		}
		return flag;
	}
}
