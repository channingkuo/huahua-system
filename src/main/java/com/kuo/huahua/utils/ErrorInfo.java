package com.kuo.huahua.utils;

/**
 * @author Channing Kuo
 * @date 2021/1/5
 */
public interface ErrorInfo {

	/**
	 * 错误信息Code
	 * @return Code
	 */
	String getResultCode();

	/**
	 * 错误信息Message
	 * @return Message
	 */
	String getResultMessage();
}
