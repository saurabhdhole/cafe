package com.mm.ms.exception;

public class ReconcileException extends Exception {
	String msg;

	public ReconcileException(String msg) {
		super();
		this.msg = msg;
	}
}
