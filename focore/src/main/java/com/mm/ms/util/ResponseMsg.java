package com.mm.ms.util;

public class ResponseMsg {	
	public static final String HTTP_HEADER_NAME = Const.APP_NAME + "-" + "RESP-MSG";
	public static final String MSG_DLMTR = ": ";
	
	//Continue
	public static final String HTTP_100 = "100";
	//Processing
	public static final String HTTP_102 = "102";
	
	//OK
	public static final String HTTP_200 = "200";
	//Created
	public static final String HTTP_201 = "201";
	//Accepted
	public static final String HTTP_202 = "202";
	
	//Bad Request
	public static final String HTTP_400 = "400";
	//Unauthorized
	public static final String HTTP_401 = "401";
	//Payment Required
	public static final String HTTP_402 = "402";
	//Forbidden
	public static final String HTTP_403 = "403";
	//Not Found
	public static final String HTTP_404 = "404";
	//Request Timeout
	public static final String HTTP_408 = "408";
	//Conflict
	public static final String HTTP_409 = "409";
	//Internal Server Error
	public static final String HTTP_500 = "500";
	public static final String HTTP_503 = "503";
}
