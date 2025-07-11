package com.deliverytech.delivery.exceptions;

public class BusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private String errorCode;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message, String errorCode, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	protected void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
}