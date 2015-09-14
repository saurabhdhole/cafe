package com.mm.ms.exception;

public class CustomException extends Exception {
	String msg;

	public CustomException(String msg) {
		super();
		this.msg = msg;
	}
}
