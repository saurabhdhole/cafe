package com.mm.ms.util;

public class RespMsgUtil {
	public String getStr(String msCode, String respMsgHttpCode, String respMsgDetails){
		return Const.APP_NAME + msCode + respMsgHttpCode + ResponseMsg.MSG_DLMTR + respMsgDetails;
	}
}
