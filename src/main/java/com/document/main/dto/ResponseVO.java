package com.document.main.dto;

import org.apache.commons.lang3.StringUtils;

public class ResponseVO<T> {

	private String status;

	private Integer statusCode;

	private String message;

	private T result;

	protected ResponseVO(Integer statusCode, String status, T data) {
		this.statusCode = statusCode;
		this.status = status;
		this.result = data;
		this.message = StringUtils.EMPTY;
	}

	protected ResponseVO(Integer statusCode, String status, String message) {
		this.statusCode = statusCode;
		this.status = status;
		this.message = message;
	}

	protected ResponseVO(Integer statusCode, String status, T data, String message) {
		this.statusCode = statusCode;
		this.status = status;
		this.result = data;
		this.message = message;
	}

	public static <U> ResponseVO<U> create(Integer statusCode, String status, U data) {
		return new ResponseVO<U>(statusCode, status, data);
	}

	public static ResponseVO<Void> create(Integer statusCode, String status, String message) {
		return new ResponseVO<Void>(statusCode, status, message);
	}
	
	public static <U> ResponseVO<U> create(Integer statusCode, String status, String message, U data) {
		return new ResponseVO<U>(statusCode, status, data, message);
	}

	public String getStatus() {
		return status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public T getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}
}
