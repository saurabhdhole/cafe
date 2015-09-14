package com.mm.ms.util;

public class LogUtil {
	public String getPreStr(String msCode, String loggedInUser) {
		return msCode + "[" + loggedInUser + "] ";
	}
}
