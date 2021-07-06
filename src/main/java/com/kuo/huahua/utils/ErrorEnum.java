package com.kuo.huahua.utils;

/**
 * @author Channing Kuo
 * @date 2021/1/5
 */
public enum ErrorEnum implements ErrorInfo {
	// Api Error response
	SUCCESS("200", "成功！"),
	USERNAME_ERROR("401", "用户名不正确！"),
	PASSWORD_ERROR("401", "密码不正确！"),
	INTERNAL_SERVER_ERROR("500", "服务器异常，请稍后再试！"),
	NULL_POINT_ERROR("501", "[Null_Point]服务器异常，请稍后再试！");

	/**
	 * 错误码
 	 */
	private String resultCode;

	/**
	 * 错误描述
	 */
	private String resultMessage;

	ErrorEnum(String resultCode, String resultMessage) {
		this.resultCode = resultCode;
		this.resultMessage = resultMessage;
	}

	@Override
	public String getResultCode() {
		return resultCode;
	}

	@Override
	public String getResultMessage() {
		return resultMessage;
	}
}
