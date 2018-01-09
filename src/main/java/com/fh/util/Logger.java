package com.fh.util;

import org.slf4j.LoggerFactory;

/** 
 * 说明：日志处理
 * 创建人：FH Q313596790
 * 修改时间：2014年9月20日
 * @version
 */
public class Logger {

	private org.slf4j.Logger logger;

	
	/**
	 * 构造方法，初始化Log4j的日志对象
	 */
	private Logger(org.slf4j.Logger logger) {
		this.logger = logger;
	}


    /**
     * 获取构造器，根据类初始化Logger对象
     *
     * @param classObject Class对象
     * @return Logger对象
     */
	public static Logger getLogger(Class classObject) {
		return new Logger(LoggerFactory.getLogger(classObject));
	}

    /**
     * 获取构造器，根据类名初始化Logger对象
     *
     * @param loggerName 类名字符串
     * @return Logger对象
     */
	public static Logger getLogger(String loggerName) {
		return new Logger(LoggerFactory.getLogger(loggerName));
	}

	public void info(String str) {
		logger.info(str);
	}
	
	public void info(String str,Throwable e) {
		logger.info(str,e);
	}

	public void warn(String str) {
		logger.warn(str);
	}

	public void warn(String str, Throwable e) {
		logger.warn(str, e);
	}

	public void error(String str) {
		logger.error(str);
	}
	
	public void error(String str, Throwable e) {
		logger.error(str,e);
	}

	public String getName() {
		return logger.getName();
	}

	public org.slf4j.Logger getLog4jLogger() {
		return logger;
	}

	public boolean equals(Logger newLogger) {
		return logger.equals(newLogger.getLog4jLogger());
	}
}