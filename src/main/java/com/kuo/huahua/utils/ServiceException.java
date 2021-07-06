package com.kuo.huahua.utils;

/**
 * 服务（业务）异常
 * @author Channing Kuo
 * @date 2020/12/16
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * 错误码
	 */
	protected String code;

	/**
	 * 错误信息
	 */
	private String message;

	public ServiceException() {
		super();
	}

	public ServiceException(ErrorInfo errorInfo) {
		super(errorInfo.getResultMessage());
		this.code = errorInfo.getResultCode();
		this.message = errorInfo.getResultMessage();
	}

	public ServiceException(ErrorInfo errorInfo, Throwable cause) {
		super(errorInfo.getResultMessage(), cause);
		this.code = errorInfo.getResultCode();
		this.message = errorInfo.getResultMessage();
	}

	public ServiceException(String message) {
		super(message);
		this.message = message;
	}

	public ServiceException(String code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public ServiceException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getErrorMessage() {
		return message;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
