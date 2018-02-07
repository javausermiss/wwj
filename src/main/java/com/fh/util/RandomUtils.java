package com.fh.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.util.StringUtils;

/**
 * 
 * RandomUtils
 * 
 * @description
 * @author zqy
 * @date 2014-5-7
 */
public class RandomUtils {

	/**
	 * 
	 * @title getRandomStr
	 * @description 生成随机字符串
	 * @author zqy
	 * @date 2014-5-7
	 * @param length生成的字符串长度
	 * @param source组成字符串的字符
	 * @return String
	 */
	public static String getRandomStr(int length, String source) {
		if (length <= 0) {
			return "";
		}

		if (StringUtils.isEmpty(source)) {
			source = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		}

		int range = source.length();
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			buffer.append(source.charAt(random.nextInt(range)));
		}

		return buffer.toString();
	}

	/**
	 * 
	 * @title getRandomLetterStr
	 * @description 生成由大小写字母组成的随机字符串
	 * @author zqy
	 * @date 2014-5-7
	 * @param length生成的字符串长度
	 * @return String
	 */
	public static String getRandomLetterStr(int length) {
		return getRandomStr(length, "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
	}

	/**
	 * 
	 * @title getRandomNumbernStr
	 * @description 生成由数字组成的随机字符串
	 * @author zqy
	 * @date 2014-5-7
	 * @param length生成的字符串长度
	 * @return String
	 */
	public static String getRandomNumbernStr(int length) {
		return getRandomStr(length, "0123456789");
	}
	
	/**
	 * 
	 * @title getRandomHexStr
	 * @description 生成十六进制组成的随机字符串
	 * @date 2014-5-7
	 * @param length生成的字符串长度
	 * @return String
	 */
	public static String getRandomHexStr(int length) {
		String source = "0123456789abcdef";
		int range = source.length();
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			char randomChar = source.charAt(random.nextInt(range));
			while(buffer.indexOf(String.valueOf(randomChar))>=0){
				randomChar = source.charAt(random.nextInt(range));
			}
			buffer.append(randomChar);
		}
		
		return buffer.toString();
	}
	
	/**
	 * 
	 * @title getRandomAllStr
	 * @description 生成由大小写字母和数字组成的随机字符串
	 * @author zqy
	 * @date 2014-5-7
	 * @param length生成的字符串长度
	 * @return String
	 */
	public static String getRandomAllStr(int length) {
		return getRandomStr(length, null);
	}

	/**
	 * 生成6位由大小写字母和数字组成的随机字符串
	 * 
	 * @Title get6Random
	 * @Description 
	 * @return
	 * @author zg
	 * @Date 2015-1-9下午2:19:29
	 */
	public static String get6Random() {
		return getRandomAllStr(6);
	}

	/**
	 * 生成8位由大小写字母和数字组成的随机字符串
	 * 
	 * @title get8Random
	 * @description 生成8位由大小写字母和数字组成的随机字符串
	 * @author zg
	 * @date 2014-5-7
	 * @return String
	 */
	public static String get8Random() {
		return getRandomAllStr(8);
	}

	/**
	 * 生成16位由大小写字母和数字组成的随机字符串
	 * 
	 * @title get16Random
	 * @description 生成16位由大小写字母和数字组成的随机字符串
	 * @author zg
	 * @date 2014-5-7
	 * @return String
	 */
	public static String get16Random() {
		return getRandomAllStr(16);
	}

	/**
	 * 
	 * @title get32Random
	 * @description 生成32位由大小写字母和数字组成的随机字符串
	 * @author zg
	 * @date 2014-5-7
	 * @return String
	 */
	public static String get32Random() {
		return getRandomAllStr(32);
	}

	/**
	 * 
	 * @title get64Random
	 * @description 生成64位由大小写字母和数字组成的随机字符串
	 * @author zg
	 * @date 2014-5-7
	 * @return String
	 */
	public static String get64Random() {
		return getRandomAllStr(64);
	}
	public static final String NUM_CHAR = "0123456789";
	
	public static final int LEN_CHAR = NUM_CHAR.length();
	 
	public static void main(String[] args) {
		String code=getRandomLetterStr(8);
		System.out.println(code);
	}
}
